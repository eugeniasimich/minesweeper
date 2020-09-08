package models

import doobie._
import doobie.implicits._
import cats.effect.IO

import cats.implicits._
import doobie.util.ExecutionContexts
import models.GameModel._
import play.api.libs.json.Json

class GameDAO(databaseConfig: DatabaseConfig) {
  implicit val cs = IO.contextShift(ExecutionContexts.synchronous)
  case class DBGame(username: String, savedgame: String, name: String) {
    def toSaveGame: Option[SaveGame] = {
      Json.toJson(savedgame).validate[SaveGame].fold[Option[SaveGame]](_ => None, Some(_))
    }
  }

  def saveGame(saveGame: SaveGame, username: String): Int = {
    val xa = databaseConfig.getTransactor

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
    val xa = databaseConfig.getTransactor

    val list: List[String] = sql"select name from games where username = $username order by id desc"
      .query[String]
      .stream
      .take(10)
      .compile
      .toList
      .transact(xa)
      .unsafeRunSync()
    list
  }

  def getGame(name: String, username: String): Option[SaveGame] = {
    val xa = databaseConfig.getTransactor

    val stringResult: Option[String] =
      sql"""select savedgame from games where name = $name and username = $username"""
        .query[String]
        .option
        .transact(xa)
        .unsafeRunSync()
    stringResult.flatMap { g =>
      Json.parse(g).validate[SaveGame].fold[Option[SaveGame]](_ => None, Some(_))
    }
  }
}
