package controllers

import java.time.LocalDateTime

import models.SessionModel.{Session, SessionDAO}
import models.UserDAO
import models.UserModel.User
import org.scalatestplus.mockito.MockitoSugar
import org.mockito.Mockito._
import org.specs2.matcher.Matchers
import org.specs2.mutable.Specification

class LoginManagerSpec extends Specification with Matchers with MockitoSugar {

  "LoginManager.login" should {
    "return a new session when user exists" in {
      val existingUser = User("name", "pass")
      val newSession = Session("name", "token1234", LocalDateTime.now())

      val userDAO = mock[UserDAO]
      when(userDAO.getUser(existingUser.username)).thenReturn(Some(existingUser))

      val sessionDAO = mock[SessionDAO]
      when(sessionDAO.sessionForUser(existingUser.username)).thenReturn(newSession)

      val loginManager = LoginManager(userDAO, sessionDAO)
      loginManager.login(existingUser) should_== Some(newSession)

    }
  }

  "LoginManager.login" should {
    "return no session when user does not exists" in {
      val someNotExistingUser = User("someNotExistingUser", "pass")

      val userDAO = mock[UserDAO]
      when(userDAO.getUser("someNotExistingUser")).thenReturn(None)

      val sessionDAO = mock[SessionDAO]

      val loginManager = LoginManager(userDAO, sessionDAO)
      loginManager.login(someNotExistingUser) should_== None

    }
  }

  "LoginManager.signup" should {
    "return a new user when username was not present already" in {
      val newUser = User("newUser", "pass")
      val newSession = Session("newUser", "token1234", LocalDateTime.now())

      val userDAO = mock[UserDAO]
      when(userDAO.getUser(newUser.username)).thenReturn(None)
      when(userDAO.addUser(newUser.username, newUser.password)).thenReturn(Some(newUser))

      val sessionDAO = mock[SessionDAO]
      when(sessionDAO.sessionForUser(newUser.username)).thenReturn(newSession)

      val loginManager = LoginManager(userDAO, sessionDAO)
      loginManager.signup(newUser) should_== Some(newSession)
    }

    "return none when username was present already" in {
      val existingUser = User("collidingName", "pass")

      val userDAO = mock[UserDAO]
      when(userDAO.getUser("collidingName")).thenReturn(Some(existingUser))

      val sessionDAO = mock[SessionDAO]

      val loginManager = LoginManager(userDAO, sessionDAO)
      loginManager.signup(existingUser) should_== None
    }
  }

}
