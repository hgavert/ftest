package gavert.ftest.CAPI.controllers


import com.google.inject.Inject
import com.twitter.finatra.http.Controller
import gavert.ftest.CAPI.services._
import gavert.ftest.events._


class CPost @Inject() (overrides: personalOverrideService) extends Controller{
  post("/cpost") { tr: Transaction =>

    overrides.get(tr) match {
      case Some(cat) => RichTransaction(tr.time, tr.account, tr.otherPartyAccount, tr.otherPartyName, cat)
      case None => RichTransaction.fromTransaction(tr)
    }
  }
}
