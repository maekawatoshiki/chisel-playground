package gcd

import chisel3._
import chiseltest._
import org.scalatest.freespec.AnyFreeSpec
import chisel3.experimental.BundleLiterals._

class AdderSpec extends AnyFreeSpec with ChiselScalatestTester {

  "Adder" in {
    test(new DecoupledAdder(16)) { dut =>
      dut.input.initSource()
      dut.input.setSourceClock(dut.clock)
      dut.output.initSink()
      dut.output.setSinkClock(dut.clock)

      val testValues = for { x <- 0 to 10; y <- 0 to 10 } yield (x, y)
      val inputSeq = testValues.map { case (x, y) =>
        (new AdderInputBundle(16)).Lit(_.in0 -> x.U, _.in1 -> y.U)
      }
      val resultSeq = testValues.map { case (x, y) => (x + y).U }

      fork {
        dut.input.enqueueSeq(inputSeq)
      }.fork {
        dut.output.expectDequeueSeq(resultSeq)
      }.join()
    }
  }
}
