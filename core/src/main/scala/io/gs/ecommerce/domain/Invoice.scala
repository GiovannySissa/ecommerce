package io.gs.ecommerce.domain

import cats.data.ValidatedNel
import cats.instances.either._
import cats.syntax.either._
import io.gs.ecommerce.errors.{InvalidInput, InvalidInvoiceDate, InvalidInvoiceNumber}
import io.gs.ecommerce.ops.checker._

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

final case class InvoiceNo private (value: String) extends AnyVal
object InvoiceNo {

  def apply(input: String): ValidatedNel[InvalidInput, InvoiceNo] =
    input
      .checkF(_.nonEmpty)(InvalidInvoiceNumber())
      .map(new InvoiceNo(_))
      .toValidatedNel
}

final case class InvoiceDate private (date: LocalDateTime) extends AnyVal
object InvoiceDate {

  val dateTimePattern: DateTimeFormatter = DateTimeFormatter.ofPattern("MM/d/yyyy H:mm")
  def apply(input: String): ValidatedNel[InvalidInput, InvoiceDate] =
    Either
      .catchNonFatal(LocalDateTime.parse(input, dateTimePattern))
      .bimap(
        _ => InvalidInvoiceDate(),
        new InvoiceDate(_)
      )
      .toValidatedNel

}
