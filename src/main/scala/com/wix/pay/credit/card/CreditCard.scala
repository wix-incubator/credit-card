/*      __ __ _____  __                                              *\
**     / // // /_/ |/ /          Wix                                 **
**    / // // / /|   /           (c) 2006-2014, Wix LTD.             **
**   / // // / //   |            http://www.wix.com/                 **
**   \__/|__/_//_/| |                                                **
\*                |/                                                 */
package com.wix.pay.credit.card


/** Represents a credit card.
  *
  * @author <a href="mailto:ohadr@wix.com">Raz, Ohad</a>
  */
case class CreditCard(number: String,
                      expiration: YearMonth,
                      csc: Option[String] = None,
                      holderId: Option[String] = None,
                      holderName: Option[String] = None,
                      billingAddress: Option[String] = None,
                      billingPostalCode: Option[String] = None) extends Serializable

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
      holderId = creditCard.holderId,
      holderName = creditCard.holderName,
      billingAddress = creditCard.billingAddress,
      billingPostalCode = creditCard.billingPostalCode)
  }

  def apply(creditCard: PublicCreditCard, number: String): CreditCard = {
    CreditCard(
      number = number,
      expiration = creditCard.expiration,
      csc = None,
      holderId = creditCard.holderId,
      holderName = creditCard.holderName,
      billingAddress = creditCard.billingAddress,
      billingPostalCode = creditCard.billingPostalCode)
  }
}
