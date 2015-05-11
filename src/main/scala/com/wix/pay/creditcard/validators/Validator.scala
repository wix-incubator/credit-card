/*      __ __ _____  __                                              *\
**     / // // /_/ |/ /          Wix                                 **
**    / // // / /|   /           (c) 2006-2015, Wix LTD.             **
**   / // // / //   |            http://www.wix.com/                 **
**   \__/|__/_//_/| |                                                **
\*                |/                                                 */
package com.wix.pay.creditcard.validators


/** Credit Card validator trait.
  *
  * @author <a href="mailto:ohadr@wix.com">Raz, Ohad</a>
  */
trait Validator {
  def validate(creditCardNumber: String): Boolean
}
