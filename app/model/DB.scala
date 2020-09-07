package model

import java.sql.{Connection, DriverManager, ResultSet}

import model.GameModel.SaveGame
import play.api.libs.json.Json

import scala.util.{Failure, Success, Try}

class DB(url: String) {

  private def maybeCreate(conn: Connection): Try[Boolean] = {
    val stm = conn.prepareStatement(
      "CREATE TABLE IF NOT EXISTS games(id SERIAL NOT NULL PRIMARY KEY, username VARCHAR(225) NOT NULL, savedgame TEXT, name VARCHAR(255))")
    Try(stm.execute())

  }

  private def insert(saveGame: SaveGame, conn: Connection) = {
    val prep =
      conn.prepareStatement("INSERT INTO games (username, savedgame, name) VALUES (?, ?, ?) ")
    prep.setString(1, "euge")
    prep.setString(2, Json.stringify(Json.toJson(saveGame)))
    prep.setString(3, saveGame.name)
    Try(prep.executeUpdate)
  }

  private def getGame(conn: Connection, name: String, username: String) = {

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

  def saveGame(saveGame: SaveGame) = {

    val conn = DriverManager.getConnection(url)
    for {
      _ <- maybeCreate(conn)
      _ <- insert(saveGame, conn)
    } yield ()

    conn.close()

  }

  def listOfGames(username: String): List[String] = {
    val conn = DriverManager.getConnection(url)
    val prep = conn.prepareStatement("SELECT name FROM games WHERE username = ?")
    prep.setString(1, username)
    val res = Try(prep.executeQuery())

    def buildListOfNames(res: ResultSet, l: List[String]): List[String] = {
      if (res.next())
        buildListOfNames(res, res.getString("name") :: l)
      else
        l
    }
    val listOfNames = res.map(r => buildListOfNames(r, List[String]())).getOrElse(List[String]())

    conn.close()
    listOfNames
  }

}
