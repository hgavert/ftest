package gavert.ftest.scala_misc

import org.scalatest.{FunSuite, Matchers}

class Helpers_test extends FunSuite with Matchers {
  import Helpers._

  test("get1stOrElse") {
    def f_none(x: Int): Option[String] = None
    def f_some(pre: String)(x: Int): Option[String] = Some(pre + x)
    // test these first
    assert(f_none(123) === None)
    assert(f_some("xxx:")(321) === Some("xxx:321"))

    // then check the function
    def list1 = List[Int => Option[String]](
      f_some("1:") _,
      f_some("2:") _,
      f_some("3:") _,
      f_none).toStream
    def list2 = List[Int => Option[String]](
      f_none,
      f_some("2:") _,
      f_some("3:") _,
      f_none).toStream
    def list3 = List[Int => Option[String]](
      f_none,
      f_none,
      f_none,
      f_none).toStream
    def list4 = List[Int => Option[String]]().toStream
    def v = 5

    get1stOrElse(list1, "default")(v) shouldBe "1:5"
    get1stOrElse(list2, "default")(v) shouldBe "2:5"
    get1stOrElse(list3, "default")(v) shouldBe "default"
    get1stOrElse(list4, "default")(v) shouldBe "default"
  }

}
