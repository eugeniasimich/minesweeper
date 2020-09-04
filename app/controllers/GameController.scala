package controllers

import javax.inject._
import play.api.libs.json.{JsString, Json}
import play.api.mvc._

@Singleton
class GameController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def newGame() = Action {
    Ok(JsString(s"This is a new game"))
  }
}