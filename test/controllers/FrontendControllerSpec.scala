package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test._
import play.api.test.Helpers._

class FrontendControllerSpec extends PlaySpec with GuiceOneAppPerTest {

  "FrontendController GET" should {

    "render the index page from the router" in {
      val request = FakeRequest(GET, "/")
      val home = route(app, request).get

      status(home) mustBe OK

    }
  }
}
