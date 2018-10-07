package gavert.ftest.events

import org.scalatest.{FunSuite, Matchers}

class RichTransaction_test extends FunSuite with Matchers {

  test("create from Transaction by categorizing it") {
    val trans1 = Transaction("2018-02-01T12:23:12", "FI123", "FI567", "foo ")
    //assert(categorize(trans1) == "foobar")
    val richtrans1 = RichTransaction("2018-02-01T12:23:12", "FI123", "FI567", "foo ", "foobar")

    RichTransaction.fromTransaction(trans1) shouldBe richtrans1

    val trans2 = Transaction("2018-02-01T12:23:12", "FI123", "FI567", "baz")
    //assert(categorize(trans2) == "unknown")
    val richtrans2 = RichTransaction("2018-02-01T12:23:12", "FI123", "FI567", "baz", "unknown")

    RichTransaction.fromTransaction(trans2) shouldBe richtrans2
  }

}
