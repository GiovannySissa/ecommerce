package io.gs.ecommerce.infrastructure.kafka.mappers

import cats.syntax.option._
import com.google.protobuf.timestamp.Timestamp
import io.gs.ecommerce.domain.Order
import io.gs.ecommerce.infrastructure.kafka.schema.event.OrderCreated

import java.time.{LocalDateTime, ZoneOffset}

object OrderProtoMapper {

  val fromDomain: Order => OrderCreated =
    order =>
      OrderCreated(
        invoiceNo = order.invoiceNo.value.some,
        stockCode = order.stockCode.value.some,
        orderDescription = order.description.value.some,
        quantity = order.quantity.value.some,
        invoiceDate = fromZonedDateTime(order.invoiceDate.date).some,
        unitPrice = order.unitPrice.value.some,
        customerId = order.customerId.value.some,
        country = order.country.value.some
      )

  val fromZonedDateTime: LocalDateTime => Timestamp = date =>
    Timestamp(date.toEpochSecond(ZoneOffset.UTC))
}
