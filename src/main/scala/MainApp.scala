import org.test.{MinPath, MinTrianglePath}

import scala.io.StdIn

object MainApp extends App {

  def loop(s: String, minTrianglePath: MinTrianglePath): Either[Throwable, MinPath] = {
    if (s == null || s.trim.isBlank || s.trim.isEmpty) {
      println("Finding a minimal triangle path...")
      Right(minTrianglePath.getMinPath())
    } else {
      minTrianglePath.add(s.trim).flatMap(updatedMinTrianglePath => loop(StdIn.readLine(), updatedMinTrianglePath))
    }
  }

  val minPath = loop(StdIn.readLine(), MinTrianglePath())
  minPath.foreach(path => println(s"Minimal path is: ${path.cost} = ${path.minCostPath.map(_.toString).mkString(" + ")}"))
  minPath.left.foreach(println)
}
