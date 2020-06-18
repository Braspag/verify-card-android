package br.com.braspag.verify_card.models

import com.google.gson.annotations.SerializedName

data class VerifyCardErrorResponse(
    @SerializedName("Code")
    val code: String,

    @SerializedName("Message")
    val message: String
)