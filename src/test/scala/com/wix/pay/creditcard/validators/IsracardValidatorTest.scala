/*      __ __ _____  __                                              *\
**     / // // /_/ |/ /          Wix                                 **
**    / // // / /|   /           (c) 2006-2015, Wix LTD.             **
**   / // // / //   |            http://www.wix.com/                 **
**   \__/|__/_//_/| |                                                **
\*                |/                                                 */
package com.wix.pay.creditcard.validators


import org.specs2.mutable.SpecWithJUnit


/** Unit-Test for the [[IsracardValidator]] object.
  *
  * @author <a href="mailto:ohadr@wix.com">Raz, Ohad</a>
  */
class IsracardValidatorTest extends SpecWithJUnit {

  "validate" should {
    "approve Isracard's short number format" in {
      IsracardValidator.validate("10830529") must beTrue
    }

    "approve Isracard's long number format" in {
      IsracardValidator.validate("010830529") must beTrue
    }

    "reject invalid number format" in {
      IsracardValidator.validate("1111222233334444") must beFalse
    }
  }
}
