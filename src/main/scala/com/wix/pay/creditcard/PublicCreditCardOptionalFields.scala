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
case class PublicCreditCardOptionalFields private (override val holderId: Option[String],
                                                   override val holderName: Option[String],
                                                   override val billingAddress: Option[String],
                                                   override val billingPostalCode: Option[String],
                                                   override val billingAddressDetailed: Option[AddressDetailed])
    extends Serializable with CommonPublicCreditCardFields

object PublicCreditCardOptionalFields  {

  def apply(holderId: Option[String] = None,
            holderName: Option[String] = None,
            billingAddress: Option[String] = None,
            billingPostalCode: Option[String] = None): PublicCreditCardOptionalFields = PublicCreditCardOptionalFields(
    holderId = holderId,
    holderName = holderName,
    billingAddress = billingAddress,
    billingPostalCode = billingPostalCode,
    billingAddressDetailed = None)

  def apply(holderId: Option[String],
            holderName: Option[String],
            billingAddressDetailed: Option[AddressDetailed]): PublicCreditCardOptionalFields = PublicCreditCardOptionalFields(
    holderId = holderId,
    holderName = holderName,
    billingAddress = billingAddressDetailed map(_.composedAddress),
    billingPostalCode = billingAddressDetailed.flatMap(_.postalCode),
    billingAddressDetailed = billingAddressDetailed)
}
