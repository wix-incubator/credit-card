/*      __ __ _____  __                                              *\
**     / // // /_/ |/ /          Wix                                 **
**    / // // / /|   /           (c) 2006-2015, Wix LTD.             **
**   / // // / //   |            http://www.wix.com/                 **
**   \__/|__/_//_/| |                                                **
\*                |/                                                 */
package com.wix.pay.creditcard


import com.wix.pay.creditcard.networks.Networks


/** Represents a credit card, which can be shown Public.
  * As such, several details are removed (in comparison to the [[CreditCard]]), such as the full
  * number, cvv, etc.
  *
  * @author <a href="mailto:ohadr@wix.com">Raz, Ohad</a>
  */
case class PublicCreditCard private (lastDigits: String,
                                     expiration: YearMonth,
                                     network: Option[String],
                                     additionalFields: Option[PublicCreditCardOptionalFields])
    extends Serializable with CommonPublicCreditCardFields {

  override def holderId: Option[String] = additionalFields flatMap (_.holderId)
  override def billingAddress: Option[String] = additionalFields flatMap (_.billingAddress)
  override def billingPostalCode: Option[String] = additionalFields flatMap (_.billingPostalCode)
  override def holderName: Option[String] = additionalFields flatMap (_.holderName)
  override def billingAddressDetailed: Option[AddressDetailed] = additionalFields flatMap(_.billingAddressDetailed)

}


/** The companion object of the [[PublicCreditCard]] case class, introduces means to create a
  * public credit card based on a given [[CreditCard]].
  *
  * @author <a href="mailto:ohadr@wix.com">Raz, Ohad</a>
  */
object PublicCreditCard {

  def apply(number: String,
            expiration: YearMonth,
            additionalFields: Option[PublicCreditCardOptionalFields] = None) = {
    new PublicCreditCard(
      lastDigits = number.takeRight(4),
      expiration = expiration,
      network = Networks(number),
      additionalFields = additionalFields)
  }

  def apply(creditCard: CreditCard): PublicCreditCard = {
    new PublicCreditCard(
      lastDigits = creditCard.number.takeRight(4),
      expiration = creditCard.expiration,
      network = Networks(creditCard.number),
      additionalFields = creditCard.additionalFields flatMap toAdditionalCreditCardFields)
  }
  

  def toAdditionalCreditCardFields(additionalFields: CreditCardOptionalFields): Option[PublicCreditCardOptionalFields] = {
    additionalFields.publicFields map (fields =>
      new PublicCreditCardOptionalFields(
        holderId = fields.holderId,
        holderName = fields.holderName,
        billingAddress = fields.billingAddress,
        billingPostalCode = fields.billingPostalCode))
  }
}
