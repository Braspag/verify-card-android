package br.com.braspag.verify_card.models

import com.google.gson.annotations.SerializedName

data class VerifyCardResponse (
    @SerializedName("Status")
    val status: Int,

    @SerializedName("ProviderReturnCode")
    val providerReturnCode: String,

    @SerializedName("ProviderReturnMessage")
    val providerReturnMessage: String,

    @SerializedName("BinData")
    val binData: BinData
)

data class BinData (
    @SerializedName("Provider")
    val provider: String,

    @SerializedName("CardType")
    val cardType: String,

    @SerializedName("ForeignCard")
    val foreignCard: Boolean,

    @SerializedName("Code")
    val code: String,

    @SerializedName("Message")
    val message: String,

    @SerializedName("CorporateCard")
    val corporateCard: Boolean,

    @SerializedName("Issuer")
    val issuer: String,

    @SerializedName("IssuerCode")
    val issuerCode: String,

    @SerializedName("CardBin")
    val cardBin: String,

    @SerializedName("CardLast4Digits")
    val lastFourDigits: String
)
