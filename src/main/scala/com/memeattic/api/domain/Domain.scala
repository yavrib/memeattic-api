package com.memeattic.api.domain

import spray.json._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport

object Domain {
  object MediaType {
    trait Val
    case class Image() extends Val
    case class Video() extends Val
    case class Website() extends Val
    case class Text() extends Val
  }

  case class Media(id: String, `type`: MediaType.Val, url: String)

  case class Meme(id: String, punchline: String, theme: Seq[String], moods: Seq[String], keywords: Seq[String], media: Seq[Media])
  
  object MemeJsonProtocol extends DefaultJsonProtocol with SprayJsonSupport {
    implicit object MediaTypeFormatter extends RootJsonFormat[MediaType.Val] {
      def write(mediaType: MediaType.Val): JsString = mediaType match {
        case MediaType.Image() => JsString("image")
        case MediaType.Video() => JsString("video")
        case MediaType.Website() => JsString("website")
        case MediaType.Text() => JsString("text")
      }
      
      def read(json: JsValue) = json.convertTo[String] match {
        case "image" => MediaType.Image()
        case "video" => MediaType.Video()
        case "website" => MediaType.Website()
        case "text" => MediaType.Text()
        case s => throw new Exception(f"Unknown media type $s")
      }
    }
    
    implicit val mediaFormatter = jsonFormat3(Media)
    implicit val memeFormatter = jsonFormat6(Meme)
  }
}
