package com.lumi.ethtest.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.lumi.ethtest.presentation.state.LoadingState
import com.lumi.ethtest.presentation.viewmodel.TransactionUIModel
import com.lumi.ethtest.presentation.viewmodel.TransactionsViewModel
import com.lumi.ethtest.ui.util.AppStrings
import com.lumi.ethtest.ui.util.commonPadding
import com.lumi.ethtest.ui.util.convertTimestampToDate
import com.lumi.ethtest.ui.util.dialogMaxWidth
import com.lumi.ethtest.ui.util.dialogMinWidth
import com.lumi.ethtest.ui.util.dialogPadding
import com.lumi.ethtest.ui.util.dialogTitlePadding
import com.lumi.ethtest.ui.util.setAppThemeContent
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope

class TransactionsFragment : Fragment(), AndroidScopeComponent {

    override val scope: Scope by fragmentScope()

    private val viewModel: TransactionsViewModel by viewModel(
        parameters = { parametersOf(requireArguments().getString(ADDRESS_KEY)) }
    )

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
    val openDialog = remember { mutableStateOf(false) }
    if (openDialog.value) {
        uiState.selectedTransaction.value?.let {
            TransactionDialog(openDialog, it)
        }
    }
    when (val loadingState = uiState.loadingState.value) {
        is LoadingState.Loading -> {
            Column {
                LinearProgressIndicator(
                    Modifier
                        .height(2.dp)
                        .fillMaxWidth()
                )
            }
        }

        is LoadingState.Failed -> {
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .padding(all = commonPadding)
            ) {
                Column {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.error
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = commonPadding)
                    ) {
                        Text(
                            modifier = Modifier.padding(all = commonPadding),
                            text = loadingState.errorMessage
                        )
                    }
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            viewModel.onRefreshClick()
                        }
                    ) {
                        Text(text = stringResource(AppStrings.button_refresh))
                    }
                }
            }
        }

        is LoadingState.Success -> {
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
                                viewModel.onTransactionClick(transaction)
                                openDialog.value = true
                            }) {
                        Column(Modifier.padding(all = commonPadding)) {
                            TransactionLabelText(text = stringResource(AppStrings.transaction_card_date))
                            TransactionBodyText(
                                text = transaction.date.toLong().convertTimestampToDate()
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            TransactionLabelText(text = stringResource(AppStrings.transaction_card_sender_address))
                            TransactionBodyText(text = transaction.senderAddress)
                            Spacer(modifier = Modifier.height(8.dp))
                            TransactionLabelText(text = stringResource(AppStrings.transaction_card_receiver_address))
                            TransactionBodyText(text = transaction.receiverAddress)
                            Spacer(modifier = Modifier.height(8.dp))
                            TransactionLabelText(text = stringResource(AppStrings.transaction_card_eth_count))
                            TransactionBodyText(text = transaction.ethCount)
                            Spacer(modifier = Modifier.height(8.dp))
                            TransactionLabelText(text = stringResource(AppStrings.transaction_card_direction))
                            TransactionBodyText(text = transaction.direction)
                        }
                    }
                    Spacer(modifier = Modifier.height(commonPadding))
                }
            }
        }
    }
}

@Composable
fun TransactionDialog(openDialog: MutableState<Boolean>, transaction: TransactionUIModel) {
    Dialog(
        onDismissRequest = {
            openDialog.value = false
        }
    ) {
        Card(
            Modifier
                .sizeIn(minWidth = dialogMinWidth, maxWidth = dialogMaxWidth)
                .padding(dialogPadding)
        ) {
            val scroll = rememberScrollState()
            Column(Modifier.padding(horizontal = commonPadding)) {
                Text(
                    modifier = Modifier.padding(dialogTitlePadding),
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                    text = stringResource(AppStrings.transaction_dialog_input_title)
                )
                Text(
                    modifier = Modifier
                        .sizeIn(maxHeight = 200.dp)
                        .verticalScroll(scroll),
                    text = transaction.input
                )
                transaction.decodedFunction?.let { function ->
                    Text(
                        modifier = Modifier.padding(vertical = commonPadding),
                        fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                        text = stringResource(AppStrings.transaction_dialog_decoded_function)
                    )
                    Text(text = function.toString())
                }

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = commonPadding, top = commonPadding),
                    onClick = {
                        openDialog.value = false
                    }) {
                    Text(stringResource(AppStrings.button_ok))
                }
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