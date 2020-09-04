package model

import play.api.libs.json.Json

object GameModel {

  case class Cell(isOpen: Boolean, isMine: Boolean, isFlag: Boolean, n: Int)

  implicit val fmtCell = Json.format[Cell]

  case class Game(data: Array[Cell])

  implicit val fmtGame = Json.format[Game]

  val a = Game(Array(Cell(true, true, false, 0)))
}
