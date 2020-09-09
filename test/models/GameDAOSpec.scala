package models

import cats.effect.IO
import doobie.specs2.IOChecker
import doobie.util.ExecutionContexts
import doobie.util.testing.Analyzable._
import models.GameModel.{Cell, Game, SaveGame}
import org.specs2.mutable.Specification

class GameDAOSpec extends Specification with IOChecker {

  implicit val cs = IO.contextShift(ExecutionContexts.synchronous)

  val db = PostgresConfig("jdbc:postgresql://localhost/buscamines") //TODO make this depend on config

  val transactor = db.getTransactor

  val queries = new GameDAO(db).Queries

  check { queries.create }
  check {
    val game = Game(Array(Array(Cell.empty, Cell.empty), Array(Cell.empty, Cell.empty)),
                    2,
                    2,
                    0,
                    false,
                    false)
    queries.insert(SaveGame(game, Array.empty, 20, "saveThisGame"), "euge")
  }
  check { queries.list("euge") }

}
