/*      __ __ _____  __                                              *\
**     / // // /_/ |/ /          Wix                                 **
**    / // // / /|   /           (c) 2006-2015, Wix LTD.             **
**   / // // / //   |            http://www.wix.com/                 **
**   \__/|__/_//_/| |                                                **
\*                |/                                                 */
package com.wix.pay.creditcard


import com.wix.pay.creditcard.networks.Networks
import com.wix.pay.creditcard.validators.LuhnValidator
import org.specs2.matcher.MustMatchers._
import org.specs2.matcher.{AlwaysMatcher, Matcher}
import org.specs2.mutable.SpecWithJUnit


/** Unit-Test for the [[PublicCreditCard]] class.
  *
  * @author <a href="mailto:ohadr@wix.com">Raz, Ohad</a>
  */
class PublicCreditCardTest extends SpecWithJUnit {
  val lastDigits = "9876"
  val ccNumber = s"123412341234$lastDigits"
  val expiration = YearMonth(2020, 12)
  val csc = Option("csc")
  val holderId = Option("holder id")
  val holderName = Option("holder name")
  val billingAddress = Option("billing address")
  val billingPostalCode = Option("billing postal code")


  def generateLuhnValid(prefix: String, length: Int) = {
    def addLastDigitLuhnValid(number: String): String = {
      s"$number${(10 - LuhnValidator.calculateLuhnSum((number.toLong * 10).toString) % 10) % 10}"
    }

    val baseNumber = s"$prefix${Array.fill(length - prefix.length - 1)("0").mkString}"

    addLastDigitLuhnValid(baseNumber)
  }

  def beCreditCard(lastDigits: Matcher[String] = AlwaysMatcher(),
                   expiration: Matcher[YearMonth] = AlwaysMatcher(),
                   network: Matcher[Option[String]] = AlwaysMatcher(),
                   additionalFields: Matcher[Option[PublicCreditCardOptionalFields]] = AlwaysMatcher()): Matcher[PublicCreditCard] = {
    lastDigits ^^ { (_: PublicCreditCard).lastDigits aka "number" } and
      expiration ^^ { (_: PublicCreditCard).expiration aka "expiration" } and
      network ^^ { (_: PublicCreditCard).network aka "network" } and
      additionalFields ^^ { (_: PublicCreditCard).additionalFields aka "additional fields" }
  }


  "Converting Credit Card to a public credit card" should {
    "use the data from the credit card" in {
      val publicAdditionalFields = new PublicCreditCardOptionalFields(
        holderId = holderId,
        holderName = holderName,
        billingAddress = billingAddress,
        billingPostalCode = billingPostalCode)
      val additionalFields = Some(CreditCardOptionalFields(
        csc = csc,
        publicFields = Some(publicAdditionalFields)))
      val creditCard = CreditCard(
        number = ccNumber,
        expiration = expiration,
        additionalFields = additionalFields)

      PublicCreditCard(creditCard) must
        beCreditCard(
          lastDigits = ===(lastDigits),
          expiration = ===(expiration),
          additionalFields = beSome(publicAdditionalFields))
    }

    "use None as additional fields, if the credit card's public fields is None" in {
      val additionalFields = Some(CreditCardOptionalFields(
        csc = csc,
        publicFields = None))
      val creditCard = CreditCard(
        number = ccNumber,
        expiration = expiration,
        additionalFields = additionalFields)

      PublicCreditCard(creditCard) must
        beCreditCard(
          lastDigits = ===(lastDigits),
          expiration = ===(expiration),
          additionalFields = beNone)
    }

    "populate a corresponding network" in {
      val masterCardNumber = generateLuhnValid("51", 16)
      val creditCard = CreditCard(
        number = masterCardNumber,
        expiration = expiration)

      PublicCreditCard(creditCard) must
        beCreditCard(
          lastDigits = ===(masterCardNumber.takeRight(4)),
          expiration = ===(expiration),
          network = beSome(Networks.masterCard))
    }
  }
}
