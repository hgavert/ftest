package gavert.ftest.CAPI

import com.twitter.finagle.http.Request
import com.twitter.finagle.http.filter.JsonpFilter
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.CommonFilters
import com.twitter.finatra.http.routing.HttpRouter
import filters._
import controllers._

object CategorizationServerMain extends CategorizationServer

class CategorizationServer extends HttpServer {
  override def configureHttp(router: HttpRouter): Unit = {
    router
      // Filters are in order and they are composed after the routes
      .filter[CommonFilters] // https://github.com/twitter/finatra/blob/develop/http/src/main/scala/com/twitter/finatra/http/filters/CommonFilters.scala
      .filter[HeaderContentLengthFilter[Request]]
      .filter[JsonpFilter[Request]]
      .filter[GlobalCacheControlFilter[Request]]
      .add[PingController]
  }
}
