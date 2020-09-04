package model

import play.api.libs.json.Json
import utils.Random.randomSetOfPositions

object GameModel {

  case class Cell(isOpen: Boolean, isMine: Boolean, n: Int) {
    def putMine = copy(isMine = true)
    def setValue(value: Int) = copy(n = value)
  }

  case object Cell {
    def empty = Cell(false, false, 0)
  }

  implicit val fmtCell = Json.format[Cell]

  case class Game(data: Array[Array[Cell]], nRows: Int, nCols: Int, nMines: Int) {

    def putMine(row: Int, col: Int) = {
      data(row)(col) = data(row)(col).putMine
    }

  }

  implicit val fmtGame = Json.format[Game]

}
