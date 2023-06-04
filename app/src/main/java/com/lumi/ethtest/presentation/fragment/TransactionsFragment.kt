package com.lumi.ethtest.presentation.fragment

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.lumi.ethtest.presentation.viewmodel.TransactionsViewModel
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.scope.Scope

class TransactionsFragment : Fragment(), AndroidScopeComponent {

    override val scope: Scope by fragmentScope()

    private val viewModel: TransactionsViewModel by viewModel()

    companion object {
        private const val ADDRESS_KEY = "address"
        fun getInstance(address: String) = TransactionsFragment().apply {
            arguments = bundleOf(
                ADDRESS_KEY to address
            )
        }
    }
}