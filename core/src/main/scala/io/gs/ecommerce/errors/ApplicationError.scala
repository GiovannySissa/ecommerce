package io.gs.ecommerce.errors

abstract class ApplicationError(err: Error)
    extends Exception(
      err.msg
    )

final case class AppFailure() extends ApplicationError(Error("Unexpected failure!"))
