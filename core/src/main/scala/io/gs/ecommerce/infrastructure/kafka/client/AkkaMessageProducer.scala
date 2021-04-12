package io.gs.ecommerce.infrastructure.kafka.client

import akka.actor.ActorSystem
import akka.kafka.ProducerMessage.MultiResultPart
import akka.kafka.scaladsl.Producer
import akka.kafka.{ProducerMessage, ProducerSettings}
import akka.stream.scaladsl.{Sink, Source}
import io.gs.ecommerce.infrastructure.kafka.MessageProducer
import org.apache.kafka.clients.producer.ProducerRecord

import scala.concurrent.{ExecutionContext, Future}

class AkkaMessageProducer private (producerSettings: ProducerSettings[String, Array[Byte]])(implicit
    actorSystem: ActorSystem,
    exc: ExecutionContext
) extends MessageProducer[Future[*]]{
  override def send(topic: String, record: Array[Byte]): Future[Unit] = {
    Source
      .single(new ProducerRecord[String, Array[Byte]](topic, record))
      .map(ProducerMessage.single(_))
  }.via(Producer.flexiFlow(producerSettings))
    .map {
      case ProducerMessage.Result(metadata, message) =>
        s"${metadata.topic}/${metadata.partition} ${metadata.offset}: $message"
      case ProducerMessage.MultiResult(parts, _) =>
        parts
          .map { case MultiResultPart(metadata, record) =>
            s"${metadata.topic}/${metadata.partition} ${metadata.offset}: ${record.value}"
          }
          .mkString(", ")
      case ProducerMessage.PassThroughResult(_) => "passed through"
    }
    .runWith(Sink.foreach(println)) // todo [Optional] use some logger
    .map(_ => ())

}

object AkkaMessageProducer {
  def apply(
      producerSettings: ProducerSettings[String, Array[Byte]]
  )(implicit actorSystem: ActorSystem, exc: ExecutionContext) =
    new AkkaMessageProducer(producerSettings)

}
