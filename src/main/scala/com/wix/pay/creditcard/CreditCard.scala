/*      __ __ _____  __                                              *\
**     / // // /_/ |/ /          Wix                                 **
**    / // // / /|   /           (c) 2006-2014, Wix LTD.             **
**   / // // / //   |            http://www.wix.com/                 **
**   \__/|__/_//_/| |                                                **
\*                |/                                                 */
package com.wix.pay.creditcard


import com.wix.pay.creditcard.validators.{IsracardValidator, LuhnValidator}


/** Represents a credit card.
  *
  * @author <a href="mailto:ohadr@wix.com">Raz, Ohad</a>
  */
case class CreditCard(number: String,
                      expiration: YearMonth,
                      additionalFields: Option[CreditCardOptionalFields] = None)
    extends Serializable with CommonCreditCardFields {

  private val validator = number match {
    case Isracard() => IsracardValidator
    case _ => LuhnValidator
  }

  if (!validator.validate(number)) {
    throw new InvalidCreditCardNumberException(s"Invalid card number '$number'")
  }

  override val csc: Option[String] = additionalFields flatMap (_.csc)
  override val holderId: Option[String] = additionalFields flatMap (_.holderId)
  override val holderName: Option[String] = additionalFields flatMap (_.holderName)
  override val billingAddress: Option[String] = additionalFields flatMap (_.billingAddress)
  override val billingPostalCode: Option[String] = additionalFields flatMap (_.billingPostalCode)
}


/** The companion object of the [[CreditCard]] case class, introduces alternative means to create a credit card,
  * e.g., based on a given [[PublicCreditCard]].
  *
  * @author <a href="mailto:ohadr@wix.com">Raz, Ohad</a>
  */
object CreditCard {

  def apply(creditCard: PublicCreditCard, number: String): CreditCard = {
    CreditCard(
      number = number,
      expiration = creditCard.expiration,
      additionalFields = Some(CreditCardOptionalFields(None, creditCard.additionalFields)))
  }

  def apply(creditCard: PublicCreditCard, number: String, csc: String): CreditCard = {
    CreditCard(
      number = number,
      expiration = creditCard.expiration,
      additionalFields = Some(CreditCardOptionalFields(Option(csc), creditCard.additionalFields)))
  }
}


/** An Extractor Object usable for a Credit Card numbers, which indicates whether the number matches ''Isracard''
  * Credit Card.
  *
  * An Extractor Object is an object that has a method(s) called {{{unapply}}} as one of its members. The purpose
  * of that {{{unapply}}} method is to match a value and take it apart.
  *
  * @author <a href="mailto:ohadr@wix.com">Raz, Ohad</a>
  */
private object Isracard {
  def unapply(number: String): Boolean = {
    number match {
      case PublicCreditCard.isracardRegex(_*) => true
      case _ => false
    }
  }
}
