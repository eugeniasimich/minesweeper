package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json.{JsError, Json}
import model.GameModel._

@Singleton
class GameController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

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

}
