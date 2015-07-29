/*      __ __ _____  __                                              *\
**     / // // /_/ |/ /          Wix                                 **
**    / // // / /|   /           (c) 2006-2015, Wix LTD.             **
**   / // // / //   |            http://www.wix.com/                 **
**   \__/|__/_//_/| |                                                **
\*                |/                                                 */
package com.wix.pay.creditcard


/** Container that holds optional fields of a Credit Card (e.g., csc, holder ID).
  *
  * @author <a href="mailto:ohadr@wix.com">Raz, Ohad</a>
  */
case class CreditCardOptionalFields(override val csc: Option[String] = None,
                                    publicFields: Option[PublicCreditCardOptionalFields] = None)
    extends Serializable with CommonCreditCardFields {

  override val holderId: Option[String] = publicFields flatMap(_.holderId)
  override val holderName: Option[String] = publicFields flatMap(_.holderName)
  override val billingAddress: Option[String] = publicFields flatMap(_.billingAddress)
  override val billingPostalCode: Option[String] = publicFields flatMap(_.billingPostalCode)
  override val billingAddressDetailed: Option[AddressDetailed] = publicFields flatMap(_.billingAddressDetailed)

  def withBillingAddressDetailed(billingAddress : Option[AddressDetailed]) : CreditCardOptionalFields = {
    publicFields match {
      case None => copy(publicFields =
        Some(new PublicCreditCardOptionalFields(holderId = None, holderName = None, billingAddressDetailed = billingAddress)))
      case Some(pubFields) => copy(publicFields = Some(
        new PublicCreditCardOptionalFields(
          holderId =  pubFields.holderId,
          holderName =  pubFields.holderName,
          billingAddressDetailed =  billingAddress
      )))
    }
  }

  def fillMissing(other: CreditCardOptionalFields): CreditCardOptionalFields = {
    def bothAddressDetailedIsNull(thisFields: PublicCreditCardOptionalFields, otherFields: PublicCreditCardOptionalFields): Boolean = {
      (thisFields.billingAddressDetailed == None) && (otherFields.billingAddressDetailed == None)
    }
    val fieldsMerged = (this.publicFields, other.publicFields) match {
      case (Some(thisFields), Some(otherFields)) =>
        if (bothAddressDetailedIsNull(thisFields, otherFields) )
          Some(new PublicCreditCardOptionalFields(
            holderId = thisFields.holderId.orElse(otherFields.holderId),
            holderName = thisFields.holderName.orElse(otherFields.holderName),
            billingAddress = thisFields.billingAddress.orElse(otherFields.billingAddress),
            billingPostalCode = thisFields.billingPostalCode.orElse(otherFields.billingPostalCode))
          )
        else {
          Some(new PublicCreditCardOptionalFields(
            holderId = thisFields.holderId.orElse(otherFields.holderId),
            holderName = thisFields.holderName.orElse(otherFields.holderName),
            billingAddressDetailed = thisFields.billingAddressDetailed.orElse(otherFields.billingAddressDetailed))
          )
        }


      case (None, Some(fields)) =>
        Option(fields)

      case (Some(fields), None) =>
        Option(fields)

      case _ =>
        None
    }
    val cscMerged = this.csc.orElse(other.csc)

    CreditCardOptionalFields(csc = cscMerged, publicFields = fieldsMerged)
  }
}


/** The companion object of the [[CreditCardOptionalFields]] case class, introduces alternative means to create credit
  * card optional fields, e.g., by specifying the Public Credit Card attributes.
  *
  * @author <a href="mailto:ohadr@wix.com">Raz, Ohad</a>
  */
object CreditCardOptionalFields {

  def empty(): CreditCardOptionalFields = {
    withFields()
  }

  def withFields(csc: Option[String] = None,
                 holderId: Option[String] = None,
                 holderName: Option[String] = None,
                 billingAddress: Option[String] = None,
                 billingPostalCode: Option[String] = None): CreditCardOptionalFields = {
    CreditCardOptionalFields(
      csc,
      (holderId, holderName, billingAddress, billingPostalCode) match {
        case (None, None, None, None) => None

        case _ =>
          Some(new PublicCreditCardOptionalFields(
            holderId = holderId,
            holderName = holderName,
            billingAddress = billingAddress,
            billingPostalCode = billingPostalCode))
      })
  }
}
