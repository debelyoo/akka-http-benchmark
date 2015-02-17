package test.akka.http.benchmark

import akka.actor.ActorSystem
import akka.http.Http
import akka.http.model.HttpMethods._
import akka.http.model.{Uri, HttpResponse, HttpRequest}
import akka.stream.FlowMaterializer

class HttpServer(h: Option[String] = None, p: Option[Int] = None)(implicit system: ActorSystem) {
  implicit private val materializer = FlowMaterializer()
  private val conf = system.settings.config
  private val host = h.getOrElse(conf.getString("server.ip"))
  private val port = p.getOrElse(conf.getInt("server.port"))

  private def handleGetRequestSynch: HttpRequest => HttpResponse = {
    case HttpRequest(GET, Uri.Path("/plaintext"), _, _, _)  => HttpResponse(200, entity = "PONG!")
    case _: HttpRequest                                => HttpResponse(404, entity = "Unknown resource!")
  }

  def bindServer() {
    val serverBinding = Http(system).bind(interface = host, port = port, backlog = 50000)

    serverBinding.connections.foreach { connection => // foreach materializes the source
      //info(s"Accepted new connection from ${connection.remoteAddress}")

      connection handleWithSyncHandler handleGetRequestSynch
    }
    println(s"Server is started [${host}:${port}] ...")
  }
}
