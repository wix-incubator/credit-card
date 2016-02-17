package com.wix.pay.creditcard

import java.util.Locale

import org.specs2.mutable.Specification
import org.specs2.specification.Scope

class PublicCreditCardOptionalFieldsTest extends Specification {

  "PublicCreditCardOptionalFieldsTest" should {
    "fill billingAddress and billingPostalCode correctly" in new ctx {
      val actualFields = PublicCreditCardOptionalFields(Some(holderId), Some(holderName), billingAddressDetailed)
      actualFields.billingAddress must beSome(composedBillingAddress)
      actualFields.billingPostalCode must beSome(billingPostalCode)
    }

    "apply None to billingAddress and billingPostalCode" in new ctx {
      val fields: PublicCreditCardOptionalFields = PublicCreditCardOptionalFields(Some(holderId), Some(holderName), addressDetailedWithAllNones)
      fields.billingAddress must beNone
      fields.billingPostalCode must beNone
    }

  }

  trait ctx extends Scope {
    val holderId = "holder ID"
    val holderName = "holder name"
    val billingAddress = "billing address"
    val billingPostalCode = "billing postal code"
    private val billingCity = "billing city"
    private val billingState = "billing state"
    val billingAddressDetailed = Some(AddressDetailed(
      Some(billingAddress),
      Some(billingCity),
      Some(billingState),
      Some(billingPostalCode),
      Some(new Locale("US"))))
    val addressDetailedWithAllNones: Option[AddressDetailed] = Some(AddressDetailed())
    val composedBillingAddress = s"$billingAddress $billingCity $billingState $billingPostalCode"
  }

}
