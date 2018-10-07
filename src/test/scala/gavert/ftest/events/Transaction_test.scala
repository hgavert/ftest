package gavert.ftest.events

import org.scalatest.{FunSuite, Matchers}

class Transaction_test extends FunSuite with Matchers{
  import TransactionFuncs._

  test("categorize 1") {
    val trans1 = Transaction("2018-02-01T12:23:12", "FI123", "FI567", "foo ")
    assert(categorize(trans1) == "foobar")

    val trans2 = Transaction("2018-02-01T12:23:12", "FI123", "FI567", "baz")
    assert(categorize(trans2) == "unknown")
  }

  test("categorize 2") {
    val toTest = List(
      (Transaction("2018-02-01T12:23:12", "FI123", "FI567", "valion eläkekassa "), "asuminen"),
      (Transaction("2018-02-01T12:23:12", "FI123", "FI567", "tenava "), "lastenhoito"),
      (Transaction("2018-02-01T12:23:12", "FI123", "FI567", "nordnet "), "sijoittaminen"),
      (Transaction("2018-02-01T12:23:12", "FI123", "FI567", "jack jones "), "vaatetus"),
      (Transaction("2018-02-01T12:23:12", "FI123", "FI567", "veikkaus "), "rahapelit"),
      (Transaction("2018-02-01T12:23:12", "FI123", "FI567", "kulta ja romu"), "kellot ja korut"),
      (Transaction("2018-02-01T12:23:12", "FI123", "FI567", "kampaaja "), "kauneus, liikunta ja hyvinvointi")
    )
    toTest foreach { case (t, c) => categorize(t) shouldBe c }
  }

  test("categorize speed in ordering") {
    val t1 = Transaction("2018-02-01T12:23:12", "FI123", "FI567", "vuokra ")
    val t2 = Transaction("2018-02-01T12:23:12", "FI123", "FI567", "kampaaja ")

    import gavert.ftest.scala_misc.Helpers._
    val time1 = microBenchmark({categorize(t1)}, 10000, 3, true)
    val time2 = microBenchmark({categorize(t2)}, 10000, 3, true)

    time1._2 should be <= time2._2
  }


  test("createMatcherFunctions") {
    val test = createMatcherFunctions("syke", "liikunta")
    test("foo") shouldBe None
    test("syke") shouldBe None // really? well, definition currently
    test("syke ") shouldBe Some("liikunta")
    test(" syke ") shouldBe Some("liikunta")
    test("foo syke") shouldBe None // really??
    test("foo syke ") shouldBe Some("liikunta")
    test("foo syke bar") shouldBe Some("liikunta")
    test("sykesyke") shouldBe None // really?
    test("sykesyke ") shouldBe Some("liikunta")
    test("syke syke") shouldBe Some("liikunta")
    // continue...
  }

  test("createMatcherFunctions2") {
    val test = createMatcherFunctions2("syke", "liikunta")
    test("foo") shouldBe None
    test("syke") shouldBe Some("liikunta") // really? well, definition currently
    test("syke ") shouldBe Some("liikunta")
    test(" syke ") shouldBe Some("liikunta")
    test("foo syke") shouldBe Some("liikunta") // really??
    test("foo syke ") shouldBe Some("liikunta")
    test("foo syke bar") shouldBe Some("liikunta")
    test("sykesyke") shouldBe Some("liikunta") // really?
    test("sykesyke ") shouldBe Some("liikunta")
    test("syke syke") shouldBe Some("liikunta")
    // continue...
  }

}
