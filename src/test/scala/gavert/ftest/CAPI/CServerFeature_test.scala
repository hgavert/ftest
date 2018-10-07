package gavert.ftest.CAPI

import com.twitter.finagle.http.Status._
import com.twitter.finatra.http.EmbeddedHttpServer
import com.twitter.inject.server.FeatureTest

class CServerFeature_test extends FeatureTest {
  override val server = new EmbeddedHttpServer(new CServer)

  test("CServer Ping") {
    server.httpGet(path = "/ping", andExpect = Ok, withBody = "pong")
  }


}

