package io.gs.ecommerce.infrastructure.kafka

trait MessageProducer[F[_]] {
  def send(topic: String, key: String, record: Array[Byte]): F[Unit]
}
object MessageProducer {
  def apply[F[_]: MessageProducer]: MessageProducer[F] = implicitly
}
