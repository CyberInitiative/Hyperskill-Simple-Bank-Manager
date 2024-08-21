package org.hyperskill.simplebankmanager

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.activityViewModels

class PayBillsFragment : Fragment() {

    private lateinit var payBillsCodeInputEditText: EditText
    private lateinit var payBillsShowBillInfoButton: Button

    private val viewModel: BankViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pay_bills, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        payBillsCodeInputEditText = view.findViewById(R.id.payBillsCodeInputEditText)
        payBillsShowBillInfoButton = view.findViewById(R.id.payBillsShowBillInfoButton)
        payBillsShowBillInfoButton.setOnClickListener {
            val inputFromEditText = payBillsCodeInputEditText.text.toString()
            if (viewModel.billInfoMap.containsKey(inputFromEditText)) {
                showConfirmDialog(viewModel.billInfoMap[inputFromEditText]!!)
            } else {
                showFailureAlertDialog("Wrong code")
            }
        }

    }

    private fun showFailureAlertDialog(errorMessage: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Error")
            .setMessage(errorMessage)
            .setPositiveButton(android.R.string.ok, null)
            .show()
    }

    private fun showConfirmDialog(billInfo: Triple<String, String, Double>) {
        AlertDialog.Builder(requireContext())
            .setTitle("Bill info")
            .setMessage(
                requireContext().getString(
                    R.string.bill_pay_confirm_dialog_message,
                    billInfo.first,
                    billInfo.second,
                    billInfo.third
                )
            )
            .setPositiveButton(requireContext().getString(R.string.confirm)) { _, _ ->
                val accountBalance = viewModel.bankAccount.value!!.balance
                if (accountBalance >= billInfo.third) {
                    viewModel.updateBalance(accountBalance - billInfo.third)
                    Toast.makeText(requireContext(), "Payment for bill ${billInfo.first}, was successful", Toast.LENGTH_SHORT).show()
                } else {
                    showFailureAlertDialog("Not enough funds")
                }
            }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }
}