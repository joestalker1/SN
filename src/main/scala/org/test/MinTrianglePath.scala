package org.test

import cats.Eval

import scala.util.Try

case class MinTrianglePath(matrix: SparseMatrix = SparseMatrix()) {
  /**
   * Parse the given string to list of numbers and add them to a sparse matrix to use them later.
   *
   * @param s
   * @return
   */
  def parseAndAppendNewRowMatrix(s: String): Either[Throwable, MinTrianglePath] = Try {
    val nums = s.split(" ").map(_.toInt)
    val newMatrix = matrix.appendRow(nums.toList)
    MinTrianglePath(newMatrix)
  }.toEither

  /**
   * Find the minimal path by using dynamic programming top down approach.
   * It uses cats Eval to get eleminated StackOverflow exception.
   * In order to restore paht it store next cell for current cell and later on it iterates over them
   * to build number list as minimal path.
   *
   * @return
   */
  def findMinPath(): MinPath = {
    if (matrix.isEmpty) MinPath(0)
    else {
      def findEval(pos: Pos, matrix: SparseMatrix, pathCost: scala.collection.mutable.Map[Pos, Int], path: scala.collection.mutable.Map[Pos, Pos]): Eval[Int] = {
        if (matrix.get(pos).isEmpty || pos.col >= matrix.colNum || pos.row >= matrix.rowNum) Eval.now(Int.MaxValue)
        else if (pos.row == matrix.rowNum - 1) {
          Eval.now(matrix.get(pos).fold(Int.MaxValue)(identity[Int]))
        } else {
          if (pathCost.get(pos).nonEmpty) Eval.now(pathCost.get(pos).fold(Int.MaxValue)(identity[Int]))
          else {
            Eval.defer {
              findEval(Pos(pos.row + 1, pos.col), matrix, pathCost, path)
                .flatMap(cost1 => findEval(Pos(pos.row + 1, pos.col + 1), matrix, pathCost, path).map(cost2 => (cost1, cost2)))
                .map { case (cost1, cost2) =>
                  val newCost = matrix.get(pos).fold(Int.MaxValue)(identity[Int]) + (cost1 min cost2)
                  //update path
                  if (cost1 < cost2) path.update(pos, Pos(pos.row + 1, pos.col))
                  else path.update(pos, Pos(pos.row + 1, pos.col + 1))
                  pathCost.update(pos, newCost)
                  newCost
                }
            }
          }
        }
      }

      val path = scala.collection.mutable.Map.empty[Pos, Pos]
      val pathCost = scala.collection.mutable.Map.empty[Pos, Int]
      val cost = findEval(Pos(0, 0), matrix, pathCost, path).value
      MinPath(cost, matrix, path)
    }
  }
}
