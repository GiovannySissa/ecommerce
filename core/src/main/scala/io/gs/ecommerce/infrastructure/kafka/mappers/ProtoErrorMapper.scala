package io.gs.ecommerce.infrastructure.kafka.mappers

import cats.data.NonEmptyList
import cats.syntax.option._
import io.gs.ecommerce.errors.InvalidInput
import io.gs.ecommerce.infrastructure.kafka.schema.error.{ErrorMsg, SeqErrors}

object ProtoErrorMapper {

  val fromList: NonEmptyList[InvalidInput] => SeqErrors =
    nel =>
      SeqErrors(
        nel.toList.map(fromInvalidInput)
      )

  val fromInvalidInput: InvalidInput => ErrorMsg = err =>
    ErrorMsg(tag = err.getClass.getSimpleName.some, msg = err.getMessage.some)

}
