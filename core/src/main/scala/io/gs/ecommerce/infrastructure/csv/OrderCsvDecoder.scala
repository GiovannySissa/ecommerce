package io.gs.ecommerce.infrastructure.csv

import io.gs.ecommerce.domain._

object OrderCsvDecoder {

  implicit val customerCsvDec: CsvFieldDecoder[CustomerId]          = CustomerId(_)
  implicit val invoiceNoCsvDec: CsvFieldDecoder[InvoiceNo]          = InvoiceNo(_)
  implicit val stockCodeCsvDec: CsvFieldDecoder[StockCode]          = StockCode(_)
  implicit val descriptionCsvDec: CsvFieldDecoder[OrderDescription] = OrderDescription(_)

  implicit def quantityCsvDec(implicit intDec: CsvFieldDecoder[Int]): CsvFieldDecoder[Quantity] =
    intDec.decode(_).andThen(Quantity(_))

  implicit def unitPriceCsvDec(implicit
      floatDec: CsvFieldDecoder[Float]
  ): CsvFieldDecoder[UnitPrice] =
    floatDec.decode(_).andThen(UnitPrice(_))

  implicit val invoiceDateCsvDec: CsvFieldDecoder[InvoiceDate] = InvoiceDate(_)
  implicit val countryCsvDec: CsvFieldDecoder[Country]         = Country(_)
}
