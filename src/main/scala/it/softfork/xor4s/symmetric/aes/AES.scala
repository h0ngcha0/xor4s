package it.softfork.xor4s.symmetric.aes

import it.softfork.xor4s.utils.Implicits._

object AES {
  val AES_128_ROUNDS = 10
  // val AES_192_ROUNDS = 12
  // val AES_256_ROUNDS = 14

  // AES only supports 128 bit key
  // Input block is 16 bytes (128 bits) long
  def encrypt(input: Array[Byte], key: Array[Array[Byte]]): Array[Byte] = {
    /*
     *  Split input into a matrix, called state, as follows:
     *
     *  |--------+--------+--------+--------|
     *  | S(0,0) | S(0,1) | S(0,2) | S(0,3) |
     *  |--------+--------+--------+--------|
     *  | S(1,0) | S(1,1) | S(1,2) | S(1,3) |
     *  |--------+--------+--------+--------|
     *  | S(2,0) | S(2,1) | S(2,2) | S(2,3) |
     *  |--------+--------+--------+--------|
     *  | S(3,0) | S(3,1) | S(3,2) | S(3,3) |
     *  |--------+--------+--------+--------|
     *
     */
    val state = transpose(input.grouped(4).toArray)

    // In AES with 128 bits block size (16 bytes), there should be 10 transformation rounds (Nr = 10)
    // and 11 (Nr + 1) round key, each of which is 128 bits long.
    addRoundKey(state, key(0))

    for (i <- 1 to AES_128_ROUNDS) {
      SubBytes(state)
      ShiftRows(state)
      MixColumns(state)
      addRoundKey(state, key(i))
    }

    transpose(state).flatten
  }

  def decrypt(input: Array[Byte], key: Array[Array[Byte]]): Array[Byte] = {
    val state = transpose(input.grouped(4).toArray)

    addRoundKey(state, key(AES_128_ROUNDS))

    for (i <- 1 to 9) {
      ShiftRows.inverse(state)
      SubBytes.inverse(state)
      addRoundKey(state, key(AES_128_ROUNDS - i))
      MixColumns.inverse(state)
    }

    ShiftRows.inverse(state)
    SubBytes.inverse(state)
    addRoundKey(state, key(0))
    // No MixColumns.inverse(state) in the last round

    transpose(state).flatten
  }

  private def addRoundKey(state: Array[Array[Byte]], key: Array[Byte]): Array[Array[Byte]] = {
    for (i <- 0 to 3) {
      for (j <- 0 to 3) {
        state(j)(i) = (state(j)(i) ^ key(4 - j)).toByte
      }
    }

    state
  }

  private def transpose(matrix: Array[Array[Byte]]): Array[Array[Byte]] = {
    matrix.head.indices.map(i => matrix.map(_(i))).toArray
  }
}
