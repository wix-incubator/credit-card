/*      __ __ _____  __                                              *\
**     / // // /_/ |/ /          Wix                                 **
**    / // // / /|   /           (c) 2006-2015, Wix LTD.             **
**   / // // / //   |            http://www.wix.com/                 **
**   \__/|__/_//_/| |                                                **
\*                |/                                                 */
package com.wix.pay.creditcard


import org.specs2.matcher.{AlwaysMatcher, Matcher}
import org.specs2.mutable.SpecificationWithJUnit
import PublicCreditCard.toAdditionalPublicCreditCardFields


/** Unit-Test for the [[PublicCreditCard]] class.
  *
  * @author <a href="mailto:ohadr@wix.com">Raz, Ohad</a>
  */
class PublicCreditCardTest extends SpecificationWithJUnit {

  def beCreditCard(lastDigits: Matcher[String] = AlwaysMatcher(),
                   expiration: Matcher[YearMonth] = AlwaysMatcher(),
                   additionalFields: Matcher[Option[PublicCreditCardOptionalFields]] = AlwaysMatcher()): Matcher[PublicCreditCard] = {
    lastDigits ^^ { (_: PublicCreditCard).lastDigits aka "number" } and
      expiration ^^ { (_: PublicCreditCard).expiration aka "expiration" } and
      additionalFields ^^ { (_: PublicCreditCard).additionalFields aka "additional fields" }
  }


  "Converting Credit Card to a public credit card" should {
    "use the data from the credit card" in {
      val lastDigits = "9876"
      val ccNumber = s"1234-1234-1234-$lastDigits"
      val expiration = YearMonth(2020, 12)
      val csc = Option("csc")
      val holderId = Option("holder id")
      val holderName = Option("holder name")
      val billingAddress = Option("billing address")
      val billingPostalCode = Option("billing postal code")
      val additionalFields = Some(CreditCardOptionalFields(
        csc = csc,
        holderId = holderId,
        holderName = holderName,
        billingAddress = billingAddress,
        billingPostalCode = billingPostalCode))
      val creditCard = CreditCard(
        number = ccNumber,
        expiration = expiration,
        additionalFields = additionalFields map (_.copy()))

      PublicCreditCard(creditCard) must
        beCreditCard(
          lastDigits = ===(lastDigits),
          expiration = ===(expiration),
          additionalFields = ===(additionalFields.map(toAdditionalPublicCreditCardFields)))
    }
  }
}
