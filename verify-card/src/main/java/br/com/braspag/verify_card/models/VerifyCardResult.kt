package br.com.braspag.verify_card.models

import br.com.braspag.models.HttpStatusCode

data class VerifyCardResult <T>(
    var result: T?,
    var statusCode: HttpStatusCode,
    var errors: List<VerifyCardErrorResponse?> = listOf()
)