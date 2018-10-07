package gavert.ftest.scala_misc

object Helpers {

  def get1stOrElse[I,O](funStream: Stream[I => Option[O]], default: O)(value: I) =
    funStream.map(f => f(value)).collect{ case Some(s) => s }.headOption match {
      case Some(s) => s
      case None => default
    }

  def microBenchmark(block: => Unit, repeat: Int = 1, samples: Int = 3, warmup: Boolean = true) = {
    @scala.annotation.tailrec
    def repeatOp(t: Int, code: => Unit) {
      if (t > 0) {
        code; repeatOp(t - 1, code)
      }
    }

    def timeOp(codeBlock: => Unit) = {
      val start = System.currentTimeMillis()
      codeBlock
      System.currentTimeMillis() - start
    }

    if (warmup) {
      println("Warming up...")
      repeatOp(repeat, block)
    }
    println("Running...")
    var res = Array[Long]()
    var s = 1
    while (s <= samples) {
      val thisTime = timeOp(repeatOp(repeat, block))
      res = res :+ thisTime
      println(s + ": " + thisTime + " ms")
      s += 1
    }
    val min = res.min
    val max = res.max
    val mean = res.sum / res.length
    val median = res.sortWith(_ < _)(res.length / 2)
    println("mean: " + mean + " ms (with min: " + min + " ms and max: " + max + " ms)")
    (min, mean, max)
    }

}
