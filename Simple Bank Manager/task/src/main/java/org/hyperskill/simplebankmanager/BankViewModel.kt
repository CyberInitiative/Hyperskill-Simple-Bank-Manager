package org.hyperskill.simplebankmanager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BankViewModel : ViewModel() {

    private val _bankAccount = MutableLiveData<BankAccount>()
    val bankAccount: LiveData<BankAccount> get() = _bankAccount

    lateinit var exchangeMap : Map<String, Map<String, Double>>
    lateinit var billInfoMap : Map<String, Triple<String, String, Double>>

    fun setBankAccount(account: BankAccount) {
        _bankAccount.value = account
    }

    fun updateBalance(newBalance: Double) {
        _bankAccount.value?.balance = newBalance
        _bankAccount.value = _bankAccount.value
    }

    fun isAccountNotSet(): Boolean {
        return _bankAccount.value == null
    }
}