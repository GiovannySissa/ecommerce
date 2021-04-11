package io.gs.ecommerce.events

import io.circe.Json

trait Event {
  def action: String
  def payload: Json
}
