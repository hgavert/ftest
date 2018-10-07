package gavert.ftest.events

import gavert.ftest.events.TransactionFuncs.categorize

object RichTransaction {
  def fromTransaction(tr: Transaction): RichTransaction = {
    val cat = categorize(tr)
    RichTransaction(tr.time,
      tr.account,
      tr.otherPartyAccount,
      tr.otherPartyName,
      cat)
  }
}

case class RichTransaction(time: String,
                           account: String,
                           otherPartyAccount: String,
                           otherPartyName: String,
                           category: String)
