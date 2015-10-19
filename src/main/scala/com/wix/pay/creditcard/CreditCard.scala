/*      __ __ _____  __                                              *\
**     / // // /_/ |/ /          Wix                                 **
**    / // // / /|   /           (c) 2006-2014, Wix LTD.             **
**   / // // / //   |            http://www.wix.com/                 **
**   \__/|__/_//_/| |                                                **
\*                |/                                                 */
package com.wix.pay.creditcard


import com.wix.pay.creditcard.networks.Networks
import com.wix.pay.creditcard.validators.{Validator, IsracardValidator, LuhnValidator}


/** Represents a credit card.
  *
  * @author <a href="mailto:ohadr@wix.com">Raz, Ohad</a>
  */
case class CreditCard(number: String,
                      expiration: YearMonth,
                      additionalFields: Option[CreditCardOptionalFields] = None)
    extends Serializable with CommonCreditCardFields {


  if (!validator.validate(number)) {
    throw InvalidCreditCardNumberException(number)
  }

  override def csc: Option[String] = additionalFields flatMap (_.csc)
  override def holderId: Option[String] = additionalFields flatMap (_.holderId)
  override def holderName: Option[String] = additionalFields flatMap (_.holderName)
  override def billingAddress: Option[String] = additionalFields flatMap (_.billingAddress)
  override def billingPostalCode: Option[String] = additionalFields flatMap (_.billingPostalCode)
  override def billingAddressDetailed: Option[AddressDetailed] = additionalFields flatMap(_.billingAddressDetailed)

  private def validator: Validator = {
    object Isracard {
      def unapply(number: String): Boolean = {
        Networks(number).fold(false)(_ == Networks.isracard)
      }
    }

    number match {
      case Isracard() => IsracardValidator
      case _ => LuhnValidator
    }
  }
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
