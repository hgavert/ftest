package gavert.ftest.CAPI.filters

import com.google.inject.Singleton
import com.twitter.finagle.{Service, SimpleFilter}
import com.twitter.finagle.http.{Request, Response}
import com.twitter.util.Future

// This was needed for AWS ELB - still?

@Singleton
class HeaderContentLengthFilter[REQUEST <: Request] extends SimpleFilter[REQUEST, Response] {
  def apply(request: REQUEST, service: Service[REQUEST, Response]): Future[Response] = {
    service(request) onSuccess { response => response.headerMap("Content-Length") = "" + response.getLength() }
  }
}
