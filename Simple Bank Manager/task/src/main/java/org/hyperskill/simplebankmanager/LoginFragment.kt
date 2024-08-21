package org.hyperskill.simplebankmanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

class LoginFragment : Fragment() {

    private lateinit var loginUsername: EditText
    private lateinit var loginPassword: EditText
    private lateinit var loginButton: Button

    private lateinit var bankAccount: BankAccount

    private val viewModel: BankViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginUsername = view.findViewById(R.id.loginUsername)
        loginPassword = view.findViewById(R.id.loginPassword)
        loginButton = view.findViewById(R.id.loginButton)

        viewModel.bankAccount.observe(requireActivity(), Observer {
            bankAccount = it
        })

        loginButton.setOnClickListener {
            if (bankAccount.username == loginUsername.text.toString()
                &&
                bankAccount.password == loginPassword.text.toString()
            ) {
                Toast.makeText(requireContext(), "logged in", Toast.LENGTH_SHORT).show()

                findNavController().navigate(R.id.userMenuFragment)

            } else {
                Toast.makeText(requireContext(), "invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }

    }

}