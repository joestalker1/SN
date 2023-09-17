package org.test
//to store position in matrix
final case class Pos(row: Int, col: Int) extends Serializable with Product

case class MinPath(cost: Int, minCostPath: List[Int] = List.empty) extends Serializable with Product