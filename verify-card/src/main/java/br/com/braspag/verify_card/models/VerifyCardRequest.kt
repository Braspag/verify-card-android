package br.com.braspag.verify_card.models

import com.google.gson.annotations.SerializedName

data class VerifyCardRequest (
    @SerializedName("Provider")
    val provider: String,

    @SerializedName("Card")
    val card: Card
)

data class Card (
    @SerializedName("CardNumber")
    val cardNumber: String,

    @SerializedName("Holder")
    val holder: String,

    @SerializedName("ExpirationDate")
    val expirationDate: String,

    @SerializedName("SecurityCode")
    val securityCode: String,

    @SerializedName("Brand")
    val brand: String,

    @SerializedName("Type")
    val type: cardType
)

enum class cardType {
    @SerializedName("DebitCard")
    DEBIT_CARD,

    @SerializedName("CreditCard")
    CREDIT_CARD
}