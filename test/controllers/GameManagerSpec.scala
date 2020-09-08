package controllers

import models.GameModel.Game
import org.scalacheck.{Gen, Shrink}
import org.scalacheck.Arbitrary.arbitrary
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks
import org.scalacheck.Shrink

trait BaseScalaSpec
    extends FlatSpec
    with Matchers
    with BeforeAndAfterAll
    with ScalaCheckDrivenPropertyChecks

class GameManagerSpec extends BaseScalaSpec {
  implicit val noShrink: Shrink[Int] = Shrink.shrinkAny
  def genGame: Gen[Game] =
    for {
      nRows <- Gen.chooseNum(2, 6)
      nCols <- Gen.chooseNum(2, 6)
      nMines <- Gen.chooseNum(0, (nRows * nCols) - 1)
    } yield GameManager.createNewGame(nRows, nCols, nMines)

  "createNewGame" should "create a game with correct size" in {

    forAll(genGame) { game =>
      game.data.length shouldBe game.nRows
    }

    forAll(genGame) { game =>
      game.data should not be empty
      game.data(0).length shouldBe game.nCols
    }
  }
  it should "create a game with correct number of mines" in {
    forAll(genGame) { game =>
      game.data.map(row => row.count(cell => cell.isMine)).sum shouldBe game.nMines
    }
  }
  it should "correctly set the surrounding values" in {
    forAll(genGame) { game =>
      def validpos(i: Int, j: Int) = i >= 0 && i < game.nRows && j >= 0 && j < game.nCols
      forAll(Gen.chooseNum(0, (game.nRows - 1)), Gen.chooseNum(0, (game.nCols - 1))) { (row, col) =>
        val indexes = List((row - 1, col - 1),
                           (row - 1, col),
                           (row - 1, col + 1),
                           (row, col - 1),
                           (row, col + 1),
                           (row + 1, col - 1),
                           (row + 1, col),
                           (row + 1, col + 1))
        indexes.map {
          case (i, j) => if (validpos(i, j) && game.data(i)(j).isMine) 1 else 0
        }.sum shouldBe game
          .data(row)(col)
          .n

      }
    }

  }

  "openCell" should "determine game is lost" in {
    forAll(genGame) { game =>
      forAll(Gen.chooseNum(0, (game.nRows - 1)), Gen.chooseNum(0, (game.nCols - 1))) { (row, col) =>
        game.data(row)(col).isMine shouldBe GameManager.openCell(game, row, col).hasLost
      }
    }

  }

  it should "return a new game with open cell in position" in {
    forAll(genGame) { game =>
      forAll(Gen.chooseNum(0, (game.nRows - 1)), Gen.chooseNum(0, (game.nCols - 1))) { (row, col) =>
        GameManager.openCell(game, row, col).data(row)(col).isOpen shouldBe true
      }
    }
  }

}
