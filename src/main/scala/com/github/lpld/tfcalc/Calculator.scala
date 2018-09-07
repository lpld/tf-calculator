package com.github.lpld.tfcalc

import scala.language.higherKinds

/**
  * Simple calculator algebra that supports basic operations: addition, subtraction, multiplication.
  *
  * @author leopold
  * @since 6/09/18
  */
trait Calculator[F[_]] {
  type IntOp = (F[Int], F[Int]) => F[Int]

  def num(i: Int): F[Int]

  def infixOp(op: InfixOp[Int])(i1: F[Int], i2: F[Int]): F[Int]

  val plus: IntOp = infixOp(InfixOp("+", _ + _))
  val minus: IntOp = infixOp(InfixOp("-", _ - _))
  val mult: IntOp = infixOp(InfixOp("*", _ * _))
}

case class InfixOp[X](str: String, f: (X, X) => X)

object Calculator {

  def apply[F[_]](implicit C: Calculator[F]): Calculator[F] = C

  /**
    * Implicits for Calculator algebra that will allow us to construct expressions in a readable manner:
    * {{{1.n + 2.n * 5.n}}}
    */
  object ops {

    implicit class IntOps[F[_]](i: Int)(implicit C: Calculator[F]) {
      def n: F[Int] = C.num(i)
    }

    implicit class CalcOps[F[_]](f: F[Int])(implicit C: Calculator[F]) {
      def +(f2: F[Int]) = C.plus(f, f2)
      def -(f2: F[Int]) = C.minus(f, f2)
      def *(f2: F[Int]) = C.mult(f, f2)
    }
  }
}
