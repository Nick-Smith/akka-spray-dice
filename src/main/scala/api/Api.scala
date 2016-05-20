package api

import spray.http.MediaTypes._
import spray.routing.{Directives, HttpServiceActor}

class Api extends HttpServiceActor {
  implicit lazy val system = context.system

  def receive = runRoute(routes)

  val routes = new DiceApi(system).routes ~ Index.route
}

object Index extends Directives {

  val route =
    (path("") & get & respondWithMediaType(`text/html`)) {
      complete {
        <html>
          <body>
            <h1>Say hello to
              <i>Akka Dice</i>
              !</h1>
          </body>
        </html>
      }
    }
}