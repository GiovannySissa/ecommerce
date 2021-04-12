package io.gs.ecommerce.infrastructure.csv

import io.gs.ecommerce.domain.Order
import io.gs.ecommerce.generators.CsvGenerator
import io.gs.ecommerce.suite.TestSuite

class CsvDecoderTest extends TestSuite with CsvGenerator {

  import CsvFieldDecoder._
  import CsvLineDecoder._
  import OrderCsvDecoder._

  case class Foo(string: String)
  case class Bar(value: Float)

  test("decode a case class with String field") {
    val decoder = CsvLineDecoder[Foo]
    forAll(strCsvGen) { in =>
      assert(decoder.decode(in).isValid)
    }
  }

  test("decode a case class with Long field") {
    val decoder = CsvLineDecoder[Bar]
    forAll(floatCsvGen) { in =>
      assert(decoder.decode(in).isValid)
    }
  }

  test("decode a order") {
    val decoder = CsvLineDecoder[Order]
    forAll(orderCsvGen) { in =>
      assert(decoder.decode(in).isValid)
    }
  }

  test("Can't decode an invalid order with incomplete input") {
    val decoder = CsvLineDecoder[Order]
    forAll(invalidOrderCsvGen) { in =>
      if (in.forall(_.nonEmpty)) {
        assert(true) // because exist a valid generator
      } else {
        assert(decoder.decode(in).isInvalid)
      }
    }
  }
}
