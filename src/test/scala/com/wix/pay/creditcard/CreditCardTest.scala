/*      __ __ _____  __                                              *\
**     / // // /_/ |/ /          Wix                                 **
**    / // // / /|   /           (c) 2006-2015, Wix LTD.             **
**   / // // / //   |            http://www.wix.com/                 **
**   \__/|__/_//_/| |                                                **
\*                |/                                                 */
package com.wix.pay.creditcard


import org.specs2.matcher.{AlwaysMatcher, Matcher}
import org.specs2.mutable.{SpecWithJUnit}
import org.specs2.matcher.MustMatchers._


/** Unit-Test for the [[CreditCard]] class.
  *
  * @author <a href="mailto:ohadr@wix.com">Raz, Ohad</a>
  */
class CreditCardTest extends SpecWithJUnit {
  val ccNumber = "123412341232"
  val expiration = YearMonth(2020, 12)
  val csc = "csc"
  val network = "network"


  def beCreditCard(number: Matcher[String] = AlwaysMatcher(),
                   expiration: Matcher[YearMonth] = AlwaysMatcher(),
                   additionalFields: Matcher[Option[CommonCreditCardFields]] = AlwaysMatcher()): Matcher[CreditCard] = {
    number ^^ { (_: CreditCard).number aka "number" } and
      expiration ^^ { (_: CreditCard).expiration aka "expiration" } and
      additionalFields ^^ { cc: CreditCard => cc.additionalFields aka "additional fields"}
  }


  "Converting Public Credit Card to a full credit card" should {
    val holderId = Option("holder id")
    val holderName = Option("holder name")
    val billingAddress = Option("billing address")
    val billingPostalCode = Option("billing postal code")
    val additionalFields = PublicCreditCardOptionalFields(holderId,
      holderName,
      billingAddress,
      billingPostalCode)

    "use the data from the public card, including additional fields, given number and csc, if specified" in {
      val publicCard = PublicCreditCard(
        "1234",
        expiration,
        Some(additionalFields))

      CreditCard(publicCard, ccNumber, csc) must
        beCreditCard(
          number = ===(ccNumber),
          expiration = ===(expiration),
          additionalFields = beSome(CreditCardOptionalFields(Some(csc), Some(additionalFields))))
    }

    "use the data from the public card that has no additional fields, given number and csc, if specified" in {
      val publicCard = PublicCreditCard(
        "1234",
        expiration)

      CreditCard(publicCard, ccNumber, csc) must
        beCreditCard(
          number = ===(ccNumber),
          expiration = ===(expiration),
          additionalFields = beSome(CreditCardOptionalFields(Some(csc))))
    }

    "use the data from the public card, and given number (if no csc is specified)" in {
      val publicCard = PublicCreditCard(
        "1234",
        expiration,
        Some(additionalFields))

      CreditCard(publicCard, ccNumber) must
        beCreditCard(
          number = ===(ccNumber),
          expiration = ===(expiration),
          additionalFields = beSome(CreditCardOptionalFields(None, Some(additionalFields))))
    }

    "reject invalid number format" in {
      val invalidNumber = s"${ccNumber}3"
      val publicCard = PublicCreditCard(
        "1234",
        expiration,
        Some(additionalFields))

      CreditCard(publicCard, invalidNumber) must
        throwAn[InvalidCreditCardNumberException](message = s"Invalid card number '$invalidNumber'")
    }
  }
}
