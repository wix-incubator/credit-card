package com.wix.pay.creditcard

import java.util.Locale

case class AddressDetailed (
  street     : Option[String]= None,
  city       : Option[String]= None,
  state      : Option[String]= None,
  postalCode : Option[String]= None,
  countryCode : Option[Locale]= None) {
  val composedAddress : String = {
    (street.getOrElse("") + " " + city.getOrElse("") + " " + state.getOrElse(" ") + " " +
      postalCode.getOrElse(" ") + countryCode.map(_.getDisplayCountry).getOrElse("")).trim
  }
}

