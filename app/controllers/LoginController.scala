package controllers

import javax.inject._
import models.{PostgresConfig, SessionDAO, UserDAO}
import models.UserModel._
import models.SessionDAO.{Session, fmtSession}
import play.api.mvc._
import play.api.libs.json.{JsError, JsString, Json}
import play.api.Configuration
@Singleton
class LoginController @Inject()(cc: ControllerComponents, config: Configuration)
    extends AbstractController(cc) {

  val userDAO = new UserDAO(PostgresConfig(config.get[String]("db.default.url")))

  def checkLogin() = Action(parse.json) { request =>
    val placeResult = request.body.validate[Session]
    placeResult.fold(
      errors => {
        BadRequest(Json.obj("message" -> JsError.toJson(errors)))
      },
      session => {
        SessionDAO.getSession(session.token) match {
          case None    => Unauthorized(Json.obj("message" -> JsString("Wrong or expired token")))
          case Some(s) => Ok(Json.toJson(s))
        }

      }
    )
  }

  def login() = Action(parse.json) { request =>
    val placeResult = request.body.validate[User]
    placeResult.fold(
      errors => {
        BadRequest(Json.obj("message" -> JsError.toJson(errors)))
      },
      user => {
        checkUsernamePassword(user, userDAO.getUser(user.username)) match {
          case false => Forbidden(Json.obj("message" -> JsString("Wrong username or pass")))
          case true => {
            val s = SessionDAO.sessionForUser(user.username)
            Ok(Json.toJson(s))
          }
        }

      }
    )
  }

  def signup() = Action(parse.json) { request =>
    val placeResult = request.body.validate[User]
    placeResult.fold(
      errors => {
        BadRequest(Json.obj("message" -> JsError.toJson(errors)))
      },
      user => {
        val maybeUser: Option[User] = userDAO.addUser(user.username: String, user.password: String)
        maybeUser match {
          case None => Forbidden(Json.obj("message" -> JsString("Username already taken")))
          case Some(user) => {
            val s = SessionDAO.sessionForUser(user.username)
            Ok(Json.toJson(s))
          }
        }
      }
    )
  }
}
