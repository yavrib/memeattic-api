package com.memeattic.api;

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import scala.io.StdIn

object MemeSearchApi {
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("MemeSearchApi")
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher

    val route =
      pathPrefix("hello") {
        (get & parameters('name?)) { name =>
          name match {
            case Some(name) => complete(s"Hello, ${name}!")
            case None => complete(s"Hello world!")
          }
        }
      }

    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}
