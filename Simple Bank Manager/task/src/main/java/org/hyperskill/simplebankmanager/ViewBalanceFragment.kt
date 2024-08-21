package org.hyperskill.simplebankmanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ViewBalanceFragment : Fragment() {

    private lateinit var viewBalanceAmountTextView : TextView

    private lateinit var bankAccount: BankAccount

    private val viewModel: BankViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_view_balance, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.bankAccount.observe(requireActivity(), Observer {
            bankAccount = it
        })

        viewBalanceAmountTextView = view.findViewById<TextView?>(R.id.viewBalanceAmountTextView).apply {
            text = view.resources.getString(R.string.view_balance_amount_text, bankAccount.balance)
        }

    }

}