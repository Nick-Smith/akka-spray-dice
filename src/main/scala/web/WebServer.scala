package web

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.io.IO
import akka.util.Timeout
import api.{Api, SystemProvider}
import spray.can.Http

import scala.concurrent.duration._

trait WebServer {
  this: SystemProvider =>

  def handler: ActorRef

  implicit val timeout = Timeout(5.seconds)
  // start a new HTTP server on port 8080 with our service actor as the handler
  IO(Http) ! Http.Bind(handler, interface = "localhost", port = 8080)
}

trait ApiServer extends WebServer with SystemProvider{
  implicit lazy val system = ActorSystem("akka-dice")

  lazy val handler = system.actorOf(Props[Api], "api-actor")
}
