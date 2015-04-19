/*      __ __ _____  __                                              *\
**     / // // /_/ |/ /          Wix                                 **
**    / // // / /|   /           (c) 2006-2014, Wix LTD.             **
**   / // // / //   |            http://www.wix.com/                 **
**   \__/|__/_//_/| |                                                **
\*                |/                                                 */
package com.wix.pay.creditcard


import scala.language.implicitConversions


/** Represents a credit card.
  *
  * @author <a href="mailto:ohadr@wix.com">Raz, Ohad</a>
  */
case class CreditCard(number: String,
                      expiration: YearMonth,
                      csc: Option[String] = None,
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
      csc = Option(csc),
      additionalFields = creditCard.additionalFields map (_.copy()))
  }

  def apply(creditCard: PublicCreditCard, number: String): CreditCard = {
    CreditCard(
      number = number,
      expiration = creditCard.expiration,
      csc = None,
      additionalFields = creditCard.additionalFields map (_.copy()))
  }
  
  implicit def toAdditionalCreditCardFields(additionalFields: PublicCreditCardOptionalFields): CreditCardOptionalFields = {
    CreditCardOptionalFields(
      csc = None,
      holderId = additionalFields.holderId,
      holderName = additionalFields.holderName,
      billingAddress = additionalFields.billingAddress,
      billingPostalCode = additionalFields.billingPostalCode)
  }
}
