package org.test


//Store sparse matrix efficiently
case class SparseMatrix(rowNum: Int = 0, colNum: Int = 0, private val posToVal: Map[Pos, Int] = Map.empty) extends Serializable with Product {
  def appendRow(nums: List[Int]): SparseMatrix = {
    val posToNum = nums.zipWithIndex.map { case (num, col) => Pos(rowNum, col) -> num }
    SparseMatrix(rowNum + 1, colNum max nums.size, posToVal ++ posToNum)
  }

  def add(pos: Pos, num: Int): SparseMatrix = SparseMatrix(rowNum max (pos.row + 1), colNum max (pos.col + 1), posToVal + (pos -> num))

  def get(pos: Pos): Option[Int] = posToVal.get(pos)

  def isEmpty(): Boolean = posToVal.isEmpty
}

