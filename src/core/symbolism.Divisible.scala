/*
    Symbolism, version [unreleased]. Copyright 2024 Jon Pretty, Propensive OÜ.

    The primary distribution site is: https://propensive.com/

    Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
    file except in compliance with the License. You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software distributed under the
    License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    either express or implied. See the License for the specific language governing permissions
    and limitations under the License.
*/

package symbolism

import language.experimental.captureChecking

import scala.annotation.targetName

object Divisible:
  class Basic[DividendType, DivisorType, ResultType](lambda: (DividendType, DivisorType) => ResultType)
  extends Divisible[DivisorType]:
    type Self = DividendType
    type Result = ResultType

    def divide(dividend: DividendType, divisor: DivisorType): ResultType = lambda(dividend, divisor)

  given Double is Divisible[Double] into Double as double = _/_
  given Float is Divisible[Float] into Float as float = _/_
  given Long is Divisible[Long] into Long as long = _/_
  given Int is Divisible[Int] into Int as int = _/_

  given Short is Divisible[Short] into Short as short =
    (dividend, divisor) => (dividend/divisor).toShort

  given Byte is Divisible[Byte] into Byte as byte =
    (dividend, divisor) => (dividend/divisor).toByte

trait Divisible[-DivisorType]:
  type Self
  type Result
  def divide(dividend: Self, divisor: DivisorType): Result

  extension (dividend: Self)
    @targetName("divide")
    inline infix def / (divisor: DivisorType): Result = divide(dividend, divisor)
