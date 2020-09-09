package models
import doobie._
import doobie.implicits._
import cats.effect.IO
import cats.implicits._
import doobie.util.ExecutionContexts
import play.api.libs.json.Json

object UserModel {

  case class User(username: String, password: String)
  implicit val fmtUser = Json.format[User]

  def checkUsernamePassword(input: User, fromDB: Option[User]): Boolean = {
    fromDB.exists(_.password == input.password)
  }
}

class UserDAO(databaseConfig: DatabaseConfig) {
  import UserModel._

  implicit val cs = IO.contextShift(ExecutionContexts.synchronous)

  object Queries {
    val create = sql"""create table if not exists users (
      username varchar(225) not null primary key, 
      password varchar(255) not null)""".update

    def insert(user: User) =
      sql"insert into users (username, password) values ( ${user.username}, ${user.password})".update

    def getByUserName(username: String) =
      sql"""select password from users where username = $username"""
        .query[String]

  }
  import Queries._

  def getUser(username: String): Option[User] = {
    val xa = databaseConfig.getTransactor
    val resultDescription = for {
      _ <- create.run
      a <- getByUserName(username).option
    } yield a
    resultDescription.transact(xa).unsafeRunSync().map(User(username, _))
  }

  def addUser(username: String, password: String): Option[User] = { //TODO encrypt
    val xa = databaseConfig.getTransactor
    create.run.transact(xa).unsafeRunSync()
    if (getUser(username).nonEmpty) {
      None
    } else {
      val user = User(username, password)
      if (insert(user).run.transact(xa).unsafeRunSync() == 1)
        Some(user)
      else None
    }
  }

}
