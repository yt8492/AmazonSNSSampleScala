package com.yt8492.amazonsnssamplescala

import com.amazonaws.auth.{AWSCredentials, AWSStaticCredentialsProvider}
import com.amazonaws.regions.Regions
import com.amazonaws.services.sns.AmazonSNSClient
import com.amazonaws.services.sns.model.{CreatePlatformEndpointRequest, GetEndpointAttributesRequest, PublishRequest, SetEndpointAttributesRequest}

import scala.collection.mutable
import collection.JavaConverters._
import scala.util.control.NonFatal

object SNSService {
  private val userArns = mutable.Set[String]()
  private val snsClient = AmazonSNSClient.builder()
    .withCredentials(new AWSStaticCredentialsProvider(new AWSCredentials {
      override def getAWSAccessKeyId: String = "access_key_id"

      override def getAWSSecretKey: String = "secret_key"
    }))
    .withRegion(Regions.US_EAST_1)
    .build()
  private val APPLICATION_ARN = "application_arn"

  def createEndpointARN(fcmToken: String): String = {
    try {

      val cpeReq = new CreatePlatformEndpointRequest()
        .withPlatformApplicationArn(APPLICATION_ARN)
        .withToken(fcmToken)
      val cpeRes = snsClient.createPlatformEndpoint(cpeReq)
      val arn = cpeRes.getEndpointArn
      val getAttrReq = new GetEndpointAttributesRequest()
        .withEndpointArn(arn)
      val getAttrRes = snsClient.getEndpointAttributes(getAttrReq)
      println(getAttrRes.getAttributes)
      userArns.add(arn)
      arn
    } catch {
      case NonFatal(t) => {
        t.printStackTrace()
        ""
      }

    }
  }

  def setEndpointAttr(fcmToken: String): Unit = {
    try {
      val attr = Map("Token" -> fcmToken, "Enabled" -> "true").asJava
      val endpointArn = createEndpointARN(fcmToken)
      val setEndpointAttrReq = new SetEndpointAttributesRequest()
        .withEndpointArn(endpointArn)
        .withAttributes(attr)
      snsClient.setEndpointAttributes(setEndpointAttrReq)
    } catch {
      case NonFatal(t) => t.printStackTrace()
    }
  }

  def sendNotification(message: String): Unit = {
    userArns.foreach { arn =>
      try {
        val publishRequest = new PublishRequest()
          .withTargetArn(arn)
          .withMessageStructure("json")
          .withMessage(
            s"""{
               |  "GCM": "{ \\\"notification\\\": { \\\"text\\\": \\\"$message\\\", \\\"title\\\": \\\"タイトル\\\" } }"
               |}""".stripMargin)
        println(publishRequest.getMessage)
        snsClient.publish(publishRequest)
      } catch {
        case NonFatal(f) => f.printStackTrace()
      }
    }
  }
}
