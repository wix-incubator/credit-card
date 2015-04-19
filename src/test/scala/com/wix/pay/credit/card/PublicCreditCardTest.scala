/*      __ __ _____  __                                              *\
**     / // // /_/ |/ /          Wix                                 **
**    / // // / /|   /           (c) 2006-2015, Wix LTD.             **
**   / // // / //   |            http://www.wix.com/                 **
**   \__/|__/_//_/| |                                                **
\*                |/                                                 */
package com.wix.pay.credit.card


import org.specs2.matcher.{AlwaysMatcher, Matcher}
import org.specs2.mutable.SpecificationWithJUnit


/** Unit-Test for the [[PublicCreditCard]] class.
  *
  * @author <a href="mailto:ohadr@wix.com">Raz, Ohad</a>
  */
class PublicCreditCardTest extends SpecificationWithJUnit {

  def beCreditCard(lastDigits: Matcher[String] = AlwaysMatcher(),
                   expiration: Matcher[YearMonth] = AlwaysMatcher(),
                   holderId: Matcher[Option[String]] = AlwaysMatcher(),
                   holderName: Matcher[Option[String]] = AlwaysMatcher(),
                   billingAddress: Matcher[Option[String]] = AlwaysMatcher(),
                   billingPostalCode: Matcher[Option[String]] = AlwaysMatcher()): Matcher[PublicCreditCard] = {
    lastDigits ^^ { (_: PublicCreditCard).lastDigits aka "number" } and
      expiration ^^ { (_: PublicCreditCard).expiration aka "expiration" } and
      holderId ^^ { (_: PublicCreditCard).holderId aka "holderId" } and
      holderName ^^ { (_: PublicCreditCard).holderName aka "holderName" } and
      billingAddress ^^ { (_: PublicCreditCard).billingAddress aka "billingAddress" } and
      billingPostalCode ^^ { (_: PublicCreditCard).billingPostalCode aka "billingPostalCode" }
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

      val creditCard = CreditCard(
        number = ccNumber,
        expiration = expiration,
        csc = csc,
        holderId = holderId,
        holderName = holderName,
        billingAddress = billingAddress,
        billingPostalCode = billingPostalCode)

      PublicCreditCard(creditCard) must
        beCreditCard(
          lastDigits = ===(lastDigits),
          expiration = ===(expiration),
          holderId = ===(holderId),
          holderName = ===(holderName),
          billingAddress = ===(billingAddress),
          billingPostalCode = ===(billingPostalCode))
    }
  }
}
