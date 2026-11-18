package com.utxicotepec.registroparcela

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.cursosant.jcform.ui.compmonents.AntDialogInfo
import com.utxicotepec.registroparcela.ui.theme._8RegistroParcelaTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _8RegistroParcelaTheme {
                val snackbarHostState = remember { SnackbarHostState() }
                val scope = rememberCoroutineScope()

                var openDialog by remember { mutableStateOf(false) }
                var cleanForm by remember { mutableStateOf(false) }
                var parcelaFilled: Parcela? = null

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(snackbarHostState) }
                ) { innerPadding ->
                    MainView(
                        Modifier.padding(innerPadding),
                        isClean = cleanForm,
                        onCleaned = { cleanForm = false },
                        onError = { error ->
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = error,
                                    actionLabel = getString(R.string.dialog_ok),
                                    duration = SnackbarDuration.Long
                                )
                            }
                        }
                    ) { parcela ->
                        Log.i("TUS_INICIALES", "onCreate: $parcela")
                        parcelaFilled = parcela
                        openDialog = true
                    }

                    if (openDialog) {
                        parcelaFilled?.let { parcela ->
                            AntDialogInfo(
                                info = parcelaFormatter(parcela),
                                titleRes = R.string.dialog_title,
                                confirmRes = R.string.dialog_clean
                            ) { clean ->
                                cleanForm = clean
                                openDialog = false
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun LocalPreview() {
    MainPreview()
}