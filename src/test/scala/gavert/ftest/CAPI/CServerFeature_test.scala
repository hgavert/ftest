package gavert.ftest.CAPI

import com.twitter.finagle.http.Status._
import com.twitter.finatra.http.EmbeddedHttpServer
import com.twitter.inject.server.FeatureTest

class CServerFeature_test extends FeatureTest {
  override val server = new EmbeddedHttpServer(new CServer)

  test("CServer Ping") {
    server.httpGet(path = "/ping", andExpect = Ok, withBody = "pong")
  }


  test("CServer csimple GET") {
    server.httpGet(
      path = "/csimple?time=2018-02-01T12:23:12&account=FI123&other_party_account=FI567&other_party_name=kulta%20ja%20romu",
      andExpect = Ok,
      withBody = "kellot ja korut"
    )
  }

  test("CServer CSinglePost POST") {
    server.httpPost(
      path = "/csingle",
      postBody =
        """
          |{
          |"time": "2018-02-01T12:23:12",
          |"account": "FI123",
          |"other_party_account": "FI567",
          |"other_party_name": "kulta ja romu"
          |}
        """.stripMargin,
      andExpect = Ok,
      withBody =
        """{"time":"2018-02-01T12:23:12",
          |"account":"FI123",
          |"other_party_account":"FI567",
          |"other_party_name":"kulta ja romu",
          |"category":"kellot ja korut"}""".stripMargin.replaceAll("\\n", "")
    )
  }


}

