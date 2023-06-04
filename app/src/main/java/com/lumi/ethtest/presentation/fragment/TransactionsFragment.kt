package com.lumi.ethtest.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.lumi.ethtest.presentation.viewmodel.TransactionsViewModel
import com.lumi.ethtest.ui.util.commonPadding
import com.lumi.ethtest.ui.util.convertTimestampToDate
import com.lumi.ethtest.ui.util.setAppThemeContent
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.scope.Scope

class TransactionsFragment : Fragment(), AndroidScopeComponent {

    override val scope: Scope by fragmentScope()

    private val viewModel: TransactionsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.init(
            requireArguments().getString(ADDRESS_KEY)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        setAppThemeContent {
            TransactionsUI(viewModel)
        }

    companion object {
        private const val ADDRESS_KEY = "address"
        fun getInstance(address: String) = TransactionsFragment().apply {
            arguments = bundleOf(
                ADDRESS_KEY to address
            )
        }
    }
}

@Composable
fun TransactionsUI(viewModel: TransactionsViewModel) {
    val uiState = viewModel.uiState
    if (uiState.isLoading.value) {
        Column {
            LinearProgressIndicator(
                Modifier
                    .height(2.dp)
                    .fillMaxWidth()
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = commonPadding)
        ) {
            items(
                items = uiState.transactions.value
            ) { transaction ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = commonPadding)
                        .clickable {

                        }) {
                    Column(Modifier.padding(all = commonPadding)) {
                        TransactionLabelText(text = "Date")
                        TransactionBodyText(
                            text = transaction.date.toLong().convertTimestampToDate()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        TransactionLabelText(text = "Sender address")
                        TransactionBodyText(text = transaction.senderAddress)
                        Spacer(modifier = Modifier.height(8.dp))
                        TransactionLabelText(text = "Receiver address")
                        TransactionBodyText(text = transaction.receiverAddress)
                        Spacer(modifier = Modifier.height(8.dp))
                        TransactionLabelText(text = "Eth Count")
                        TransactionBodyText(text = transaction.ethCount)
                        Spacer(modifier = Modifier.height(8.dp))
                        TransactionLabelText(text = "Direction")
                        TransactionBodyText(text = transaction.direction)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun TransactionLabelText(text: String) {
    Text(
        fontSize = MaterialTheme.typography.labelMedium.fontSize,
        text = text
    )
}

@Composable
fun TransactionBodyText(text: String) {
    Text(
        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
        text = text
    )
}