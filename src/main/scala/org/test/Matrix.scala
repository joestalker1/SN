package org.test

//to store sparse matrix efficiently
case class Matrix(rowNum: Int = 0, colNum: Int = 0, rowColToVal: Map[Pos, Int] = Map.empty) {
  def add(pos: Pos, num: Int): Matrix = Matrix(rowNum max (pos.row + 1), colNum max (pos.col + 1), rowColToVal + (pos -> num))

  def get(pos: Pos): Option[Int] = rowColToVal.get(pos)
}

