package models

import cats.effect.IO
import doobie.specs2.IOChecker
import doobie.util.ExecutionContexts
import doobie.util.testing.Analyzable._
import models.UserModel.User
import org.specs2.mutable.Specification

class UserDAOSpec extends Specification with IOChecker {

  implicit val cs = IO.contextShift(ExecutionContexts.synchronous)

  val db = PostgresConfig("jdbc:postgresql://localhost/buscamines") //TODO make this depend on config

  val transactor = db.getTransactor

  val queries = new UserDAO(db).Queries

  check { queries.create }
  check { queries.insert(User("a", "pass")) }
  check { queries.getByUserName("a") }

}
