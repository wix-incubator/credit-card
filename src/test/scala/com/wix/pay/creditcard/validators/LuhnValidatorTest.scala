/*      __ __ _____  __                                              *\
**     / // // /_/ |/ /          Wix                                 **
**    / // // / /|   /           (c) 2006-2015, Wix LTD.             **
**   / // // / //   |            http://www.wix.com/                 **
**   \__/|__/_//_/| |                                                **
\*                |/                                                 */
package com.wix.pay.creditcard.validators


import org.specs2.mutable.SpecWithJUnit


/** Unit-Test for the [[LuhnValidator]] object.
  *
  * @author <a href="mailto:ohadr@wix.com">Raz, Ohad</a>
  */
class LuhnValidatorTest extends SpecWithJUnit {

  "validate" should {
    "approve valid number format" in {
      LuhnValidator.validate("49927398716") must beTrue
    }

    "reject invalid number format" in {
      LuhnValidator.validate("49927398717") must beFalse
    }
  }
}

