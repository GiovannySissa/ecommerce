package io.gs.ecommerce.generators

import io.gs.ecommerce.domain.InvoiceDate
import org.scalacheck.Gen

trait CsvGenerator extends FieldGenerator {

  def strCsvGen: Gen[List[String]] =
    for {
      str <- orderIdGen
    } yield str :: Nil

  def floatCsvGen: Gen[List[String]] =
    for {
      str <- priceGen
    } yield str :: Nil

  val missedField: Gen[String] = Gen.const("")

  def orderCsvGen: Gen[List[String]] =
    for {
      orderNoStr  <- orderIdGen
      stockCode   <- stockCodeGen
      description <- descriptionGen
      quantity    <- quantityGen
      date        <- dateCsvGen
      unitPrice   <- priceGen
      customerId  <- customerIdGen
      country     <- countryGen
      lineCsv <- Gen.const(
        orderNoStr :: stockCode :: description :: s"$quantity" :: date :: unitPrice :: customerId :: country :: Nil
      )
    } yield lineCsv

  val dateCsvGen: Gen[String] = invoiceDateGen.map(_.format(InvoiceDate.dateTimePattern))

  // todo Generator could give a valid input
  def invalidOrderCsvGen: Gen[List[String]] =
    for {
      orderNoStr  <- Gen.oneOf(orderIdGen, missedField)
      stockCode   <- Gen.oneOf(stockCodeGen, missedField)
      description <- Gen.oneOf(descriptionGen, missedField)
      quantity    <- Gen.oneOf(quantityGen, missedField)
      date        <- Gen.oneOf(dateCsvGen, missedField)
      unitPrice   <- Gen.oneOf(priceGen, missedField)
      customerId  <- Gen.oneOf(customerIdGen, missedField)
      country     <- Gen.oneOf(countryGen, missedField)
      lineCsv <- Gen.const(
        orderNoStr :: stockCode :: description :: s"$quantity" :: date :: unitPrice :: customerId :: country :: Nil
      )
    } yield lineCsv

}
