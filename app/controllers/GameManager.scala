package controllers

import model.GameModel.{Cell, Game}
import utils.RandomUtil.randomSetOfPositions
object GameManager {

  private def calculateSurroundingIndexes(row: Int, col: Int, nRows: Int, nCols: Int) = {
    for {
      i <- (row - 1).max(0) to (row + 1).min(nRows - 1)
      j <- (col - 1).max(0) to (col + 1).min(nCols - 1)
      if !(i == row && j == col)
    } yield (i, j)
  }

  private def calculateSurroundingMines(g: Game, row: Int, col: Int) = {
    val indexes = calculateSurroundingIndexes(row, col, g.nRows, g.nCols)
    val value = indexes.map { case (i, j) => if (g.data(i)(j).isMine) 1 else 0 }.sum
    g.data(row)(col) = g.data(row)(col).setValue(value)
  }

  private def openSurroundingMines(game: Game, row: Int, col: Int): Unit = {
    val cell = game.data(row)(col)
    if (!cell.isOpen) {
      game.updateCell(row, col, _.open)

      if (cell.n == 0) {
        val indexes = calculateSurroundingIndexes(row, col, game.nRows, game.nCols)
        indexes.foreach {
          case (i, j) =>
            openSurroundingMines(game, i, j)
        }
      }

    }
  }

  private def hasWon(game: Game): Boolean = {
    for (i <- 0 until game.nRows; j <- 0 until game.nCols) {
      val cell = game.data(i)(j)
      if (!cell.isMine && !game.data(i)(j).isOpen)
        return false
    }
    true
  }

  def createNewGame(nRows: Int, nCols: Int, nMines: Int): Game = {
    val game = Game(Array.fill(nRows)(Array.fill(nCols)(Cell.empty)), nRows, nCols, nMines)
    val mines: Set[(Int, Int)] = randomSetOfPositions(nRows, nCols, nMines)
    mines.foreach { case (i, j) => game.updateCell(i, j, _.putMine) }
    for (i <- 0 until nRows; j <- 0 until nCols) {
      calculateSurroundingMines(game, i, j)
    }
    game
  }

  def openCell(game: Game, row: Int, col: Int): Game = {
    val cell = game.data(row)(col)
    if (cell.isMine) {
      game.updateAll(_.open)
      game.copy(hasLost = true)
    } else {
      openSurroundingMines(game, row, col)
      if (hasWon(game)) {
        game.updateAll(_.open)
        game.copy(hasWon = true)
      } else game
    }
  }

}
