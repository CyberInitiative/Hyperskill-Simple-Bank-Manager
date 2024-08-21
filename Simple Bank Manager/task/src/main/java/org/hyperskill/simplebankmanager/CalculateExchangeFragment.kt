package org.hyperskill.simplebankmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

class CalculateExchangeFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var calculateExchangeFromSpinner: Spinner
    private lateinit var calculateExchangeToSpinner: Spinner
    private lateinit var calculateExchangeDisplayTextView: TextView
    private lateinit var calculateExchangeAmountEditText: EditText
    private lateinit var calculateExchangeButton: Button

    private lateinit var exchangeFrom: String
    private lateinit var exchangeTo: String

    private val viewModel: BankViewModel by activityViewModels()
    private lateinit var spinnerItems: List<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calculate_exchange, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calculateExchangeFromSpinner = view.findViewById(R.id.calculateExchangeFromSpinner)
        calculateExchangeToSpinner = view.findViewById(R.id.calculateExchangeToSpinner)
        calculateExchangeDisplayTextView = view.findViewById(R.id.calculateExchangeDisplayTextView)
        calculateExchangeAmountEditText = view.findViewById(R.id.calculateExchangeAmountEditText)
        calculateExchangeButton = view.findViewById(R.id.calculateExchangeButton)

        spinnerItems = viewModel.exchangeMap.keys.toList()
        val arrayAdapter =
            ArrayAdapter(view.context, android.R.layout.simple_spinner_item, spinnerItems)
        calculateExchangeFromSpinner.onItemSelectedListener = this
        calculateExchangeToSpinner.onItemSelectedListener = this

        calculateExchangeFromSpinner.adapter = arrayAdapter
        calculateExchangeToSpinner.adapter = arrayAdapter

        calculateExchangeFromSpinner.setSelection(0)
        calculateExchangeToSpinner.setSelection(1)

        calculateExchangeButton.setOnClickListener {
            calculateExchange()
        }
    }

    private fun calculateExchange() {
        if (calculateExchangeAmountEditText.text.toString()
                .isEmpty() || calculateExchangeAmountEditText.text.toString().isBlank()
        ) {
            Toast.makeText(requireActivity(), "Enter amount", Toast.LENGTH_LONG)
                .show()
            return
        }

        if (exchangeFrom != null && exchangeTo != null) {
            val input = calculateExchangeAmountEditText.text.toString().toDouble()

            val exchangeFromMap = viewModel.exchangeMap[exchangeFrom]
            val exchangeRate = exchangeFromMap!![exchangeTo]

            val exchangeFromCurrencySign = currencyCodeToCurrencySign(exchangeFrom)
            val exchangeToCurrencySign = currencyCodeToCurrencySign(exchangeTo)

            val exchangeResult = (input * exchangeRate!!)
            calculateExchangeDisplayTextView.text = requireActivity()
                .resources
                .getString(
                    R.string.currency_exchange_result,
                    exchangeFromCurrencySign,
                    input,
                    exchangeToCurrencySign,
                    exchangeResult
                )
        }
    }

    private fun currencyCodeToCurrencySign(currencyCode: String): String {
        return when (currencyCode) {
            "EUR" -> "€"
            "GBP" -> "£"
            "USD" -> "$"
            else -> {
                "error"
            }
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        when (p0!!.id) {
            R.id.calculateExchangeFromSpinner -> {
                exchangeFrom = p0.getItemAtPosition(p2).toString()
                checkIfUserSelectedSameItem(
                    p2,
                    calculateExchangeFromSpinner,
                    calculateExchangeToSpinner
                )
            }

            R.id.calculateExchangeToSpinner -> {
                exchangeTo = p0.getItemAtPosition(p2).toString()
                checkIfUserSelectedSameItem(
                    p2,
                    calculateExchangeToSpinner,
                    calculateExchangeFromSpinner
                )
            }
        }

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        when (p0!!.id) {
            R.id.calculateExchangeFromSpinner -> {
                exchangeFrom = calculateExchangeFromSpinner.selectedItem.toString()
            }

            R.id.calculateExchangeToSpinner -> {
                exchangeTo = calculateExchangeToSpinner.selectedItem.toString()
            }
        }
    }

    private fun checkIfUserSelectedSameItem(
        itemPosition: Int,
        firstSpinner: Spinner,
        secondSpinner: Spinner
    ) {
        if (firstSpinner.getItemAtPosition(itemPosition) == secondSpinner.selectedItem) {
            val newItemIndex: Int
            val indexInItems = spinnerItems.indexOf(secondSpinner.selectedItem)
            if (indexInItems == spinnerItems.lastIndex) {
                newItemIndex = 0
            } else {
                newItemIndex = indexInItems + 1
            }
            secondSpinner.setSelection(newItemIndex)
            Toast.makeText(requireActivity(), "Cannot convert to same currency", Toast.LENGTH_LONG)
                .show()
        }
    }

}