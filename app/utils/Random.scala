package utils
import scala.util.Random

object Random {

  def randomSetOfPositions(maxX: Int, maxY: Int, n: Int) = {
    val rnd = new Random
    def generateRandomSet(set: Set[(Int, Int)]): Set[(Int, Int)] = {
      if (set.size == n || n > maxX * maxY)
        set
      else {
        val x: (Int, Int) = (rnd.nextInt(maxX), rnd.nextInt(maxY))
        generateRandomSet(set ++ Set(x))
      }
    }

    generateRandomSet(Set[(Int, Int)]())
  }

}
