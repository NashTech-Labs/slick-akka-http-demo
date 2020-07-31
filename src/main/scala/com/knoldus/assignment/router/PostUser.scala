package com.knoldus.assignment.router

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.knoldus.assignment.model.User
import com.knoldus.assignment.{DbConfig, JsonSupport}

import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global

object PostUserRoute extends JsonSupport {
  val route: Route = {
    post {
      path("user") {
        entity(as[User]) {
          user => {
            completeWith(instanceOf[User])
            {
              completer => insert(user , completer)
            }
          }
        }
      }
    }
  }

  private def insert(user: User , complete : User => Unit) = {
    val result = DbConfig.table.insert(user)
    result onComplete {
      case Success(value) => complete(user)
      case Failure(exception) => complete(User(-1 , ""))
    }
  }
}
