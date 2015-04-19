/*      __ __ _____  __                                              *\
**     / // // /_/ |/ /          Wix                                 **
**    / // // / /|   /           (c) 2006-2014, Wix LTD.             **
**   / // // / //   |            http://www.wix.com/                 **
**   \__/|__/_//_/| |                                                **
\*                |/                                                 */
package com.wix.pay.creditcard


/** Represents a credit card.
  *
  * @author <a href="mailto:ohadr@wix.com">Raz, Ohad</a>
  */
case class CreditCard(number: String,
                      expiration: YearMonth,
                      additionalFields: Option[CreditCardOptionalFields] = None) extends Serializable

/** The companion object of the [[CreditCard]] case class, introduces means to create a credit card based on a given
  * [[PublicCreditCard]].
  *
  * @author <a href="mailto:ohadr@wix.com">Raz, Ohad</a>
  */
object CreditCard {

  def apply(creditCard: PublicCreditCard, number: String, csc: String): CreditCard = {
    CreditCard(
      number = number,
      expiration = creditCard.expiration,
      additionalFields = creditCard.additionalFields.map (toAdditionalCreditCardFields(_).copy(csc = Option(csc))))
  }

  def apply(creditCard: PublicCreditCard, number: String): CreditCard = {
    CreditCard(
      number = number,
      expiration = creditCard.expiration,
      additionalFields = creditCard.additionalFields.map (toAdditionalCreditCardFields(_).copy(csc = None)))
  }
  
  def toAdditionalCreditCardFields(additionalFields: PublicCreditCardOptionalFields): CreditCardOptionalFields = {
    CreditCardOptionalFields(
      csc = None,
      holderId = additionalFields.holderId,
      holderName = additionalFields.holderName,
      billingAddress = additionalFields.billingAddress,
      billingPostalCode = additionalFields.billingPostalCode)
  }
}
