package br.com.braspag.verify_card.internal.network

import br.com.braspag.models.HttpStatusCode
import br.com.braspag.verify_card.VerifyCardEnvironment
import br.com.braspag.verify_card.models.VerifyCardErrorResponse
import br.com.braspag.verify_card.models.VerifyCardRequest
import br.com.braspag.verify_card.models.VerifyCardResponse
import br.com.braspag.verify_card.models.VerifyCardResult
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class VerifyCardClient (
    private val accessToken: String,
    private val merchantId: String,
    private val environment: VerifyCardEnvironment = VerifyCardEnvironment.SANDBOX
) {

    fun verify(
        request: VerifyCardRequest,
        callback: (model: VerifyCardResult<VerifyCardResponse>) -> Unit
    ) {
        val webClient =
            WebClient(
                getEnvironmentUrl(environment)
            )

        val call = webClient.createService(VerifyCardApi::class.java).verify(
            authorization = accessToken.beared(),
            merchantId = merchantId,
            request = request
        )

        call.enqueue(object : Callback<VerifyCardResponse> {
            override fun onFailure(call: Call<VerifyCardResponse>, t: Throwable) {
                t.localizedMessage?.let {
                    callback.invoke(
                        VerifyCardResult(
                            result = null,
                            statusCode = HttpStatusCode.Unknown,
                            errors = listOf(
                                VerifyCardErrorResponse(
                                    code = "UNKNOWN_ERROR",
                                    message = it
                                )
                            )
                        )
                    )
                }
            }

            override fun onResponse(
                call: Call<VerifyCardResponse>,
                response: Response<VerifyCardResponse>
            ) {
                when (response.isSuccessful) {
                    true -> {
                        callback.invoke(
                            VerifyCardResult(
                                result = response.body(),
                                statusCode = response.code().toStatusCode()
                            )
                        )
                    }

                    false -> callback.invoke(
                        VerifyCardResult(
                            result = null,
                            statusCode = response.code().toStatusCode(),
                            errors = Gson().fromJson(
                                response.errorBody()?.string(),
                                Array<VerifyCardErrorResponse>::class.java
                            ).toList()
                        )
                    )
                }
            }
        })
    }

    private fun getEnvironmentUrl(environment: VerifyCardEnvironment): String {
        return if (environment == VerifyCardEnvironment.SANDBOX)
            "https://apisandbox.braspag.com.br/v2/"
        else
            "https://api.braspag.com.br/v2/"
    }

    private fun String.beared() : String {
        return "Bearer $this"
    }

    fun Int.toStatusCode(): HttpStatusCode = when (this) {
        200 -> HttpStatusCode.Ok
        201 -> HttpStatusCode.Created
        202 -> HttpStatusCode.Accepted
        204 -> HttpStatusCode.NoContent
        304 -> HttpStatusCode.NotModified
        400 -> HttpStatusCode.BadRequest
        401 -> HttpStatusCode.Unauthorized
        403 -> HttpStatusCode.Forbidden
        404 -> HttpStatusCode.NotFound
        408 -> HttpStatusCode.RequestTimeout
        409 -> HttpStatusCode.Conflict
        412 -> HttpStatusCode.PreconditionFailure
        413 -> HttpStatusCode.EntityTooLarge
        429 -> HttpStatusCode.TooManyRequests
        449 -> HttpStatusCode.RetryWith
        500 -> HttpStatusCode.InternalServerError
        503 -> HttpStatusCode.ServiceUnavailable
        else -> HttpStatusCode.Unknown
    }
}


