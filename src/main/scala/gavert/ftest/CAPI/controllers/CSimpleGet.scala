package gavert.ftest.CAPI.controllers

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import gavert.ftest.events.Transaction
import gavert.ftest.events.TransactionFuncs._

class CSimpleGet extends Controller {
  get("/csimple") { req: Request =>
    // This is simple and unsafe as we don't check for real object
    // get params of trans
    val time = req.getParam("time", "")
    val account = req.getParam("account", "")
    val otherPartyAccount = req.getParam("other_party_account", "")
    val otherPartyName = req.getParam("other_party_name", "foo ") // this'll match fast ;-)

    val tr = Transaction(time, account, otherPartyAccount, otherPartyName)
    val cat = categorize(tr)

    response.ok(body = cat)
  }
}
