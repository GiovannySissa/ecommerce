package io.gs.ecommerce.ops

import cats.ApplicativeError
import cats.syntax.option._
import mouse.boolean._


class Checker[A](value: A) {
  def checkF[E, F[_]: ApplicativeError[*[_], E]](check: A => Boolean)(e: E): F[A] =
    check(value).option(value).liftTo[F](e)
}
sealed trait CheckerSyntax {
  implicit final def checkerOps[A](value: A): Checker[A] = new Checker(value)
}

object checker extends CheckerSyntax
