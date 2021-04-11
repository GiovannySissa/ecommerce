package io.gs.ecommerce.domain

import cats.data.ValidatedNel
import cats.syntax.apply._
import cats.syntax.either._
import io.gs.ecommerce.errors._
import io.gs.ecommerce.ops.checker._

final case class Order private (
    invoiceNo: InvoiceNo,
    stockCode: StockCode,
    description: OrderDescription,
    quantity: Quantity,
    invoiceDate: InvoiceDate,
    unitPrice: UnitPrice,
    customerId: CustomerId,
    country: Country
)

object Order {
  def apply(
      invoiceNo: String,
      stockCode: String,
      description: String,
      quantity: Int,
      date: String,
      unitPrice: Float,
      customerId: String,
      country: String
  ): ValidatedNel[InvalidInput, Order] =
    (
      InvoiceNo(invoiceNo),
      StockCode(stockCode),
      OrderDescription(description),
      Quantity(quantity),
      InvoiceDate(date),
      UnitPrice(unitPrice),
      CustomerId(customerId),
      Country(country)
    ).mapN(
      new Order(_, _, _, _, _, _, _, _)
    )
}

final case class Country private (value: String) extends AnyVal
object Country {

  def apply(input: String): ValidatedNel[InvalidInput, Country] =
    input.checkF(_.nonEmpty)(InvalidCountry()).map(new Country(_)).toValidatedNel
}

final case class CustomerId private (value: String) extends AnyVal
object CustomerId {



  def apply(input: String): ValidatedNel[InvalidInput, CustomerId] =
    input.checkF(_.nonEmpty)(InvalidCustomerId()).map(new CustomerId(_)).toValidatedNel
}

// todo could be better using money type
final case class UnitPrice private (value: Float) extends AnyVal
object UnitPrice {

  def apply(input: Float): ValidatedNel[InvalidInput, UnitPrice] =
    input.checkF(_ > 0.0)(InvalidUnitPrice()).map(new UnitPrice(_)).toValidatedNel
}

final case class Quantity private (value: Int) extends AnyVal
object Quantity {

  def apply(input: Int): ValidatedNel[InvalidQuantity, Quantity] =
    input.checkF(_ >= 0)(InvalidQuantity()).map(new Quantity(_)).toValidatedNel

}

final case class OrderDescription private (value: String) extends AnyVal
object OrderDescription {
  def apply(input: String): ValidatedNel[InvalidInput, OrderDescription] =
    input.checkF(_.nonEmpty)(InvalidOrderDescription()).map(new OrderDescription(_)).toValidatedNel
}

final case class StockCode private (value: String) extends AnyVal

object StockCode {

  def apply(input: String): ValidatedNel[InvalidInput, StockCode] =
    input.checkF(_.nonEmpty)(InvalidStockCode()).map(new StockCode(_)).toValidatedNel
}
