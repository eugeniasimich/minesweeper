package model

import doobie._
import doobie.implicits._
import cats.effect.IO
import cats._
import cats.effect._
import cats.data._
import cats.implicits._
import doobie.util.ExecutionContexts
import model.GameModel.SaveGame
import play.api.libs.json.Json
import fs2.Stream

class DB(url: String) {
  implicit val cs = IO.contextShift(ExecutionContexts.synchronous)

  case class DBGame(username: String, savedgame: String, name: String)

  def saveGame(saveGame: SaveGame, username: String): Int = {
    val xa = Transactor.fromDriverManager[IO](
      "org.postgresql.Driver",
      url
    )

    val create = sql"""CREATE TABLE IF NOT EXISTS games (
      id SERIAL NOT NULL PRIMARY KEY, 
      username VARCHAR(225) NOT NULL, 
      savedgame TEXT, 
      name VARCHAR(255))""".update.run

    def insert(saveGame: SaveGame, username: String) =
      sql"insert into games (username, savedgame, name) values ( $username, ${Json.stringify(
        Json.toJson(saveGame))}, ${saveGame.name})".update.run

    (create, insert(saveGame, username)).mapN(_ + _).transact(xa).unsafeRunSync()

  }

  def listOfGames(username: String): List[String] = {
    val xa = Transactor.fromDriverManager[IO](
      "org.postgresql.Driver",
      url
    )

    val r: List[String] = sql"select name from games where username = $username"
      .query[String]
      .stream
      .take(5)
      .compile
      .toList
      .transact(xa)
      .unsafeRunSync()
    r
  }
}
