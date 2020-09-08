package controllers

import javax.inject._
import models.{GameDAO, PostgresConfig}
import play.api.mvc._
import play.api.libs.json.{JsError, JsString, Json}
import models.GameModel._
import play.api.Configuration
@Singleton
class PersistenceController @Inject()(cc: ControllerComponents, config: Configuration)
    extends AbstractController(cc) {

  val gameDAO = new GameDAO(PostgresConfig(config.get[String]("db.default.url")))
  val username = "euge" //until logins are implemented

  def saveGame() = Action(parse.json) { request =>
    val placeResult = request.body.validate[SaveGame]
    placeResult.fold(
      errors => {
        BadRequest(Json.obj("message" -> JsError.toJson(errors)))
      },
      saveGame => {
        val r = gameDAO.saveGame(saveGame, username)
        Ok(Json.toJson(saveGame))
      }
    )
  }

  def savedGames() = Action {
    Ok(Json.toJson(gameDAO.listOfGames(username)))
  }

  def resumeGame(name: String) = Action {
    val maybeResult: Option[SaveGame] = gameDAO.getGame(name, username)
    maybeResult match {
      case None    => NotFound(Json.obj("message" -> JsString("Game not found")))
      case Some(r) => Ok(Json.toJson(r))
    }
  }
}
