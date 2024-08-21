package org.hyperskill.simplebankmanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer

class TransferFundsFragment : Fragment() {

    private lateinit var transferFundsAccountEditText: EditText
    private lateinit var transferFundsAmountEditText: EditText
    private lateinit var transferFundsButton: Button

    private lateinit var bankAccount: BankAccount

    private val viewModel: BankViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_transfer_funds, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        transferFundsAccountEditText = view.findViewById(R.id.transferFundsAccountEditText)
        transferFundsAmountEditText = view.findViewById(R.id.transferFundsAmountEditText)
        transferFundsButton = view.findViewById(R.id.transferFundsButton)

        viewModel.bankAccount.observe(requireActivity(), Observer {
            bankAccount = it
        })

        transferFundsButton.setOnClickListener {
            val transferFundsAccountText = transferFundsAccountEditText.text.toString()
            val transferFundsAmountText = transferFundsAmountEditText.text.toString()

            var isAccountNumberCorrect = false
            var isAmountCorrect = false

            if (!transferFundsAccountText.matches("^(sa|ca)\\d{4}$".toRegex())) {
                isAccountNumberCorrect = false
                transferFundsAccountEditText.error = "Invalid account number"
            } else {
                isAccountNumberCorrect = true
            }

            if (transferFundsAmountText.isEmpty() || transferFundsAmountText.isBlank() || !(transferFundsAmountText.toDouble() > 0.0)  ) {
                isAmountCorrect = false
                transferFundsAmountEditText.error = "Invalid amount"
            } else {
                isAmountCorrect = false
                if (transferFundsAmountText.toDouble() > bankAccount.balance) {
                    val toastMessage = view.resources.getString(
                        R.string.transfer_failed_not_enough_money,
                        transferFundsAmountText.toDouble()
                    )
                    Toast.makeText(view.context, toastMessage, Toast.LENGTH_LONG).show()
                } else {
                    isAmountCorrect = true
                    val toastMessage = view.resources.getString(
                        R.string.transfer_successful,
                        transferFundsAmountText.toDouble(),
                        transferFundsAccountText
                    )

                    if(isAmountCorrect && isAccountNumberCorrect) {
                        viewModel.updateBalance(bankAccount.balance - transferFundsAmountText.toDouble())
                        Toast.makeText(view.context, toastMessage, Toast.LENGTH_LONG).show()
                        findNavController().popBackStack()
                    }
                }
            }
        }

    }

}