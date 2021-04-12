package io.gs.ecommerce.infrastructure.kafka

import scalapb.GeneratedMessage

trait Serializer[A] {
  def serialize(value: A): Array[Byte]
}

object Serializer {

  implicit def protoSerializer[ A <: GeneratedMessage]: Serializer[A] =
    (value: A) => value.toByteArray

}
