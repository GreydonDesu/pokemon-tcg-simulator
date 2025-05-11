package de.thro.packsimulator.manager

import de.thro.packsimulator.data.account.Account

object AccountManager {
    private var currentAccount: Account? = null

    fun getCurrentAccount(): Account? {
        return currentAccount
    }

    fun setCurrentAccount(account: Account?) {
        currentAccount = account
    }
}