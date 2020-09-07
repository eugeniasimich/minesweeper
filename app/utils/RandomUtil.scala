package utils
import scala.annotation.tailrec
import scala.util.Random

object RandomUtil {
  val rnd = new Random

  def randomSetOfPositions(maxX: Int, maxY: Int, n: Int) = {
    @tailrec
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
