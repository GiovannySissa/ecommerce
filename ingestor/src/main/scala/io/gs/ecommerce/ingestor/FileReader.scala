package io.gs.ecommerce.ingestor

import akka.actor.ActorSystem
import akka.stream.IOResult
import akka.stream.scaladsl._
import akka.util.ByteString

import java.nio.file.{Path, Paths}
import scala.concurrent.Future

final class FileReader private (filePath: Path) {
  implicit val system: ActorSystem = akka.actor.ActorSystem("Repl")

  def read: Future[List[String]] = {
    val x: Source[String, Future[IOResult]] = FileIO
      .fromPath(filePath)
      .via(
        Framing
          .delimiter(ByteString("\n"), 4096, true)
          .map(s => s.utf8String)
      )
    FileIO
      .fromPath(filePath)
      .via(
        Framing
          .delimiter(ByteString("\n"), 4096, true)
          .map(s => s.utf8String)
      )
      .runWith(Sink.collection)
  }

}

object FileReader {
  val path: Path = Paths.get(getClass.getResource("/e-commerce.csv").getPath)

  def apply(filePath: Path): FileReader = new FileReader(filePath)
}
