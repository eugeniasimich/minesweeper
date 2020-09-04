package controllers

import javax.inject._
import play.api.libs.json.{JsString, Json}
import play.api.mvc._

@Singleton
class GameController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def newGame(x: Int, y: Int, n: Int) = Action {
    Ok(JsString(s"This is a new game of size $x x $y and $n mines"))
  }
}