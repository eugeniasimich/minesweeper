package controllers

import javax.inject._
import model.DB
import play.api.mvc._
import play.api.libs.json.{JsError, Json}
import model.GameModel._
import play.api.Configuration

@Singleton
class GameController @Inject()(cc: ControllerComponents, config: Configuration)
    extends AbstractController(cc) {

  val db = new DB(config.get[String]("db.default.url"))
  val username = "euge" //until logins are implemented

  def newGame(x: Int, y: Int, n: Int) = Action {
    Ok(Json.toJson(GameManager.createNewGame(x, y, n)))
  }

  def openCell() = Action(parse.json) { request =>
    val placeResult = request.body.validate[GameAction]
    placeResult.fold(
      errors => {
        BadRequest(Json.obj("message" -> JsError.toJson(errors)))
      },
      action => {
        val g = GameManager.openCell(action.g, action.i, action.j)
        Ok(Json.toJson(g))
      }
    )
  }

  def saveGame() = Action(parse.json) { request =>
    val placeResult = request.body.validate[SaveGame]
    placeResult.fold(
      errors => {
        BadRequest(Json.obj("message" -> JsError.toJson(errors)))
      },
      saveGame => {
        val r = db.saveGame(saveGame, username)
        println("result updated " + r)
        Ok(Json.toJson(saveGame))
      }
    )
  }

  def savedGames() = Action {
    Ok(Json.toJson(db.listOfGames(username)))
  }

}
