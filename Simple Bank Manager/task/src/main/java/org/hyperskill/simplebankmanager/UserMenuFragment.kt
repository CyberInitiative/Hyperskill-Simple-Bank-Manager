package org.hyperskill.simplebankmanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
class UserMenuFragment : Fragment() {

    private lateinit var userMenuWelcomeTextView : TextView

    private lateinit var userMenuViewBalanceButton : Button
    private lateinit var userMenuTransferFundsButton : Button
    private lateinit var userMenuExchangeCalculatorButton : Button
    private lateinit var userMenuPayBillsButton : Button

    private lateinit var bankAccount: BankAccount

    private val viewModel: BankViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userMenuWelcomeTextView = view.findViewById(R.id.userMenuWelcomeTextView)

        viewModel.bankAccount.observe(requireActivity(), Observer {
            bankAccount = it
        })

        userMenuWelcomeTextView.text = view.resources.getString(R.string.user_menu_welcome_text, bankAccount.username)

        userMenuViewBalanceButton = view.findViewById(R.id.userMenuViewBalanceButton)
        userMenuViewBalanceButton.setOnClickListener {
            findNavController().navigate(R.id.viewBalanceFragment)
        }

        userMenuTransferFundsButton = view.findViewById(R.id.userMenuTransferFundsButton)
        userMenuTransferFundsButton.setOnClickListener {
            findNavController().navigate(R.id.transferFundsFragment)
        }

        userMenuExchangeCalculatorButton = view.findViewById(R.id.userMenuExchangeCalculatorButton)
        userMenuExchangeCalculatorButton.setOnClickListener {
            findNavController().navigate(R.id.calculateExchangeFragment)
        }

        userMenuPayBillsButton = view.findViewById(R.id.userMenuPayBillsButton)
        userMenuPayBillsButton.setOnClickListener {
            findNavController().navigate(R.id.payBillsFragment)
        }

    }
}