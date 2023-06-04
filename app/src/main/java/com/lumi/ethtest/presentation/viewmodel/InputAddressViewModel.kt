package com.lumi.ethtest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.github.terrakok.cicerone.Router
import com.lumi.ethtest.presentation.Screens
import com.lumi.ethtest.presentation.state.InputAddressUiState
import org.bouncycastle.jcajce.provider.digest.Keccak

class InputAddressViewModel(private val router: Router) : ViewModel() {

    val uiState: InputAddressUiState = InputAddressUiState()

    fun isValidAddress(address: String) =
        isValidEthereumAddress(address) && isValidAddressByChecksum(address)

    private fun isValidEthereumAddress(address: String): Boolean {
        val ethereumAddressPattern = "^0x([A-Fa-f0-9]{40})$".toRegex()
        return ethereumAddressPattern.matches(address)
    }

    private fun isValidAddressByChecksum(address: String): Boolean =
        address == address.lowercase().addChecksumToAddress()

    private fun String.addChecksumToAddress(): String {
        val cleanAddress = this.lowercase().removePrefix("0x")
        val hash = Keccak.Digest256().digest(cleanAddress.toByteArray())

        val checksumAddress = StringBuilder("0x")
        for (i in cleanAddress.indices) {
            val addressChar = cleanAddress[i]
            val hashByte = hash[i / 2]
            val hashValue = if (i % 2 == 0) {
                (hashByte.toInt() and 0xF0) ushr 4
            } else {
                hashByte.toInt() and 0x0F
            }

            checksumAddress.append(
                if (hashValue >= 8) {
                    addressChar.uppercase()
                } else {
                    addressChar
                }
            )
        }

        return checksumAddress.toString()
    }

    fun onLoadClick() {
        router.navigateTo(Screens.transactionsScreen(uiState.addressInput.value))
    }
}