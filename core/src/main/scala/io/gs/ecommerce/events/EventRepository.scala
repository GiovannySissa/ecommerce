package io.gs.ecommerce.events

import io.gs.ecommerce.infrastructure.db.postgres.records.events.EventRecord

import java.util.UUID

trait EventRepository[F[_],E <: Event] {
  def save(event: E): F[Unit]
  def getAll(id: UUID):F[Seq[EventRecord]]
}
