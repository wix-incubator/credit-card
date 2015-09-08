/*      __ __ _____  __                                              *\
**     / // // /_/ |/ /          Wix                                 **
**    / // // / /|   /           (c) 2006-2015, Wix LTD.             **
**   / // // / //   |            http://www.wix.com/                 **
**   \__/|__/_//_/| |                                                **
\*                |/                                                 */
package com.wix.pay.creditcard


/** Defines the common attributes of a Public Credit Card.
  *
  * @author <a href="mailto:ohadr@wix.com">Raz, Ohad</a>
  */
trait CommonPublicCreditCardFields {
  def holderId: Option[String]
  def holderName: Option[String]
  def billingAddress: Option[String]
  def billingPostalCode: Option[String]
  def billingAddressDetailed: Option[AddressDetailed]
}
