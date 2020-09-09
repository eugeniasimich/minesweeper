package models

import java.time.LocalDateTime
import java.util.UUID

import play.api.libs.json.Json

import java.util.concurrent.{ConcurrentHashMap => MMap}
object SessionModel {

  case class Session(username: String, token: String, expires: LocalDateTime)

  implicit val fmtSession = Json.format[Session]

  abstract class SessionDAO {
    def getSession(token: String): Option[Session]
    def sessionForUser(username: String): Session
  }

  object SessionDAO extends SessionDAO {

    //in memory session for simplicity
    private val sessions: MMap[String, Session] = new MMap

    def getSession(token: String): Option[Session] = {
      if (sessions.containsKey(token))
        Some(sessions.get(token))
      else
        None
    }

    def sessionForUser(username: String): Session = {
      val token = s"$username-token-${UUID.randomUUID().toString}"
      val session = Session(username, token, LocalDateTime.now().plusHours(1))
      sessions.put(token, Session(username, token, LocalDateTime.now().plusHours(1)))
      session
    }

  }
}
