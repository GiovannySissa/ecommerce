package io.gs.ecommerce.errors

abstract class IngestorError(error: Error) extends Exception(error.msg)

final case class ParserError()
  extends IngestorError(
    Error(
      "sample"
    )
  )
