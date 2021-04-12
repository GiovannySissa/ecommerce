package io.gs.ecommerce.suite

import cats.effect.concurrent.Deferred
import cats.effect.{ContextShift, IO, Resource, Timer}
import com.dimafeng.testcontainers.{
  ForAllTestContainer,
  KafkaContainer,
  MultipleContainers,
  PostgreSQLContainer
}
import io.gs.ecommerce.infrastructure.kafka.{Broker, KafkaConfig, KafkaHost}
import org.scalatest.BeforeAndAfterAll

import scala.concurrent.ExecutionContext

trait ResourceSuite[A] extends TestSuite with BeforeAndAfterAll with ForAllTestContainer {

  private lazy val postgresContainer = PostgreSQLContainer()
  private lazy val kafkaContainer    = KafkaContainer()

  override val container: MultipleContainers = MultipleContainers(kafkaContainer, postgresContainer)
  implicit val cs: ContextShift[IO]          = IO.contextShift(ExecutionContext.global)
  implicit val timer: Timer[IO]              = IO.timer(ExecutionContext.global)
  def resources: Resource[IO, A]

  private[this] var res: A            = _
  private[this] var cleanUp: IO[Unit] = _
  private[this] val latch             = Deferred[IO, Unit].unsafeRunSync()

  //todo postgresContainer.container.getJdbcUrl
  val (r, h) = resources.allocated.unsafeRunSync()
  def useKafkaContainer: KafkaConfig => KafkaConfig = { conf =>
    val host = KafkaHost(kafkaContainer.bootstrapServers)
    conf.copy(Broker(host))
  }
  override def beforeAll(): Unit = {
    super.beforeAll()
    res = r
    cleanUp = h
    latch.complete(()).unsafeRunSync()

  }

  override def afterAll(): Unit = {
    cleanUp.unsafeRunSync()
    super.afterAll()
  }
  def withEnv(f: (=> A) => Unit): Unit = f {
    latch.get.unsafeRunSync()
    res
  }

}
