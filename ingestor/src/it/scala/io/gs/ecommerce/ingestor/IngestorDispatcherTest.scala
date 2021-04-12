package io.gs.ecommerce.ingestor

import akka.actor.ActorSystem
import cats.effect.{IO, Resource}
import io.circe.config.parser
import io.gs.ecommerce.infrastructure.kafka.KafkaConfig
import io.gs.ecommerce.infrastructure.kafka.client.{AkkaKafka, AkkaMessageProducer}
import io.gs.ecommerce.suite.ResourceSuite

import java.nio.file.{Path, Paths}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

class IngestorDispatcherTest extends ResourceSuite[IngestorDispatcher] {

  val path: Path                   = Paths.get(getClass.getResource("/e-commerce-test.csv").getPath)
  implicit val system: ActorSystem = akka.actor.ActorSystem("ingestor-it-test")
  override def resources: Resource[IO, IngestorDispatcher] =
    for {
      conf <- Resource.liftF(parser.decodePathF[IO, KafkaConfig]("app.kafka"))
      adjustedConf = useKafkaContainer(conf)
      fileReader <- Resource.liftF(IO(FileReader(path)))
      producer <- Resource.liftF(
        IO(AkkaMessageProducer(AkkaKafka.producerSettings(system)(adjustedConf)))
      )
    } yield new IngestorDispatcher(fileReader)(producer)(conf)

  withEnv { ingestor =>
    test("Send messages to Kafka from file") {
      ingestor.parse onComplete {
        case Success(value)     => println(value)
        case Failure(exception) => fail(exception.getMessage)
      }
    }
  }
}
