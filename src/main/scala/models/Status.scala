package models

import framework.util.EnumSupport
import framework.util.EnumSerialization
import cats.effect.*
import io.circe.*
import io.circe.Encoder.AsObject
import io.circe.generic.auto.*
import io.circe.literal.json
import io.circe.syntax.*
import org.http4s.circe.*

enum Status(val code: Short, val label: String) extends EnumSerialization {
  case IS_DELETED extends Status(-1, "Deleted")
  case IS_DRAFT   extends Status(0,  "Draft")
  case IS_ACTIVE  extends Status(1,  "Active")
}
object Status extends EnumSupport[Status] {}