package io.gs.ecommerce.events.order

import io.circe.Json

final case class OrderDeleted(action: String, payload: Json) extends OrderEvent

object OrderDeleted {
  val descriptionEvent: String = "order-deleted"
}
