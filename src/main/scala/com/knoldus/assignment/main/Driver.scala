package com.knoldus.assignment.main

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.knoldus.assignment.{DbConfig, JsonSupport}
import com.knoldus.assignment.router.{GetUser, PostUser}
import com.typesafe.config.ConfigFactory

import scala.concurrent.Future
import scala.io.StdIn
import scala.util.{Failure, Success}

object Driver extends App with JsonSupport
{
    import DbConfig._

    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher

    val dbStatus = table.create()
    dbStatus onComplete {
        case Success(_) =>
        {
            val route: Route = GetUser.route ~ PostUser.route

            // `route` will be implicitly converted to `Flow` using `RouteResult.route2HandlerFlow`
            val ip = ConfigFactory.load().getString("host")
            val port = ConfigFactory.load().getString("port").toInt
            val bindingFuture = Http().bindAndHandle(route, ip, port)
            //    val bindingFuture = Http().bindAndHandle(route, "localhost", 8100)
            StdIn.readLine() // let it run until user presses return
            bindingFuture
              .flatMap(_.unbind()) // trigger unbinding from the port
              .onComplete(_ => system.terminate()) // and shutdown when done
        }
        case Failure(exception) => println(s"Cannot create the table. [$exception]")
    }

}
