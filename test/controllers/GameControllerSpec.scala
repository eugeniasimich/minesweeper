package controllers

import model.GameModel.{Game, GameAction}
import org.scalatest.Inside
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.http.HeaderNames
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.json.{JsNull, JsObject, Json}
import play.api.mvc.Results
import play.api.test.Helpers._
import play.api.test._

class GameControllerSpec
    extends PlaySpec
    with Injecting
    with Inside
    with GuiceOneAppPerSuite
    with Results {
  implicit lazy override val app: play.api.Application =
    new GuiceApplicationBuilder().configure().build()
  implicit lazy val materializer = app.materializer

  "GameController GET newGame" should {

    "return a json representing a game" in {
      val request = FakeRequest(GET, "/api/newGame/2/4/1")
      val newGame = route(app, request).get
      contentType(newGame) mustBe Some("application/json")
      val game = contentAsJson(newGame).validate[Game]
      game.isSuccess must be(true)
      game.get.nRows must be(2)
      game.get.nCols must be(4)
      game.get.nMines must be(1)
      status(newGame) mustBe OK
    }
  }

  "GameController POST openCell" should {

    "return an updated game" in {
      val payload = GameAction(GameManager.createNewGame(1, 1, 0), 0, 0)
      val request =
        FakeRequest(POST, "/api/openCell", headers = FakeHeaders(), body = Json.toJson(payload))
      val newGame = route(app, request).get
      contentType(newGame) mustBe Some("application/json")
      val game = contentAsJson(newGame).validate[Game]
      game.isSuccess must be(true)
      game.get.nRows must be(1)
      game.get.nCols must be(1)
      game.get.nMines must be(0)
      game.get.hasWon must be(true)
      status(newGame) mustBe OK
    }

    "return bad request when provided with wrong json" in {
      val payload = Json.obj("asdf" -> 1)
      val request =
        FakeRequest(POST, "/api/openCell", headers = FakeHeaders(), body = Json.toJson(payload))
      val badRequest = route(app, request).get
      contentType(badRequest) mustBe Some("application/json")
      status(badRequest) mustBe BAD_REQUEST
    }
  }

}
