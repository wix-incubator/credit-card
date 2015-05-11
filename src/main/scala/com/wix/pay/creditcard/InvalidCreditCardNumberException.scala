/*      __ __ _____  __                                              *\
**     / // // /_/ |/ /          Wix                                 **
**    / // // / /|   /           (c) 2006-2015, Wix LTD.             **
**   / // // / //   |            http://www.wix.com/                 **
**   \__/|__/_//_/| |                                                **
\*                |/                                                 */
package com.wix.pay.creditcard


/** An [[Exception]] that indicates that a Credit Card number is invalid (e.g., does not pass ''Luhn'' validation,
  * or ''Isracard'' validation, in the case of ''Isracard'' credit cards).
  *
  * @author <a href="mailto:ohadr@wix.com">Raz, Ohad</a>
  */
case class InvalidCreditCardNumberException(message: String = null,
                                            cause:Throwable = null) extends RuntimeException(message, cause)
