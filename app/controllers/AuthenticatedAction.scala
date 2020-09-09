package controllers

import java.time.LocalDateTime

import models.SessionModel.SessionDAO
import models.UserDAO
import models.UserModel.User
import play.api.mvc.{
  BaseController,
  BodyParser,
  EssentialAction,
  Request,
  RequestHeader,
  Result,
  Security
}

trait AuthenticatedAction extends BaseController {
  def withUserParseBody[A](bodyParser: BodyParser[A])(block: User => Request[A] => Result)(
      implicit userDAO: UserDAO): EssentialAction = {
    Security.WithAuthentication(extractUser)(user =>
      Action(bodyParser) { request =>
        block(user)(request)
    })
  }
  def withUser[A](block: User => Result)(implicit userDAO: UserDAO): EssentialAction = {
    Security.WithAuthentication(extractUser)(user => Action { block(user) })
  }

  private def extractUser(req: RequestHeader)(implicit userDAO: UserDAO): Option[User] = {
    val sessionTokenOpt = req.cookies.get("session-login")
    sessionTokenOpt
      .flatMap(cookie => SessionDAO.getSession(cookie.value))
      .filter(_.expires.isAfter(LocalDateTime.now()))
      .map(_.username)
      .flatMap(userDAO.getUser)
  }
}
