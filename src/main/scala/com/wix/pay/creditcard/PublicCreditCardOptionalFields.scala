/*      __ __ _____  __                                              *\
**     / // // /_/ |/ /          Wix                                 **
**    / // // / /|   /           (c) 2006-2015, Wix LTD.             **
**   / // // / //   |            http://www.wix.com/                 **
**   \__/|__/_//_/| |                                                **
\*                |/                                                 */
package com.wix.pay.creditcard

import scala.beans.BeanProperty


/** Container that holds optional fields of a Public Credit Card (e.g., holder ID, billing address).
  *
  * @author <a href="mailto:ohadr@wix.com">Raz, Ohad</a>
  */
case class PublicCreditCardOptionalFields(@BeanProperty override val holderId: Option[String] = None,
                                          @BeanProperty override val holderName: Option[String] = None,
                                          @BeanProperty override val billingAddress: Option[String] = None,
                                          @BeanProperty override val billingPostalCode: Option[String] = None)
  extends Serializable with CommonPublicCreditCardFields
