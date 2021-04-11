package io.gs.ecommerce.infrastructure.csv

import cats.data.Validated
import io.gs.ecommerce.domain.Order
import io.gs.ecommerce.generators.CsvGenerator
import org.scalatest.propspec.AnyPropSpec
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks.forAll

class CsvDecoderTest extends AnyPropSpec with CsvGenerator {

  import CsvFieldDecoder._
  import CsvLineDecoder._
  import OrderCsvDecoder._

  case class Foo(string: String)
  case class Bar(value: Float)

  property("decode a case class with String field") {
    val decoder = CsvLineDecoder[Foo]
    forAll(strCsvGen) { in =>
      assert(decoder.decode(in).isValid)
    }
  }

  property("decode a case class with Long field") {
    val decoder = CsvLineDecoder[Bar]
    forAll(floatCsvGen) { in =>
      assert(decoder.decode(in).isValid)
    }
  }

  property("decode a order") {
    val decoder = CsvLineDecoder[Order]
    forAll(orderCsvGen) { in =>
      assert(decoder.decode(in).isValid)
    }
  }

  property("Can't decode an invalid order with incomplete input") {
    val decoder = CsvLineDecoder[Order]
    forAll(invalidOrderCsvGen) { in =>
      val x = decoder.decode(in)
      x match {
        case Validated.Valid(a) => println(a)
        case Validated.Invalid(e) =>
          e.toList.foreach(s => println(s"${s.getClass.getName} ---- ${s.getClass.getSimpleName} "))
      }
      assert(decoder.decode(in).isInvalid)
    }
  }
}
