package io.gs.ecommerce.infrastructure.kafka.client

import akka.actor.ActorSystem
import akka.kafka.{ConsumerSettings, ProducerSettings}
import io.gs.ecommerce.infrastructure.kafka.KafkaConfig
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.{ByteArrayDeserializer, ByteArraySerializer, StringDeserializer, StringSerializer}

object AkkaKafka {

  def consumerSettings(
      actorSystem: ActorSystem
  )(config: KafkaConfig): ConsumerSettings[String, Array[Byte]] =
    ConsumerSettings(actorSystem, new StringDeserializer, new ByteArrayDeserializer)
      .withBootstrapServers(config.broker.host.ip)
      .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, config.consumer.autoOffset.value)

  def producerSettings(
      actorSystem: ActorSystem
  )(config: KafkaConfig): ProducerSettings[String, Array[Byte]] =
    ProducerSettings(actorSystem, new StringSerializer, new ByteArraySerializer)
      .withBootstrapServers(
        config.broker.host.ip
      )


}
