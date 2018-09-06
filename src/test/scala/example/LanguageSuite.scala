package example

import com.github.lpld.tfcalc.{Calculator, InfixOp, Show}
import org.scalatest.{FunSuite, Matchers}

import scala.language.higherKinds
import Calculator.ops._
import Show.ops._

/**
  * @author leopold
  * @since 6/09/18
  */
class LanguageSuite extends FunSuite with Matchers {

  type Id[X] = X

  val countingCalc: Calculator[Id] = new Calculator[Id] {
    def num(i: Int): Int = i

    def infixOp(op: InfixOp[Int])(i1: Int, i2: Int): Int = op.f(i1, i2)
  }

  implicit val intShow: Show[Int] = (t: Int) => t.toString

  case class Print[T: Show](acc: String)

  val printingCalc: Calculator[Print] = new Calculator[Print] {
    def num(i: Int): Print[Int] = Print(i.show)

    def infixOp(op: InfixOp[Int])(i1: Print[Int], i2: Print[Int]): Print[Int] =
      Print(s"(${i1.acc} ${op.str} ${i2.acc})")
  }

  test("test calculation") {
    implicit val C = countingCalc
    (2.n + 3.n * 7.n - 4.n) shouldBe 19
  }

  // The best thing here is that operators precedence works as expected!
  test("test printing") {
    implicit val C = printingCalc
    (2.n + 3.n * 7.n - 4.n) shouldBe Print("((2 + (3 * 7)) - 4)")
  }
}
