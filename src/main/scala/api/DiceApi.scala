package api

import akka.actor._
import akka.pattern.ask
import akka.util.Timeout
import core.{Dice, DiceResult, RNG}
import spray.routing.Directives

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

class DiceApi(system: ActorSystem) extends Directives{

  implicit val exec: ExecutionContext = system.dispatcher
  val diceService: ActorRef = system.actorOf(Props[DiceService], "dice-service")
  implicit val timeout = Timeout(1.second)

  val routes = (get & path("rolls")) {
    parameters('rolled.as[Int] ? 1, 'kept.as[Int] ? 0, 'sides.as[Int] ? 6) { (r, k, s) =>
      onSuccess(diceService ? DiceRequest(r, k, s)){ case dr: DiceResult =>
        complete(s"[${dr.total}]")
      }
    }
  }
}

case class DiceRequest(rolled: Int, kept: Int, sides: Int)

class DiceService extends Actor {
  private var rng = RNG(System.nanoTime())

  private def rollDice(rolled: Int, kept: Int, sides: Int): DiceResult = {
    val (nextRng, result) = Dice(rolled, kept, sides)(rng)
    rng = nextRng
    result
  }

  def receive: Receive = {
    case DiceRequest(r, k, s) => sender ! rollDice(r, k, s)
  }

}