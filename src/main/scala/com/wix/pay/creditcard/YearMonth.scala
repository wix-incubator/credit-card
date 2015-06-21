/*      __ __ _____  __                                              *\
**     / // // /_/ |/ /          Wix                                 **
**    / // // / /|   /           (c) 2006-2014, Wix LTD.             **
**   / // // / //   |            http://www.wix.com/                 **
**   \__/|__/_//_/| |                                                **
\*                |/                                                 */
package com.wix.pay.creditcard


/** A container for year and month pair, mainly used for credit card expiration date.
  *
  * @author <a href="mailto:ohadr@wix.com">Raz, Ohad</a>
  */
case class YearMonth(year: Int, month: Int) extends Serializable
