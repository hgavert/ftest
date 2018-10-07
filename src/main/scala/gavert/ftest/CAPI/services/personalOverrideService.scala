package gavert.ftest.CAPI.services

import gavert.ftest.events.Transaction

class personalOverrideService {
  // Actually, should be personal map
  val sep = ":"
  def receiverKey(tr: Transaction) = tr.account + sep + tr.otherPartyAccount
  def transactionKey(tr: Transaction) = tr.account + sep + tr.time

  import scala.collection.immutable._
  var store = new HashMap[String, String]()

  def setReceiver(tr: Transaction, cat: String) = {
    store += (receiverKey(tr) -> cat)
  }
  def getReceiver(tr: Transaction) = store.get(receiverKey(tr))
  def getTransaction(tr: Transaction) = store.get(transactionKey(tr))
  def get(tr: Transaction) = {
    getTransaction(tr) match {
      case Some(s) => Some(s)
      case None => getReceiver(tr)
    }
  }

  // should this in the tests
  //setReceiver(new Transaction("2018-02-01T12:23:12", "FI123", "FI567", "kulta ja romu"), "override")

}
