package br.com.braspag.verify_card

import br.com.braspag.OAuth
import br.com.braspag.models.OAuthEnvironment
import br.com.braspag.verify_card.internal.network.VerifyCardClient
import br.com.braspag.verify_card.models.VerifyCardErrorResponse
import br.com.braspag.verify_card.models.VerifyCardRequest
import br.com.braspag.verify_card.models.VerifyCardResponse
import br.com.braspag.verify_card.models.VerifyCardResult

class VerifyCard(
    private val clientId: String,
    private val clientSecret: String,
    private val merchantId: String,
    private val environment: VerifyCardEnvironment = VerifyCardEnvironment.SANDBOX
) {
    private var oAuth: OAuth

    init {
        if (environment == VerifyCardEnvironment.PRODUCTION)
            oAuth = OAuth(clientId, clientSecret, OAuthEnvironment.PRODUCTION)
        else {
            oAuth = OAuth(clientId, clientSecret, OAuthEnvironment.SANDBOX)
        }
    }

    fun verify(
        request: VerifyCardRequest,
        callback: (model: VerifyCardResult<VerifyCardResponse>) -> Unit
    ) {
        oAuth.getToken { oAuthResult ->
            if (oAuthResult.result?.accessToken != null) {
                val client =
                    VerifyCardClient(
                        accessToken = oAuthResult.result!!.accessToken,
                        merchantId = merchantId,
                        environment = environment
                    )

                client.verify(request = request) {verifyCardResult ->
                    if (verifyCardResult.result != null) {
                        callback.invoke(verifyCardResult)
                    } else {
                        callback.invoke(
                            VerifyCardResult(
                                result = null,
                                statusCode = verifyCardResult.statusCode,
                                errors = verifyCardResult.errors
                            )
                        )
                    }
                }
            } else {
                callback.invoke(
                    VerifyCardResult(
                        result = null,
                        statusCode = oAuthResult.statusCode,
                        errors = listOf(
                            VerifyCardErrorResponse(
                                code = oAuthResult.error?.errorCode ?: "AUTH_ERROR",
                                message = oAuthResult.error?.errorDescription
                                    ?: "Authentication error"
                            )
                        )
                    )
                )
            }
        }
    }
}