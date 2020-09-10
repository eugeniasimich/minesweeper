package controllers

import models.SessionModel.{Session, SessionDAO}
import models.UserDAO
import models.UserModel.{User, checkUsernamePassword}

case class LoginManager(userDAO: UserDAO, sessionDAO: SessionDAO) {
  def login(user: User): Option[Session] = {
    if (checkUsernamePassword(user, userDAO.getUser(user.username)))
      Some(sessionDAO.sessionForUser(user.username))
    else None
  }

  def signup(user: User): Option[Session] = {
    if (userDAO.getUser(user.username).nonEmpty)
      None
    else {
      val maybeUser: Option[User] = userDAO.addUser(user.username: String, user.password: String)
      maybeUser.map(_ => sessionDAO.sessionForUser(user.username))
    }
  }
}
