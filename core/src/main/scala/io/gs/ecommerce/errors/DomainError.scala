package io.gs.ecommerce.errors

abstract class DomainError(err: Error) extends Exception(err.msg)



