package controllers

import javax.inject._
import models.{PostgresConfig, UserDAO}
import models.UserModel._
import models.SessionModel._
import play.api.mvc._
import play.api.libs.json.{JsError, JsString, Json}
import play.api.Configuration
@Singleton
class LoginController @Inject()(cc: ControllerComponents, config: Configuration)
    extends AbstractController(cc) {

  val userDAO = new UserDAO(PostgresConfig(config.get[String]("db.default.url")))
  val loginManager = new LoginManager(userDAO, SessionDAO)

  def checkLogin() = Action(parse.json) { request =>
    val placeResult = request.body.validate[models.SessionModel.Session]
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
        loginManager
          .login(user)
          .fold(Forbidden(Json.obj("message" -> JsString("Wrong username or pass")))) { session =>
            Ok(Json.toJson(session))
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
        loginManager
          .signup(user)
          .fold(Forbidden(Json.obj("message" -> JsString("Username already taken")))) { session =>
            Ok(Json.toJson(session))
          }
      }
    )
  }
}
