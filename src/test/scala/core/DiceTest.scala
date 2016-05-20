package core

import org.scalatest.{Matchers, FlatSpec}


class DiceTest extends FlatSpec with Matchers {

  "Dice.rolls" should "return values between 1 and n" in {
    val rolls = Dice.rolls(6, 100).eval(RNG(1L))
    rolls.contains(0) should be(false)
    List(1, 2, 3, 4, 5, 6).forall(rolls.contains) should be(true)
    rolls.exists(_ > 6) should be(false)
  }

  "Dice" should "roll and sum one die" in {
    val rng = RNG(1L)

    val result1 = Dice(1).eval(rng)
    result1.rolled should be(1)
    result1.kept should be(1)
    result1.sides should be(6)
    result1.rolls should be(List(5))
    result1.total should be(5)
  }

  it should "roll and sum multiple dice" in {
    val rng = RNG(1L)
    val result2 = Dice(2).eval(rng)
    result2.rolled should be(2)
    result2.kept should be(2)
    result2.sides should be(6)
    result2.rolls should be(List(5, 5))
    result2.total should be(10)

    val result3 = Dice(5).eval(rng)
    result3.rolls should be(List(5, 5, 3, 4, 2))
    result3.total should be(19)
  }

  it should "keep the highest dice" in {
    val result = Dice(5, 2).eval(RNG(2L))
    result.rolled should be(5)
    result.kept should be(2)
    result.rolls should be(List(4, 2, 5, 3, 5))
    result.total should be(10)
  }

  it should "drop the 2 lowest if specified" in {
    val result = Dice(5, 3).eval(RNG(3L))
    result.rolled should be(5)
    result.kept should be(3)
    result.rolls should be(List(3, 5, 6, 5, 6))
    result.total should be(17)
  }
}