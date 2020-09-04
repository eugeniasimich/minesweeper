package model

import play.api.libs.json.Json

object GameModel {

  case class Cell(isOpen: Boolean, isMine: Boolean, n: Int) {
    def putMine = copy(isMine = true)
    def setValue(value: Int) = copy(n = value)
    def open = copy(isOpen = true)
  }

  case object Cell {
    def empty = Cell(false, false, 0)
  }

  implicit val fmtCell = Json.format[Cell]

  case class Game(data: Array[Array[Cell]],
                  nRows: Int,
                  nCols: Int,
                  nMines: Int,
                  hasLost: Boolean = false,
                  hasWon: Boolean = false) {

    def updateCell(row: Int, col: Int, f: Cell => Cell) = {
      data(row)(col) = f(data(row)(col))
    }

    def updateAll(f: Cell => Cell) = {
      for (i <- 0 until nRows; j <- 0 until nCols) {
        data(i)(j) = f(data(i)(j))
      }
    }

  }

  implicit val fmtGame = Json.format[Game]

  case class GameAction(g: Game, i: Int, j: Int)
  implicit val fmtClick = Json.format[GameAction]

}
