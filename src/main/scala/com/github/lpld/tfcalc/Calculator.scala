package com.github.lpld.tfcalc

import scala.language.higherKinds

/**
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

  object ops {

    implicit class IntOps[F[_] : Calculator](i: Int) {
      def n: F[Int] = Calculator[F].num(i)
    }

    implicit class CalcOps[F[_] : Calculator](f: F[Int]) {
      def +(f2: F[Int]): F[Int] = Calculator[F].plus(f, f2)

      def -(f2: F[Int]): F[Int] = Calculator[F].minus(f, f2)

      def *(f2: F[Int]): F[Int] = Calculator[F].mult(f, f2)
    }

  }

}
