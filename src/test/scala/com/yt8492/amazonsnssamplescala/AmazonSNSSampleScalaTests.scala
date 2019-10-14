package com.yt8492.amazonsnssamplescala

import org.scalatra.test.scalatest._

class AmazonSNSSampleScalaTests extends ScalatraFunSuite {

  addServlet(classOf[AmazonSNSSampleScala], "/*")

  test("GET / on AmazonSNSSampleScala should return status 200") {
    get("/") {
      status should equal (200)
    }
  }

}
