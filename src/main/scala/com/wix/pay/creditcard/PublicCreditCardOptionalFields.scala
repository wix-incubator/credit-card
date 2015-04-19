/*      __ __ _____  __                                              *\
**     / // // /_/ |/ /          Wix                                 **
**    / // // / /|   /           (c) 2006-2015, Wix LTD.             **
**   / // // / //   |            http://www.wix.com/                 **
**   \__/|__/_//_/| |                                                **
\*                |/                                                 */
package com.wix.pay.creditcard

/** This class is
  *
  * @author <a href="mailto:ohadr@wix.com">Raz, Ohad</a>
  */
case class PublicCreditCardOptionalFields(holderId: Option[String] = None,
                                          holderName: Option[String] = None,
                                          billingAddress: Option[String] = None,
                                          billingPostalCode: Option[String] = None) extends Serializable
