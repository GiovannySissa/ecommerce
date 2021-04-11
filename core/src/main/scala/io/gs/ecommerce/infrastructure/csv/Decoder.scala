package io.gs.ecommerce.infrastructure.csv

import cats.data.Validated.Valid
import cats.data.{Validated, ValidatedNel}
import cats.syntax.apply._
import cats.syntax.either._
import io.gs.ecommerce.errors.{InvalidInput, InvalidWithMsg}
import shapeless.{::, Generic, HList, HNil}

trait CsvFieldDecoder[A] {
  def decode(value: String): ValidatedNel[InvalidInput, A]
}

trait CsvLineDecoder[A] {
  def decode(value: List[String]): ValidatedNel[InvalidInput, A]
}

object CsvLineDecoder {
  def apply[A](implicit dec: CsvLineDecoder[A]): CsvLineDecoder[A] = dec

  implicit val hnilDec: CsvLineDecoder[HNil] = _ => Valid(HNil)

  implicit def genericParser[T, R <: HList](implicit
      gen: Generic.Aux[T, R],
      reprParser: CsvLineDecoder[R]
  ): CsvLineDecoder[T] =
    (s: List[String]) => reprParser.decode(s).map(r => gen.from(r))

  implicit def hListDec[H, T <: HList: CsvLineDecoder](implicit
      hDec: CsvFieldDecoder[H],
      tDec: CsvLineDecoder[T]
  ): CsvLineDecoder[H :: T] = {

    case Nil => Validated.invalidNel(InvalidWithMsg("Csv line must have can't be empty"))
    case head +: tail =>
      (
        hDec.decode(head),
        tDec.decode(tail)
      ) mapN (_ :: _)
  }
}

object CsvFieldDecoder {
  def apply[A](implicit dec: CsvFieldDecoder[A]): CsvFieldDecoder[A] = dec

  implicit val stringDecoder: CsvFieldDecoder[String] = Valid(_)

  implicit val floatDecoder: CsvFieldDecoder[Float] = str =>
    Either
      .catchNonFatal(str.toFloat)
      .leftMap(_ => InvalidWithMsg(s"The value $str has not a valid float representation"))
      .toValidatedNel
  implicit val intDecoder: CsvFieldDecoder[Int] = str =>
    Either
      .catchNonFatal(str.toInt)
      .leftMap(_ => InvalidWithMsg(s"The value $str has not a valid int representation"))
      .toValidatedNel

}
