package gcd

import chisel3._
import chisel3.util.Decoupled

class AdderInputBundle(val w: Int) extends Bundle {
  val in0 = Input(UInt(16.W));
  val in1 = Input(UInt(16.W));
}

class DecoupledAdder(width: Int) extends Module {
  val input = IO(Flipped(Decoupled(new AdderInputBundle(width))));
  val output = IO(Decoupled(UInt(width.W)));

  var bundle = input.deq();
  var x = bundle.in0;
  var y = bundle.in1;

  output.bits := x + y;
  output.valid := true.B;
}
