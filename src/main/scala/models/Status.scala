package models

import framework.util.{EnumSerializable, EnumSupport}
import cats.effect.*
import io.circe.*
import io.circe.Encoder.AsObject
import io.circe.generic.auto.*
import io.circe.literal.json
import io.circe.syntax.*
import org.http4s.circe.*

enum Status(val key: Short, override val value: Option[String]) extends EnumSerializable[Short, String] {
  case IS_DELETED extends Status(-1, Some("Deleted"))
  case IS_DRAFT   extends Status(0,  Some("Draft"))
  case IS_ACTIVE  extends Status(1,  Some("Active"))
}
object Status extends EnumSupport[Status]