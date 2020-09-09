package controllers

import javax.inject._
import models.{GameDAO, PostgresConfig, UserDAO}
import play.api.mvc._
import play.api.libs.json.{JsError, JsString, Json}
import models.GameModel._
import play.api.Configuration
@Singleton
class PersistenceController @Inject()(cc: ControllerComponents, config: Configuration)
    extends AbstractController(cc)
    with AuthenticatedAction {

  val postgresConfig = PostgresConfig(config.get[String]("db.default.url"))
  val gameDAO = new GameDAO(postgresConfig)
  implicit val userDAO = new UserDAO(postgresConfig)

  def saveGame() = withUserParseBody(parse.json) { user => request =>
    val placeResult = request.body.validate[SaveGame]
    placeResult.fold(
      errors => {
        BadRequest(Json.obj("message" -> JsError.toJson(errors)))
      },
      saveGame => {
        gameDAO.saveGame(saveGame, user.username)
        Ok(Json.toJson(saveGame))
      }
    )
  }

  def savedGames() = withUser { user =>
    Ok(Json.toJson(gameDAO.listOfGames(user.username)))
  }

  def resumeGame(name: String) = withUser { user =>
    val maybeResult: Option[SaveGame] = gameDAO.getGame(name, user.username)
    maybeResult match {
      case None            => NotFound(Json.obj("message" -> JsString("Game not found")))
      case Some(savedGame) => Ok(Json.toJson(savedGame))
    }
  }

}
