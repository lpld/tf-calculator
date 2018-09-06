package com.github.lpld.tfcalc

import simulacrum._

/**
  * @author leopold
  * @since 7/09/18
  */
@typeclass trait Show[T] {
  def show(t: T): String
}

