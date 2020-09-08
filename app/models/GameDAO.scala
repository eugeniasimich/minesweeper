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

    val create = sql"""create table if not exists games (
      id serial not null primary key, 
      username varchar(225) not null, 
      savedgame text, 
      name varchar(255) unique)""".update.run

    def insert(saveGame: SaveGame, username: String) = {
      val jsonGame = Json.stringify(Json.toJson(saveGame))
      sql"""insert into games (username, savedgame, name) 
           values ( $username, $jsonGame, ${saveGame.name}) 
           on conflict (name) do update set savedgame = $jsonGame""".update.run
    }

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
