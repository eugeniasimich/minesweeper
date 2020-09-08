package models
import doobie._
import doobie.implicits._
import cats.effect.IO

import cats.implicits._
import doobie.util.ExecutionContexts

case class User(username: String, password: String)

class UserDAO(databaseConfig: DatabaseConfig) {
  implicit val cs = IO.contextShift(ExecutionContexts.synchronous)

  object Queries {
    val create = sql"""CREATE TABLE IF NOT EXISTS users (
      username VARCHAR(225) NOT NULL PRIMARY KEY, 
      password VARCHAR(255))""".update.run

    def insert(user: User) =
      sql"insert into users (username, password) values ( ${user.username}, ${user.password})".update.run

    def getByUserName(username: String) =
      sql"""select password from users where username = $username"""
        .query[String]

  }
  import Queries._

  def getUser(username: String): Option[User] = {
    val xa = databaseConfig.getTransactor
    getByUserName(username).option.transact(xa).unsafeRunSync().map(User(username, _))
  }

  def addUser(username: String, password: String): Option[User] = {
    val xa = databaseConfig.getTransactor
    create.transact(xa).unsafeRunSync()
    if (getUser(username).nonEmpty) {
      None
    } else {
      val user = User(username, password)
      if (insert(user).transact(xa).unsafeRunSync() == 1)
        Some(user)
      else None
    }
  }

}
