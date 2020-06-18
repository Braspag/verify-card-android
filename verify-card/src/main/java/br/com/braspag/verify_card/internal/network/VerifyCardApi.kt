package br.com.braspag.verify_card.internal.network

import br.com.braspag.verify_card.models.VerifyCardRequest
import br.com.braspag.verify_card.models.VerifyCardResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

internal interface VerifyCardApi {
    @POST("verifycard")
    fun verify(
        @Header("Authorization") authorization: String,
        @Header("MerchantId") merchantId: String,
        @Body request: VerifyCardRequest,
        @Header("Content-Type") content_type: String = "application/json",
        @Header("Accept") accept: String = "application/json"
    ): Call<VerifyCardResponse>
}