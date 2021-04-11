package io.gs.ecommerce.infrastructure.db.postgres.daos.events

import doobie.Meta
import doobie.implicits._
import io.gs.ecommerce.infrastructure.db.postgres.records.events.EventRecord
import doobie.postgres.implicits._
import io.circe.{Json}
import io.circe.parser.parse
import cats.syntax.either._
import org.postgresql.util.PGobject

import java.util.UUID

object EventSQL {

  implicit val jsonMeta: Meta[Json] =
    Meta.Advanced.other[PGobject]("json").timap[Json](
      a => parse(a.getValue).leftMap[Json](e => throw e).merge)(
      a => {
        val o = new PGobject
        o.setType("jsonb")
        o.setValue(a.noSpaces)
        o
      }
    )

  def insert(record: EventRecord): doobie.Update0 =
    sql"""INSERT INTO EVENT_LOG  (ID, ACTION, PAYLOAD, TIMESTAMP )
            VALUES (${record.id}, ${record.action}, ${record.payload}, ${record.timestamp})
          ON CONFLICT DO NOTHING
       """.update

  def getAll(id: UUID): doobie.Query0[EventRecord] =
    sql"""SELECT ID, ACTION, PAYLOAD, TIMESTAMP  FROM EVENT_LOG
            WHERE ID = $id
          ORDER BY TIMESTAMP 
       """.query[EventRecord]
}
