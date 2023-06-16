package com.example.mobile_dev

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.mobile_dev.data.Result
import com.example.mobile_dev.data.response.DataItem
import com.example.mobile_dev.model.FranchiseViewModel
import com.example.mobile_dev.model.ViewModelFactory
import com.example.mobile_dev.ui.theme.MobiledevTheme
class MainActivity : ComponentActivity() {
    private val franchiseViewModel: FranchiseViewModel by viewModels {
        ViewModelFactory(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var listFranchise: List<DataItem>
        franchiseViewModel.agreement().observe(this@MainActivity) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading-> {}
                    is Result.Success -> {
                        listFranchise = result.data.data
                        setContent {
                            MobiledevTheme {
                                // A surface container using the 'background' color from the theme
                                Surface(
                                    modifier = Modifier.fillMaxSize(),
                                    color = MaterialTheme.colorScheme.background
                                ) {
                                    BizzitApp(listFranchise)
                                }
                            }
                        }
                    }
                    is Result.Error -> {}
                }
            }
        }
    }
}