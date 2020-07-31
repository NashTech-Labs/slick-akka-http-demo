package com.knoldus.assignment.router

import akka.http.scaladsl.server.{Directives, Route}

trait BaseRoute extends Directives{
  val route: Route
}
