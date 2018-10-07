package gavert.ftest.CAPI.filters

import com.twitter.finagle.{Service, SimpleFilter}
import com.twitter.finagle.http.{Request, Response}
import com.twitter.util.Future

// we don't want no caching
class GlobalCacheControlFilter[REQUEST <: Request] extends SimpleFilter[REQUEST, Response] {
  def apply(request: REQUEST, service: Service[REQUEST, Response]): Future[Response] = {
    service(request) onSuccess { response =>
      response.headerMap("Expires") = "0"
      response.headerMap("Cache-Control") = "max-age=0, no-cache"
    }
  }
}
