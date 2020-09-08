package models

import java.time.LocalDateTime
import java.util.UUID

import play.api.libs.json.Json

import scala.collection.mutable.{Map => MMap}

object SessionDAO {

  case class Session(username: String, token: String, expires: LocalDateTime)

  implicit val fmtSession = Json.format[Session]

  //in memory session for simplicity
  private val sessions = MMap.empty[String, Session]

  def getSession(token: String): Option[Session] = {
    sessions.get(token)
  }

  def sessionForUser(username: String): Session = {
    val token = s"$username-token-${UUID.randomUUID().toString}"
    val session = Session(username, token, LocalDateTime.now().plusHours(1))
    sessions.put(token, Session(username, token, LocalDateTime.now().plusHours(1)))
    session
  }

}
