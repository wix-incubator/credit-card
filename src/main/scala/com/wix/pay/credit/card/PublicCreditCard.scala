/*      __ __ _____  __                                              *\
**     / // // /_/ |/ /          Wix                                 **
**    / // // / /|   /           (c) 2006-2015, Wix LTD.             **
**   / // // / //   |            http://www.wix.com/                 **
**   \__/|__/_//_/| |                                                **
\*                |/                                                 */
package com.wix.pay.credit.card


/** Represents a credit card, which can be shown Public.
  * As such, several details are removed (in comparison to the [[CreditCard]]), such as the full
  * number, cvv, etc.
  *
  * @author <a href="mailto:ohadr@wix.com">Raz, Ohad</a>
  */
case class PublicCreditCard(lastDigits: String,
                            expiration: YearMonth,
                            holderId: Option[String] = None,
                            holderName: Option[String] = None,
                            billingAddress: Option[String] = None,
                            billingPostalCode: Option[String] = None)

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
      holderId = creditCard.holderId,
      holderName = creditCard.holderName,
      billingAddress = creditCard.billingAddress,
      billingPostalCode = creditCard.billingPostalCode)
  }
}
