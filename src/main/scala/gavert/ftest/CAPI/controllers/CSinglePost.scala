package gavert.ftest.CAPI.controllers

import com.twitter.finatra.http.Controller
import gavert.ftest.events._


class CSinglePost extends Controller {
  post("/csingle") { tr: Transaction =>
    RichTransaction.fromTransaction(tr)
  }
}
