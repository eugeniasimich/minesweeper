package model

import java.sql.{Connection, DriverManager}

import model.GameModel.SaveGame
import play.api.libs.json.Json

import scala.util.{Failure, Success, Try}

object DB {

  def maybeCreate(conn: Connection): Try[Boolean] = {
    val stm = conn.prepareStatement(
      "CREATE TABLE IF NOT EXISTS games(id SERIAL NOT NULL PRIMARY KEY, username VARCHAR(225) NOT NULL, savedgame TEXT, name VARCHAR(255))")
    Try(stm.execute())

  }

  def insert(saveGame: SaveGame, conn: Connection) = {
    val prep =
      conn.prepareStatement("INSERT INTO games (username, savedgame, name) VALUES (?, ?, ?) ")
    prep.setString(1, "euge")
    prep.setString(2, Json.stringify(Json.toJson(saveGame)))
    prep.setString(3, saveGame.name)
    Try(prep.executeUpdate)
  }

  def getGame(conn: Connection, name: String, username: String) = {

    val prep = conn.prepareStatement("SELECT * FROM games WHERE name = ? and username = ?")
    prep.setString(1, name)
    prep.setString(2, username)
    Try(prep.executeQuery()).flatMap { res =>
      Json
        .parse(res.getString("savedgame"))
        .validate[SaveGame]
        .fold(err => Failure(new Exception(err.toString())), Success(_))
    }
  }

  def saveGame(saveGame: SaveGame, url: String) = {

    val conn = DriverManager.getConnection(url)
    for {
      _ <- maybeCreate(conn)
      _ <- insert(saveGame, conn)
    } yield ()

    conn.close()

  }
}
