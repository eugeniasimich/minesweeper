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
        val url = config.get[String]("db.default.url")
        DB.saveGame(saveGame, url)
        Ok(Json.toJson(saveGame))
      }
    )
  }

}
