/*      __ __ _____  __                                              *\
**     / // // /_/ |/ /          Wix                                 **
**    / // // / /|   /           (c) 2006-2015, Wix LTD.             **
**   / // // / //   |            http://www.wix.com/                 **
**   \__/|__/_//_/| |                                                **
\*                |/                                                 */
package com.wix.pay.creditcard.validators


/** A [[Validator]] or ''Isracard'' cards.
  *
  * @author <a href="mailto:ohadr@wix.com">Raz, Ohad</a>
  */
object IsracardValidator extends Validator {

  def validate(creditCardNumber: String): Boolean = {
    val calculateIsracardSum: String => Int = creditCardNumber => {
      creditCardNumber.map {_.asDigit}.reverse.zipWithIndex.map {
        case (digit, multiplier) => digit * (multiplier + 1)
      }.sum
    }

    calculateIsracardSum(creditCardNumber) % 11 == 0
  }
}
