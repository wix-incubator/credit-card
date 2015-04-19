/*      __ __ _____  __                                              *\
**     / // // /_/ |/ /          Wix                                 **
**    / // // / /|   /           (c) 2006-2015, Wix LTD.             **
**   / // // / //   |            http://www.wix.com/                 **
**   \__/|__/_//_/| |                                                **
\*                |/                                                 */
package com.wix.pay.credit.card


import org.specs2.matcher.{AlwaysMatcher, Matcher}
import org.specs2.mutable.SpecificationWithJUnit


/** Unit-Test for the [[CreditCard]] class.
  *
  * @author <a href="mailto:ohadr@wix.com">Raz, Ohad</a>
  */
class CreditCardTest extends SpecificationWithJUnit {

  def beCreditCard(number: Matcher[String] = AlwaysMatcher(),
                   expiration: Matcher[YearMonth] = AlwaysMatcher(),
                   csc: Matcher[Option[String]] = AlwaysMatcher(),
                   holderId: Matcher[Option[String]] = AlwaysMatcher(),
                   holderName: Matcher[Option[String]] = AlwaysMatcher(),
                   billingAddress: Matcher[Option[String]] = AlwaysMatcher(),
                   billingPostalCode: Matcher[Option[String]] = AlwaysMatcher()): Matcher[CreditCard] = {
    number ^^ { (_: CreditCard).number aka "number" } and
      expiration ^^ { (_: CreditCard).expiration aka "expiration" } and
      csc ^^ { (_: CreditCard).csc aka "csc" } and
      holderId ^^ { (_: CreditCard).holderId aka "holderId" } and
      holderName ^^ { (_: CreditCard).holderName aka "holderName" } and
      billingAddress ^^ { (_: CreditCard).billingAddress aka "billingAddress" } and
      billingPostalCode ^^ { (_: CreditCard).billingPostalCode aka "billingPostalCode" }
  }


  "Converting Public Credit Card to a full credit card" should {
    val ccNumber = "1234-1234-1234"
    val expiration = YearMonth(2020, 12)
    val holderId = Option("holder id")
    val holderName = Option("holder name")
    val billingAddress = Option("billing address")
    val billingPostalCode = Option("billing postal code")
    val csc = "csc"

    "use the data from the public card, given number and csc, if specified" in {
      val csc = "csc"

      val publicCard = PublicCreditCard(
        "1234",
        expiration,
        holderId,
        holderName,
        billingAddress,
        billingPostalCode)

      CreditCard(publicCard, ccNumber, csc) must
        beCreditCard(
          number = ===(ccNumber),
          expiration = ===(expiration),
          csc = beSome(csc),
          holderId = ===(holderId),
          holderName = ===(holderName),
          billingAddress = ===(billingAddress),
          billingPostalCode = ===(billingPostalCode))
    }

    "use the data from the public card, and given number (if no csc is specified)" in {
      val publicCard = PublicCreditCard(
        "1234",
        expiration,
        holderId,
        holderName,
        billingAddress,
        billingPostalCode)

      CreditCard(publicCard, ccNumber) must
        beCreditCard(
          number = ===(ccNumber),
          expiration = ===(expiration),
          csc = beNone,
          holderId = ===(holderId),
          holderName = ===(holderName),
          billingAddress = ===(billingAddress),
          billingPostalCode = ===(billingPostalCode))
    }
  }
}
