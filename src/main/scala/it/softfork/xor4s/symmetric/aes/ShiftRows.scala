package it.softfork.xor4s.symmetric.aes

object ShiftRows {

  def apply(state: Array[Array[Byte]]): Array[Array[Byte]] = {
    state(1) = shiftLeft(1)(state(1))
    state(2) = shiftLeft(2)(state(2))
    state(3) = shiftLeft(3)(state(3))

    state
  }

  def inverse(state: Array[Array[Byte]]): Array[Array[Byte]] = {
    state(1) = shiftRight(1)(state(1))
    state(2) = shiftRight(2)(state(2))
    state(3) = shiftRight(3)(state(3))

    state
  }

  private def shiftLeft(by: Int)(bytes: Array[Byte]): Array[Byte] = {
    val (first, rest) = bytes.splitAt(by)
    rest ++ first
  }

  private def shiftRight(by: Int)(bytes: Array[Byte]): Array[Byte] = {
    val length = bytes.length
    val (first, rest) = bytes.splitAt(length - by - 1)
    rest ++ first
  }
}
