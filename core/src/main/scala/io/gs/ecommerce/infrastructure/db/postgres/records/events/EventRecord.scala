package io.gs.ecommerce.infrastructure.db.postgres.records.events

import io.circe.Json
import io.gs.ecommerce.events.order.OrderEvent

import java.time.{ZoneOffset, ZonedDateTime}
import java.util.UUID

final case class EventRecord(id: UUID, action: String, payload: Json, timestamp: ZonedDateTime)

object EventRecord {
  val from: OrderEvent => EventRecord = event =>
    EventRecord(
      id = UUID.randomUUID(),
      action = event.action,
      payload = event.payload,
      timestamp = ZonedDateTime.now(ZoneOffset.UTC)
    )
}
