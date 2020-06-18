package br.com.braspag

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import br.com.braspag.verify_card.*
import br.com.braspag.verify_card.models.Card
import br.com.braspag.verify_card.models.VerifyCardRequest
import br.com.braspag.verify_card.models.cardType
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        result_content.visibility = View.INVISIBLE
        loading.visibility = View.VISIBLE

        val verifyCard = VerifyCard(
            clientId = "CLIENT-ID",
            clientSecret = "CLIENT-SECRET",
            merchantId = "MERCHANT-ID",
            environment = VerifyCardEnvironment.SANDBOX
        )

        verifyCard.verify(
            request = VerifyCardRequest(
                provider = "Cielo30",
                card = Card(
                    cardNumber = "111122******4444",
                    holder = "Maurici F Junior",
                    securityCode = "123",
                    expirationDate = "01/2030",
                    brand = "Master",
                    type = cardType.DEBIT_CARD
                )
            )
        ) {
            if (it.errors.isNotEmpty()) {
                errors_list_view.adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    it.errors
                )
            }
            if (it.result != null) {
                with (it.result!!) {
                    result_status.text = status.toString()
                    result_provider_return_code.text = providerReturnCode
                    result_provider_return_message.text = providerReturnMessage
                    result_bin_data_provider.text = binData.provider
                    result_bin_data_card_type.text = binData.cardType
                    result_bin_data_foreign_card.text = binData.foreignCard.toString()
                    result_bin_data_code.text = binData.code
                    result_bin_data_message.text = binData.message
                    result_bin_data_corporate_card.text = binData.corporateCard.toString()
                    result_bin_data_issuer.text = binData.issuer
                    result_bin_data_issuer_code.text = binData.issuerCode
                    result_bin_data_card_bin.text = binData.cardBin
                    result_bin_data_last_four.text = binData.lastFourDigits
                }
            }

            loading.visibility = View.INVISIBLE
            result_content.visibility = View.VISIBLE
        }
    }
}