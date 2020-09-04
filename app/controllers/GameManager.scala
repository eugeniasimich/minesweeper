package controllers

import model.GameModel.{Cell, Game}
import utils.Random.randomSetOfPositions
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

  def createNewGame(nRows: Int, nCols: Int, nMines: Int): Game = {
    val game = Game(Array.fill(nRows)(Array.fill(nCols)(Cell.empty)), nRows, nCols, nMines)
    val mines: Set[(Int, Int)] = randomSetOfPositions(nRows, nCols, nMines)
    mines.foreach { case (i, j) => game.putMine(i, j) }
    for (i <- 0 until nRows; j <- 0 until nCols) {
      calculateSurroundingMines(game, i, j)
    }
    game
  }

}
