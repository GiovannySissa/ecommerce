package io.gs.ecommerce.events.order

import io.circe.Json

final case class OrderCreated (action: String, payload: Json) extends OrderEvent

object OrderCreated {
  val descriptionEvent: String = "order-created"

}