package com.memeattic.api;

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.memeattic.api.domain.Domain._

import scala.io.StdIn

object MemeSearchApi {
  import MemeJsonProtocol._
  
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("MemeSearchApi")
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher

    val memes: List[Meme] = List(
      Meme(
        id = "foo",
        punchline = "feels good man",
        moods = List("happy", "glad"),
        theme = List("pepe", "reaction"),
        media = List(
          Media("bar", domain.Domain.MediaType.Image(), "http://www.publicseminar.org/wp-content/uploads/2016/10/SW_Pepe_feelsgoodman.png")
        ),
        keywords = List("frog", "pepe", "happy", "feels good")
      )
    )

    val route =
      pathPrefix("search") {
        (get & parameters('term)) { term =>
          complete(memes)
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
