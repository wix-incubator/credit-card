/*      __ __ _____  __                                              *\
**     / // // /_/ |/ /          Wix                                 **
**    / // // / /|   /           (c) 2006-2015, Wix LTD.             **
**   / // // / //   |            http://www.wix.com/                 **
**   \__/|__/_//_/| |                                                **
\*                |/                                                 */
package com.wix.pay.creditcard


/** Indicates that a Credit Card number is invalid (e.g., does not pass ''Luhn'' validation,
  * or ''Isracard'' validation, in the case of ''Isracard'' credit cards).
  *
  * @author <a href="mailto:ohadr@wix.com">Raz, Ohad</a>
  */
case class InvalidCreditCardNumberException(number: String,
                                            cause:Throwable) extends RuntimeException(
  InvalidCreditCardNumberException.composeMsg(number), cause)


/** The Companion Object of the [[InvalidCreditCardNumberException]] class, which introduces means for instantiating
  * an exception object.
  *
  * @author <a href="mailto:ohadr@wix.com">Raz, Ohad</a>
  */
object InvalidCreditCardNumberException {
  def apply(number: String): InvalidCreditCardNumberException = this(composeMsg(number), null)
  def apply(cause: Throwable): InvalidCreditCardNumberException = this(Option(cause).map(_.toString).orNull, cause)
  def apply(): InvalidCreditCardNumberException = this(null, null)

  private def composeMsg(number: String): String = {
    s"Invalid card number '${number.replaceAll("\\d(?=\\d{4})", "X")}'"
  }
}