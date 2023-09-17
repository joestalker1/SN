package org.test

import cats.Eval

import scala.annotation.tailrec
import scala.util.Try

case class MinTrianglePath(lineNum: Int = 0, matrix: Matrix = Matrix()) {
  /**
   * Parse the given string to list of nymber and add them to a sparse matrix to use them later.
   *
   * @param s
   * @return
   */
  def add(s: String): Either[Throwable, MinTrianglePath] = Try {
    val indexWithNums = s.split(" ").zipWithIndex.map { case (value, index) => (index, value.toInt) }
    val updatedMatrix = indexWithNums.foldLeft(matrix) { (acc, indexWithNum) =>
      val (index, num) = indexWithNum
      acc.add(Pos(lineNum, index), num)
    }
    MinTrianglePath(lineNum + 1, updatedMatrix)
  }.toEither

  /**
   * Restore effecive path in path matix, where every cell has next a position in matrix
   *
   * @param pos
   * @param matrix
   * @param path
   * @return
   */
  @tailrec
  private def restorePath(pos: Pos, matrix: Matrix, path: scala.collection.mutable.Map[Pos, Pos], curPath: List[Int] = List.empty): List[Int] = {
    if (matrix.get(pos).isEmpty || pos.row < 0 || pos.col < 0 || pos.row >= matrix.rowNum || pos.col >= matrix.colNum)
      curPath
    else {
      val nextPos = path.get(pos).fold(Pos(-1, -1))(identity[Pos])
      restorePath(nextPos, matrix, path, matrix.get(pos).fold(Int.MaxValue)(identity[Int]) :: curPath)
    }
  }

  /**
   * Find the minimal path by using dynamic programming top down approach.
   * It uses cats Eval to get eleminated StackOverflow exception.
   * In order to restore paht it store next cell for current cell and later on it iterates over them
   * to build number list as minimal path.
   *
   * @return
   */
  def getMinPath(): MinPath = {
    if (matrix.rowColToVal.isEmpty) MinPath(0)
    else {

      def findEval(pos: Pos, matrix: Matrix, dp: scala.collection.mutable.Map[Pos, Int], path: scala.collection.mutable.Map[Pos, Pos]): Eval[Int] = {
        if (matrix.get(pos).isEmpty || pos.col >= matrix.colNum || pos.row >= matrix.rowNum) Eval.now(Int.MaxValue)
        else if (pos.row == matrix.rowNum - 1) {
          Eval.now(matrix.get(pos).fold(Int.MaxValue)(identity[Int]))
        } else {
          if (dp.get(pos).nonEmpty) Eval.now(dp.get(pos).fold(Int.MaxValue)(identity[Int]))
          else {
            Eval.defer {
              findEval(Pos(pos.row + 1, pos.col), matrix, dp, path)
                .flatMap(cost1 => findEval(Pos(pos.row + 1, pos.col + 1), matrix, dp, path).map(cost2 => (cost1, cost2)))
                .map { case (cost1, cost2) =>
                  val newCost = matrix.get(pos).fold(Int.MaxValue)(identity[Int]) + (cost1 min cost2)
                  //update path
                  if (cost1 < cost2) path.update(pos, Pos(pos.row + 1, pos.col))
                  else path.update(pos, Pos(pos.row + 1, pos.col + 1))
                  dp.update(pos, newCost)
                  newCost
                }
            }
          }
        }
      }

      val path = scala.collection.mutable.Map.empty[Pos, Pos]
      val dp = scala.collection.mutable.Map.empty[Pos, Int]
      val cost = findEval(Pos(0, 0), matrix, dp, path).value
      val minPath = restorePath(Pos(0, 0), matrix, path)
      MinPath(cost, minPath)
    }
  }
}
