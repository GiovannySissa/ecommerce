package io.gs.ecommerce.generators

import org.scalacheck.Gen

import java.time.LocalDateTime

trait FieldGenerator {

  val orderIdGen: Gen[String] = for {
    n       <- Gen.chooseNum(1, 100)
    orderId <- Gen.listOfN(n, Gen.alphaNumChar)
  } yield orderId.mkString("")

  val priceGen: Gen[String] = for {
    n <- Gen.choose(1.0f, 100.0f)
  } yield n.toString

  val stockCodeGen: Gen[String] = for {
    n       <- Gen.chooseNum(1, 30)
    orderId <- Gen.listOfN(n, Gen.alphaNumChar)
  } yield orderId.mkString("")

  val descriptionGen: Gen[String] = for {
    n       <- Gen.chooseNum(1, 1000)
    orderId <- Gen.listOfN(n, Gen.alphaNumChar)
  } yield orderId.mkString("")

  val quantityGen: Gen[Int] =
    Gen.choose(1, 999)

  val invoiceDateGen: Gen[LocalDateTime] =
    Gen.const(LocalDateTime.now())

  val customerIdGen: Gen[String] = for {
    n       <- Gen.chooseNum(1, 10)
    orderId <- Gen.listOfN(n, Gen.alphaNumChar)
  } yield orderId.mkString("")

  val countryGen: Gen[String] = for {
    n       <- Gen.chooseNum(1, 10)
    orderId <- Gen.listOfN(n, Gen.alphaNumChar)
  } yield orderId.mkString("")
}
