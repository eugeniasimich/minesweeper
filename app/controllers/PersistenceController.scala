package controllers

import javax.inject._
import model.DB
import play.api.mvc._
import play.api.libs.json.{JsError, JsString, Json}
import model.GameModel._
import play.api.Configuration

@Singleton
class PersistenceController @Inject()(cc: ControllerComponents, config: Configuration)
    extends AbstractController(cc) {

  val db = new DB(config.get[String]("db.default.url"))
  val username = "euge" //until logins are implemented

  def saveGame() = Action(parse.json) { request =>
    val placeResult = request.body.validate[SaveGame]
    placeResult.fold(
      errors => {
        BadRequest(Json.obj("message" -> JsError.toJson(errors)))
      },
      saveGame => {
        val r = db.saveGame(saveGame, username)
        Ok(Json.toJson(saveGame))
      }
    )
  }

  def savedGames() = Action {
    Ok(Json.toJson(db.listOfGames(username)))
  }

  def resumeGame(name: String) = Action {
    val maybeResult: Option[SaveGame] = db.resumeGame(name, username)
    maybeResult match {
      case None    => NotFound(Json.obj("message" -> JsString("Game not found")))
      case Some(r) => Ok(Json.toJson(r))
    }
  }
}
