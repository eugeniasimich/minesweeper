package controllers

import org.scalatest.{Inside, Matchers}
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.mvc.Results
import play.api.test._
import play.api.test.Helpers._

class FrontendControllerSpec
    extends PlaySpec
    with Injecting
    with Inside
    with GuiceOneAppPerSuite
    with Results {
  implicit lazy override val app: play.api.Application =
    new GuiceApplicationBuilder().configure().build()
  implicit lazy val materializer = app.materializer

  "FrontendController GET" should {

    "render the index page from the router" in {
      val request = FakeRequest(GET, "/")
      val home = route(app, request).get
      contentAsString(home) must include("Minesweeper")
      status(home) mustBe OK

    }
  }
}
