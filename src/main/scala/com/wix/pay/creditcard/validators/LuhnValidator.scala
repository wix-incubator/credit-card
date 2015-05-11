/*      __ __ _____  __                                              *\
**     / // // /_/ |/ /          Wix                                 **
**    / // // / /|   /           (c) 2006-2015, Wix LTD.             **
**   / // // / //   |            http://www.wix.com/                 **
**   \__/|__/_//_/| |                                                **
\*                |/                                                 */
package com.wix.pay.creditcard.validators


/** A ''Luhn'' Credit Card [[Validator]].
  *
  * The ''Luhn Algorithm'' is a simple checksum formula used to validate a variety of identification numbers, such as
  * credit card numbers. It is in wide use today, and specified in ISO/IEC 7812-1.
  * It is not intended to be a cryptographically secure hash function; it was designed to protect against accidental
  * errors, not malicious attacks. Most credit cards and many government identification numbers use the algorithm as
  * a simple method of distinguishing valid numbers from mistyped or otherwise incorrect numbers.
  *
  * @author <a href="mailto:ohadr@wix.com">Raz, Ohad</a>
  */
object LuhnValidator extends Validator {
  val digitalRoot = Seq(
    0, 0,
    1, 2,
    2, 4,
    3, 6,
    4, 8,
    5, 1,
    6, 3,
    7, 5,
    8, 7,
    9, 9)

  val calculateLuhnSum: String => Int = creditCardNumber => {
    creditCardNumber.map {_.asDigit}.reverse.zipWithIndex.map {
      case (digit, index) => digitalRoot(digit * 2 + index % 2)
    }.sum
  }

  def validate(creditCardNumber: String): Boolean = {
    calculateLuhnSum(creditCardNumber) % 10 == 0
  }
}
