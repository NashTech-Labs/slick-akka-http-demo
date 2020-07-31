package com.knoldus.assignment.router

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.knoldus.assignment.{DbConfig, JsonSupport}
import com.knoldus.assignment.model.GetResponse

import scala.concurrent.ExecutionContext.Implicits.global

object GetUserRoute extends JsonSupport {
  val route: Route = {
    path("user") {
      get {
        parameters('id.as[Int]) {
          id => {
            completeWith(instanceOf[GetResponse]) {
              completer => {
                getById(id, completer)
              }
            }
          }
        }
      }
    }
  }

  private def getById(id: Int, completer: GetResponse => Unit) = {
    val res = DbConfig.table.find(id)
    res.foreach(response => {
      val send = GetResponse(response.toList)
      completer(send)
    })
  }
}
