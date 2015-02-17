package test.akka.http.benchmark

import akka.actor.ActorSystem

object Main extends App {
  println("... START ...")
  implicit val system = ActorSystem()
  new HttpServer().bindServer()
}
