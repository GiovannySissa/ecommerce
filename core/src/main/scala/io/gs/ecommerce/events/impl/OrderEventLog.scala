package io.gs.ecommerce.events.impl

import cats.effect.Sync
import cats.syntax.functor._
import doobie.implicits._
import doobie.util.transactor.Transactor
import io.gs.ecommerce.events.EventRepository
import io.gs.ecommerce.events.order.OrderEvent
import io.gs.ecommerce.infrastructure.db.postgres.daos.events.EventSQL
import io.gs.ecommerce.infrastructure.db.postgres.records.events.EventRecord

import java.util.UUID

final class OrderEventLog[F[_]: Sync] private (xa: Transactor[F])
    extends EventRepository[F, OrderEvent] {
  override def save(event: OrderEvent): F[Unit] =
    EventSQL.insert(EventRecord.from(event)).run.transact(xa).void

  override def getAll(id: UUID): F[Seq[EventRecord]] =
    EventSQL.getAll(id).to[Seq].transact(xa)
}

object OrderEventLog {
  def apply[F[_]: Sync](xa: Transactor[F]): EventRepository[F, OrderEvent] =
    new OrderEventLog[F](xa)
}
