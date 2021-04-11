package io.gs.ecommerce

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.scaladsl._
import io.gs.ecommerce.ingestor.FileReader

import java.nio.file.{Path, Paths}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

class FileReaderTest extends TestSuite {

  val path: Path = Paths.get(getClass.getResource("/e-commerce-test.csv").getPath)

  test("Read a file successful") {
    FileReader(path).read.onComplete {
      case Failure(exception) => fail(exception.getMessage)
      case Success(value)     => value.size shouldBe 3
    }
  }

  test("Sample akka streams") {
    implicit val system: ActorSystem       = akka.actor.ActorSystem("Repl")
    val source: Source[Int, NotUsed]       = Source.single(1)
    val flow: Flow[Int, String, NotUsed]   = Flow.fromFunction((i: Int) => s"num $i")
    val sink: Sink[String, Future[String]] = Sink.head[String]

    val result: Future[String] = source.via(flow).runWith(sink)
    result.foreach(println)
  }
}
