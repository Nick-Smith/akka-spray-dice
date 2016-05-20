package api

import akka.actor.ActorSystem

trait SystemProvider {

  implicit def system: ActorSystem

  sys.addShutdownHook(system.shutdown())
}