package com.knoldus.assignment

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.knoldus.assignment.model.{GetResponse, User}
import spray.json.DefaultJsonProtocol

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val userFormat = jsonFormat2(User)
  implicit val getresponseFormat = jsonFormat1(GetResponse)
}
