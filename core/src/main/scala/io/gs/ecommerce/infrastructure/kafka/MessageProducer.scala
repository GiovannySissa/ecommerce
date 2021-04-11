package io.gs.ecommerce.infrastructure.kafka

trait MessageProducer[F[_]] {
  def send(topic: String, key: String, record: Array[Byte]): F[Unit]
}
