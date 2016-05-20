package core

import scalaz._
import scalaz.Scalaz._

object Dice {
  import RNG._

  def apply(rolled: Int = 1, kept: Int = 0, sides: Int = 6): Rand[DiceResult] = rolls(sides, rolled) map { results =>
    val actualKept = if (kept > 0) kept else rolled
    val total = results.sorted.reverse.take(actualKept).sum
    DiceResult(total, sides, rolled, actualKept, results)
  }

  def rolls(sides: Int, rolled: Int): Rand[List[Int]] = {
    if (rolled < 0) throw new IllegalArgumentException("Negative number of rolls not allowed!")
    else {
      val rolls = List.fill(rolled)(roll(sides))
      rolls.sequence[Rand, Int]
    }
  }

  def roll(sides: Int): Rand[Int] = nonNegativeLessThan(sides) map { _ + 1 }
}

case class DiceResult(total: Int, sides: Int, rolled: Int, kept: Int, rolls: List[Int])
