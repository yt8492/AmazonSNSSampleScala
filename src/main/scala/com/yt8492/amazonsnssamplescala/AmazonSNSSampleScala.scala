package com.yt8492.amazonsnssamplescala

import org.json4s.{DefaultFormats, Formats}
import org.scalatra._
import org.scalatra.json.JacksonJsonSupport

class AmazonSNSSampleScala extends ScalatraServlet with JacksonJsonSupport {

  get("/") {
    views.html.hello()
  }

  post("/user") {
    val user = parsedBody.extract[User]
    SNSService.setEndpointAttr(user.token)
  }

  post("/message") {
    val message = parsedBody.extract[Message]
    SNSService.sendNotification(message.message)
  }

  protected implicit lazy val jsonFormats: Formats = DefaultFormats
}

case class User(val token: String)
case class Message(val message: String)
