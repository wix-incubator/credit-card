/*      __ __ _____  __                                              *\
**     / // // /_/ |/ /          Wix                                 **
**    / // // / /|   /           (c) 2006-2015, Wix LTD.             **
**   / // // / //   |            http://www.wix.com/                 **
**   \__/|__/_//_/| |                                                **
\*                |/                                                 */
package com.wix.pay.creditcard


/** Defines the common attributes of a Credit Card.
  *
  * @author <a href="mailto:ohadr@wix.com">Raz, Ohad</a>
  */
trait CommonCreditCardFields extends CommonPublicCreditCardFields {
  def csc: Option[String]
}
