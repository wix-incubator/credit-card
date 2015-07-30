/*      __ __ _____  __                                              *\
**     / // // /_/ |/ /          Wix                                 **
**    / // // / /|   /           (c) 2006-2015, Wix LTD.             **
**   / // // / //   |            http://www.wix.com/                 **
**   \__/|__/_//_/| |                                                **
\*                |/                                                 */
package com.wix.pay.creditcard


/** Container that holds optional fields of a Public Credit Card (e.g., holder ID, billing address).
  *
  * @author <a href="mailto:ohadr@wix.com">Raz, Ohad</a>
  */
case class PublicCreditCardOptionalFields private (holderId: Option[String],
                                          holderName: Option[String],
                                          billingAddress: Option[String],
                                          billingPostalCode: Option[String],
                                          billingAddressDetailed : Option[AddressDetailed])
  extends Serializable with CommonPublicCreditCardFields {
  def this(holderId: Option[String] = None,
           holderName: Option[String] = None,
           billingAddress: Option[String] = None,
           billingPostalCode: Option[String] = None) = this(
    holderId = holderId,
    holderName = holderName,
    billingAddress = billingAddress,
    billingPostalCode = billingPostalCode,
    billingAddressDetailed = None)

  def this(holderId: Option[String] = None,
           holderName: Option[String] = None,
           billingAddressDetailed: Option[AddressDetailed]) = this(
    holderId = holderId,
    holderName = holderName,
    billingAddress = billingAddressDetailed map(_.composedAddress),
    billingPostalCode = billingAddressDetailed.flatMap(_.postalCode),
    billingAddressDetailed = billingAddressDetailed)
}
