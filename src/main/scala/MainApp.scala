import org.test.{MinPath, MinTrianglePath}

import scala.io.StdIn

object MainApp extends App {

  def inputData(s: String, minTrianglePath: MinTrianglePath): Either[Throwable, MinPath] = {
    if (s == null || s.trim.isBlank || s.trim.isEmpty) {
      println("Finding a minimal triangle path...")
      Right(minTrianglePath.findMinPath())
    } else {
      minTrianglePath.parseAndAppendNewRowMatrix(s.trim).flatMap(updatedMinTrianglePath => inputData(StdIn.readLine(), updatedMinTrianglePath))
    }
  }

  val minPath = inputData(StdIn.readLine(), MinTrianglePath())
  minPath.foreach(path => println(s"Minimal path is: ${path.cost} = ${path.minCostPath.map(_.toString).mkString(" + ")}"))
  minPath.left.foreach(println)
}
