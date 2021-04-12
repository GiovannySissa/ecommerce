package io.gs.ecommerce.ingestor

import akka.actor.ActorSystem
import akka.stream.scaladsl.{Flow, Sink}
import akka.{Done, NotUsed}
import cats.data.Validated
import io.gs.ecommerce.domain.Order
import io.gs.ecommerce.infrastructure.csv.{CsvLineDecoder, OrderCsvDecoder}
import io.gs.ecommerce.infrastructure.kafka.mappers.OrderProtoMapper._
import io.gs.ecommerce.infrastructure.kafka.mappers.ProtoErrorMapper._
import io.gs.ecommerce.infrastructure.kafka.{KafkaConfig, MessageProducer}

import scala.concurrent.Future

final class IngestorDispatcher(reader: FileReader)(producer: MessageProducer[Future])(
    kafkaConfig: KafkaConfig
)(implicit actorSystem: ActorSystem) {
  import OrderCsvDecoder._

  private val lineFlow: Flow[String, List[String], NotUsed] =
    Flow.fromFunction((line: String) => line.split(",").toList)
  val decoder: CsvLineDecoder[Order] = CsvLineDecoder[Order]

  def parse: Future[Done] = {
    reader.read
      .via(lineFlow)
      .map(decoder.decode)
      .map {
        case Validated.Valid(order) =>
          producer.send(kafkaConfig.topics.events.name, fromDomain(order).toByteArray)
        case Validated.Invalid(errors) =>
          producer.send(kafkaConfig.topics.poison.name, fromList(errors).toByteArray)
      }
      .runWith(Sink.ignore)
  }
}
