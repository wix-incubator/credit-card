/*      __ __ _____  __                                              *\
**     / // // /_/ |/ /          Wix                                 **
**    / // // / /|   /           (c) 2006-2015, Wix LTD.             **
**   / // // / //   |            http://www.wix.com/                 **
**   \__/|__/_//_/| |                                                **
\*                |/                                                 */
package com.wix.pay.creditcard


import scala.language.implicitConversions


/** Represents a credit card, which can be shown Public.
  * As such, several details are removed (in comparison to the [[CreditCard]]), such as the full
  * number, cvv, etc.
  *
  * @author <a href="mailto:ohadr@wix.com">Raz, Ohad</a>
  */
case class PublicCreditCard(lastDigits: String,
                            expiration: YearMonth,
                            additionalFields: Option[PublicCreditCardOptionalFields] = None) extends Serializable

/** The companion object of the [[PublicCreditCard]] case class, introduces means to create a public credit card based
  * on a given [[CreditCard]].
  *
  * @author <a href="mailto:ohadr@wix.com">Raz, Ohad</a>
  */
object PublicCreditCard {

  def apply(creditCard: CreditCard): PublicCreditCard = {
    PublicCreditCard(
      lastDigits = creditCard.number.takeRight(4),
      expiration = creditCard.expiration,
      additionalFields = creditCard.additionalFields map (_.copy()))
  }
  

  implicit def toAdditionalPublicCreditCardFields(additionalFields: CreditCardOptionalFields): PublicCreditCardOptionalFields = {
    PublicCreditCardOptionalFields(
      holderId = additionalFields.holderId,
      holderName = additionalFields.holderName,
      billingAddress = additionalFields.billingAddress,
      billingPostalCode = additionalFields.billingPostalCode)
  }
}
