package com.github.lpld.tfcalc

import com.github.lpld.tfcalc.Calculators._
import com.github.lpld.tfcalc.Show.ops._

object Calculators {
  type Id[X] = X
  implicit val intShow: Show[Int] = (t: Int) => t.toString
}

/**
  * Interpreter for Calculator that performs actual computation
  */
object CountingCalc extends Calculator[Id] {
  def num(i: Int) = i

  def infixOp(op: InfixOp[Int])(i1: Int, i2: Int) = op.f(i1, i2)
}

case class Print[T](acc: String)

/**
  * Interpreter for Calculator that builds a string representation of an expression
  */
object PrintingCalc extends Calculator[Print] {

  def num(i: Int) = Print(i.show)

  def infixOp(op: InfixOp[Int])(i1: Print[Int], i2: Print[Int]) = Print(s"(${i1.acc} ${op.str} ${i2.acc})")
}

