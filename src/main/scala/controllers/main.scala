package controllers

import cats.data.Kleisli
import cats.effect.*
import io.circe.Encoder.*
import io.circe.Json.{JObject, obj}
import io.circe.literal.json
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.dsl.io.*
import org.http4s.server.Router
import org.http4s.syntax.all.*
import org.http4s.{HttpRoutes, Request, Response}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import io.circe.syntax.*
import models.Status
import org.http4s.circe.*

object SimpleServer extends IOApp {
  import scala.concurrent.ExecutionContext.global

  val route: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / "length" / str => Ok("")

    case GET -> Root / id  => Ok(IO.fromFuture(IO(Future {
      Status.find(_.code.toString == id).asJson
    })))
  }

  val app: Kleisli[IO, Request[IO], Response[IO]] = Router(
    "/" -> route
  ).orNotFound

  override def run(args: List[String]): IO[ExitCode] =
    BlazeServerBuilder[IO]
      .bindHttp(8080, "localhost")
      .withHttpApp(app)
      .resource
      .useForever
      .as(ExitCode.Success)
}