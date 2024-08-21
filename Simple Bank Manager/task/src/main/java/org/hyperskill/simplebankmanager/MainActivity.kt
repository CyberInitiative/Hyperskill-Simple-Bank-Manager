package org.hyperskill.simplebankmanager

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {

    private val viewModel: BankViewModel by viewModels()

    private val defaultExchangeMap = mapOf(
        "EUR" to mapOf(
            "GBP" to 0.5,
            "USD" to 2.0
        ),
        "GBP" to mapOf(
            "EUR" to 2.0,
            "USD" to 4.0
        ),
        "USD" to mapOf(
            "EUR" to 0.5,
            "GBP" to 0.25
        )
    )

    private val defaultBillInfoMap = mapOf(
    "ELEC" to Triple("Electricity", "ELEC", 45.0),
    "GAS" to Triple("Gas", "GAS", 20.0),
    "WTR" to Triple("Water", "WTR", 25.5)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val extraUsername = intent.extras?.getString("username", "Lara") ?: "Lara"
        val extraPassword = intent.extras?.getString("password", "1234") ?: "1234"
        val extraUserBalance = intent.extras?.getDouble("balance", 100.0) ?: 100.0

        val extraExchangeMap = intent.extras?.getSerializable("exchangeMap") as? Map<String, Map<String, Double>>
            ?: defaultExchangeMap

        val extraBillInfoMap = intent.extras?.getSerializable("billInfo") as? Map<String, Triple<String, String, Double>> ?: defaultBillInfoMap

        viewModel.exchangeMap = extraExchangeMap
        viewModel.billInfoMap = extraBillInfoMap

        if (viewModel.isAccountNotSet()) {
            val bankAccount = BankAccount(extraUsername, extraPassword, extraUserBalance)
            viewModel.setBankAccount(bankAccount)
        }
    }

}