package framework.util

import io.circe.Encoder
import io.circe.literal.json
import io.circe.generic.auto._
import org.http4s.circe.CirceEntityCodec._
import scala.reflect.ClassTag
trait EnumSerialization {
  val code: Short
  val label: String
}
trait EnumSupport[T <: EnumSerialization] { self =>
  lazy val enums = self.getClass.getDeclaredMethod("values").invoke(self).asInstanceOf[Array[T]]
  implicit val encode: Encoder[T] = (a: T) => Encoder.encodeString(a.toString)
  def find(t: T => Boolean): Option[T] = enums.find(t)
  def filter(t: T => Boolean): Seq[T]  = enums.filter(t)
  def indexOf[M <: T](member: M): Int  = enums.indexOf(member)
  def map[B](t: T => B)(implicit ct: ClassTag[B]): Seq[B] = enums.map(t).toSeq
  def withName(name: String): T =
    find(_.label == name).getOrElse(throw new NoSuchElementException(s"$name is not a member of Enum $self"))
}
