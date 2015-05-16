/*      __ __ _____  __                                              *\
**     / // // /_/ |/ /          Wix                                 **
**    / // // / /|   /           (c) 2006-2015, Wix LTD.             **
**   / // // / //   |            http://www.wix.com/                 **
**   \__/|__/_//_/| |                                                **
\*                |/                                                 */
package com.wix.pay.creditcard


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

  override val holderId: Option[String] = additionalFields flatMap (_.holderId)
  override val billingAddress: Option[String] = additionalFields flatMap (_.billingAddress)
  override val billingPostalCode: Option[String] = additionalFields flatMap (_.billingPostalCode)
  override val holderName: Option[String] = additionalFields flatMap (_.holderName)
}


/** The companion object of the [[PublicCreditCard]] case class, introduces means to create a
  * public credit card based on a given [[CreditCard]].
  *
  * @author <a href="mailto:ohadr@wix.com">Raz, Ohad</a>
  */
object PublicCreditCard {
  /** Credit card network IDs.
    * Existing values are guaranteed to never change (users are allowed to persist them).
    */
  object Networks {
    val amex = "amex"
    val dankort = "dankort"
    val diners = "diners"
    val discover = "discover"
    val isracard = "isracard"
    val jcb = "jcb"
    val maestro = "maestro"
    val masterCard = "mastercard"
    val unionPay = "unionpay"
    val visa = "visa"

    /** Contains the full list of networks.
      * New values may be added in the future; existing values will never be removed.
      */
    val all = Set(amex, dankort, discover, isracard, jcb, maestro, masterCard, unionPay, visa);
  }

  val amexRegex = """^3([47]\d{3}|569[04]|[23]\d{3})\d{10}$""".r
  val dankortRegex = """^5019(34)\d{10}$""".r
  val dinersRegex = """^36(409\d|5\d{3})\d{8}$""".r
  val discoverRegex = """^(6011\d{2}|65\d{4}|64[4-9]\d{3}|601300|3[^547]\d{4})\d{10}$""".r
  val isracardRegex = """^\d{8,9}$""".r
  val jcbRegex = """^35(?:2[89]|[3-8]\d)\d{12}$""".r
  val maestroRegex =
    """^(50[^1]\d{3}|501[^9]\d{2}|5019[^3]\d|50193[^4]|5[6789]\d{4}|6[^0245]\d{4}|62[79]\d{3}|628[01]\d{2}|626257|62292[78]|62293[^4]|62294[^14]|62299\d|62298[02369]|62297[^0]|62296[^4569]|62295[^68]|62212[^6789]|62211\d|6220\d{2}|621[^4]\d{2}|6214[^8]\d|62148[^3]|620\d{3}|64[0-3]\d{3}|60[2-7]\d{3}|601[^13]\d{2}|6013[^0]\d|60130[^0]|600\d{3})\d{6,13}$""".r
  val masterCardRegex = """^5[1-5]\d{14}$""".r
  val unionPayRegex = """^(62[345]\d{3}|628[2-8]\d{2}|626[^2]\d{2}|6262[^5]\d|62625[^7]|622[^019]|6229[01]\d|62298[14578]|622970|62296[4569]|62295[68]|62294[14]|622934|62292[^78]|622[2-8]\d{2}|6221[^12]|62212[6-9]|621483)\d{10,13}$""".r
  val visaRegex = """^4\d{12}(\d{3})?$""".r

  def apply(number: String,
            expiration: YearMonth,
            additionalFields: Option[PublicCreditCardOptionalFields] = None) = {
    new PublicCreditCard(
      lastDigits = number.takeRight(4),
      expiration = expiration,
      network = network(number),
      additionalFields = additionalFields)
  }

  def apply(creditCard: CreditCard): PublicCreditCard = {
    new PublicCreditCard(
      lastDigits = creditCard.number.takeRight(4),
      expiration = creditCard.expiration,
      network = network(creditCard.number),
      additionalFields = creditCard.additionalFields flatMap toAdditionalCreditCardFields)
  }
  

  def toAdditionalCreditCardFields(additionalFields: CreditCardOptionalFields): Option[PublicCreditCardOptionalFields] = {
    additionalFields.publicFields map (fields =>
      PublicCreditCardOptionalFields(
        holderId = fields.holderId,
        holderName = fields.holderName,
        billingAddress = fields.billingAddress,
        billingPostalCode = fields.billingPostalCode))
  }

  /** According to [[http://en.wikipedia.org/wiki/Bank_card_number#cite_note-DinerUS-9]] and
    * [[https://www.bindb.com/bin-database.html]].
    *
   * @param creditCardNumber
    *                         the credit number for which a Network will be resolved.
   * @return
    *         The resolved Network, or {{{None}}} if failed to resolve one.
   */
  def network(creditCardNumber: String): Option[String] = {
    creditCardNumber match {
      case amexRegex(_*) => Some(Networks.amex)
      case dankortRegex(_*) => Some(Networks.dankort)
      case dinersRegex(_*) => Some(Networks.diners)
      case discoverRegex(_*) => Some(Networks.discover)
      case isracardRegex(_*) => Some(Networks.isracard)
      case jcbRegex(_*) => Some(Networks.jcb)
      case maestroRegex(_*) => Some(Networks.maestro)
      case masterCardRegex(_*) => Some(Networks.masterCard)
      case unionPayRegex(_*) => Some(Networks.unionPay)
      case visaRegex(_*) => Some(Networks.visa)
      case _ => None
    }
  }
}
