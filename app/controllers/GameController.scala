package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json.Json
import model.GameModel._

@Singleton
class GameController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def newGame(x: Int, y: Int, n: Int) = Action {
    Ok(Json.toJson(a))
  }
}
