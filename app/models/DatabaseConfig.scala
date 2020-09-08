package models

import cats.effect.{ContextShift, IO}
import cats.effect.IO
import doobie._
import doobie.implicits._
import cats.implicits._

abstract class DatabaseConfig {
  val url: String
  val driver: String

  def getTransactor(implicit context: ContextShift[IO]) = {
    Transactor.fromDriverManager[IO](
      driver,
      url
    )
  }
}

case class PostgresConfig(url: String) extends DatabaseConfig {
  override val driver: String = "org.postgresql.Driver"
}
