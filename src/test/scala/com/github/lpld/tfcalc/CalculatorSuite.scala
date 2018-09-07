package com.github.lpld.tfcalc

import com.github.lpld.tfcalc.Calculator.ops._
import org.scalatest.{FunSuite, Matchers}

/**
  * @author leopold
  * @since 6/09/18
  */
class CalculatorSuite extends FunSuite with Matchers {

  test("CountingCalc should compute int result") {
    implicit val C = CountingCalc
    (2.n + 3.n * 7.n - 4.n) shouldBe 19
  }

  // The best thing here is that operators precedence works as expected!
  test("PrintingCalc should generate string representation of the expression") {
    implicit val C = PrintingCalc
    (2.n + 3.n * 7.n - 4.n) shouldBe Print("((2 + (3 * 7)) - 4)")
  }
}
