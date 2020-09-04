package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json.{JsString, Json, JsError}
import model.GameModel._

@Singleton
class GameController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def newGame(x: Int, y: Int, n: Int) = Action {
    Ok(Json.toJson(a))
  }

  def openCell() = Action(parse.json) { request =>
    val placeResult = request.body.validate[Game]
    placeResult.fold(
      errors => {
        BadRequest(Json.obj("message" -> JsError.toJson(errors)))
      },
      g => {
        println(g.data.mkString(","))
        Ok(Json.obj("message" -> ("Ok")))
      }
    )
  }

}
