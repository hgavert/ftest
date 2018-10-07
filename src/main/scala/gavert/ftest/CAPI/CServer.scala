package gavert.ftest.CAPI

import com.twitter.finagle.http.Request
import com.twitter.finagle.http.filter.JsonpFilter
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.CommonFilters
import com.twitter.finatra.http.routing.HttpRouter
import filters._
import controllers._
import modules._

object CServerMain extends CServer

class CServer extends HttpServer {

  override protected def defaultHttpPort: String = ":8080" // I'm used to this...
  override protected def defaultHttpServerName: String = "Gavert CServer Finatra test"

  override val modules = Seq(
    personalOverrideServiceModule
  )

  override def configureHttp(router: HttpRouter): Unit = {
    router
      // Filters are in order and they are composed after the routes
      .filter[CommonFilters] // https://github.com/twitter/finatra/blob/develop/http/src/main/scala/com/twitter/finatra/http/filters/CommonFilters.scala
      //.filter[HeaderContentLengthFilter[Request]] // - not needed anymore
      //.filter[JsonpFilter[Request]] // - doesn't seem to work?
      .filter[GlobalCacheControlFilter[Request]]
      .add[PingController]
      .add[CSimpleGet]
      .add[CSinglePost]
      .add[CPost]
  }
}
