package io.gs.ecommerce.infrastructure.kafka
import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

final case class KafkaHost(ip: String) extends AnyVal
final case class Broker(host: KafkaHost)
final case class AutoOffset(value: String)
final case class Producer()
final case class Consumer(autoOffset: AutoOffset)

trait Topic {
  def name: String
}

final case class EventsTopic(name: String) extends Topic
final case class PoisonTopic(name: String) extends Topic

final case class Topics(events: EventsTopic, poison: PoisonTopic)

final case class KafkaConfig(broker: Broker, producer: Producer, consumer: Consumer, topics: Topics)

object KafkaConfig {
  implicit val kafkaConfigDec: Decoder[KafkaConfig] = deriveDecoder[KafkaConfig]
  implicit val brokerDec: Decoder[Broker]           = deriveDecoder[Broker]
  implicit val kafkaHostDec: Decoder[KafkaHost]     = deriveDecoder[KafkaHost]
  implicit val producerDec: Decoder[Producer]       = deriveDecoder[Producer]
  implicit val consumerDec: Decoder[Consumer]       = deriveDecoder[Consumer]
  implicit val autoOffsetDec: Decoder[AutoOffset]   = deriveDecoder[AutoOffset]
  implicit val topicsDec: Decoder[Topics]           = deriveDecoder[Topics]
  implicit val eventsTopicDec: Decoder[EventsTopic] = deriveDecoder[EventsTopic]
  implicit val poisonTopicDec: Decoder[PoisonTopic] = deriveDecoder[PoisonTopic]
}
