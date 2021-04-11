package io.gs.ecommerce.errors

abstract class InvalidInput(err: Error) extends DomainError(err)

final case class InvalidInvoiceNumber()
    extends InvalidInput(
      Error(
        "The invoice number is invalid"
      )
    )
final case class InvalidInvoiceDate()
    extends InvalidInput(
      Error(
        "Invoice date is invalid"
      )
    )
final case class InvalidStockCode()
    extends InvalidInput(
      Error(
        "Stock code can't be empty"
      )
    )

final case class InvalidOrderDescription()
    extends InvalidInput(
      Error("Invalid order description!")
    )

final case class InvalidQuantity()
    extends InvalidInput(
      Error(
        "Invalid quantity"
      )
    )

final case class InvalidUnitPrice()
    extends InvalidInput(
      Error(
        "Unit price can't be less than $0.0"
      )
    )

final case class InvalidCustomerId()
    extends InvalidInput(
      Error(
        "Invalid customerId"
      )
    )

final case class InvalidCountry()
    extends InvalidInput(
      Error(
        "Empty country is not allowed"
      )
    )

final case class InvalidWithMsg(msg: String)
    extends InvalidInput(
      Error(
        msg
      )
    )
