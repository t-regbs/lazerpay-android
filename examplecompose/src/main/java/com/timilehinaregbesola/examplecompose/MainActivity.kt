package com.timilehinaregbesola.examplecompose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.timilehinaregbesola.examplecompose.ui.theme.LazerpayandroidTheme
import com.timilehinaregbesola.lazerpay.compose.LazerPayParams
import com.timilehinaregbesola.lazerpay.compose.LazerPayView
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LazerpayandroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavGraph()
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    Scaffold(scaffoldState = scaffoldState) {
        NavHost(navController = navController, startDestination = "home") {
            composable("home") { HomeScreen(navController = navController) }
            composable("lazerpay") {
                LazerPayView(
                    data = LazerPayParams(
                        publicKey = "pk_test_LIfI1h8BvlW25UMxGQQCzgSula1MnrdVY7T5TcbOEKIh5uue36",
                        name = "Timi Regbs",
                        email = "regbs@mail.com",
                        amount = "5000",
                        reference = ""
                    ),
                    onClose = {
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("closed")
                            navController.popBackStack()
                        }
                    },
                    onError = { e ->
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Error: $e")
                            navController.popBackStack()
                        }
                    },
                    onSucess = {
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Success ${it.reference}")
                            navController.popBackStack()
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { navController.navigate("lazerpay") }) {
            Text(text = "Open Lazerpay")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LazerpayandroidTheme {
//        Greeting("Android")
    }
}
