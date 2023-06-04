package com.lumi.ethtest.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Router
import com.lumi.ethtest.presentation.viewmodel.InputAddressViewModel
import com.lumi.ethtest.ui.theme.setAppThemeContent
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.scope.Scope

class InputAddressFragment : Fragment(), AndroidScopeComponent {

    override val scope: Scope by fragmentScope()

    private val viewModel: InputAddressViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        setAppThemeContent {
            MainUI(viewModel)
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainUI(viewModel: InputAddressViewModel) {
    val uiState = viewModel.uiState
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.addressInput.value,
            onValueChange = {
                uiState.addressInput.value = it
                uiState.loadButtonEnabled.value = viewModel.isValidAddress(it)
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = uiState.loadButtonEnabled.value,
            onClick = {
                viewModel.onLoadClick()
            }
        ) {
            Text(text = "Load transactions")
        }
    }
}

@Preview
@Composable
fun MainFragmentPreview() {
    MainUI(
        InputAddressViewModel(Router())
    )
}