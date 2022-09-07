package framework.util

import io.circe.{Encoder, KeyEncoder}

import scala.reflect.ClassTag

trait EnumSerializable[K, V] {
  self =>
  val key: K
  val value: Option[V] = None
}

trait EnumSupport[T <: EnumSerializable[_, _]] {
  self =>
  lazy val enums = self.getClass.getDeclaredMethod("values").invoke(self).asInstanceOf[Array[T]]
  implicit val encodeJson: Encoder[T] = (a: T) => Encoder.encodeJsonObject(io.circe.JsonObject.fromMap(Map("key" -> io.circe.Json.fromLong(a.key.toString.toLong), "value" -> io.circe.Json.fromString({
    if (a.value.nonEmpty) a.value.head.toString else ""
  }))))

  def find(t: T => Boolean): Option[T] = enums.find(t)

  def filter(t: T => Boolean): Seq[T] = enums.filter(t)

  def indexOf[M <: T](member: M): Int = enums.indexOf(member)

  def map[B](t: T => B)(implicit ct: ClassTag[B]): Seq[B] = enums.map(t).toSeq

  def withName(name: String): T =
    find(_.value contains name).getOrElse(throw new NoSuchElementException(s"$name is not a member of Enum $self"))
}
