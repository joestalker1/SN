package org.test

import scala.annotation.tailrec

//to store position in matrix
final case class Pos(row: Int, col: Int) extends Serializable with Product

case class MinPath(cost: Int, minCostPath: List[Int] = List.empty) extends Serializable with Product

object MinPath {

  def apply(costOfFullPath: Int, matrix: SparseMatrix, path: scala.collection.mutable.Map[Pos, Pos]): MinPath = {
    @tailrec
    def restorePath(pos: Pos, matrix: SparseMatrix, path: scala.collection.mutable.Map[Pos, Pos], curPath: List[Int] = List.empty): List[Int] = {
      if (matrix.get(pos).isEmpty || pos.row < 0 || pos.col < 0 || pos.row >= matrix.rowNum || pos.col >= matrix.colNum)
        curPath
      else {
        val nextPos = path.get(pos).fold(Pos(-1, -1))(identity[Pos])
        restorePath(nextPos, matrix, path, matrix.get(pos).fold(Int.MaxValue)(identity[Int]) :: curPath)
      }
    }

    val minPath = restorePath(Pos(0, 0), matrix, path)
    MinPath(costOfFullPath, minPath)
  }

}