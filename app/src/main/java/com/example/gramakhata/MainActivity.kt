package com.example.gramakhata

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gramakhata.data.AppDatabase
import com.example.gramakhata.ui.*
import com.example.gramakhata.ui.theme.GramaKhataTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val context = LocalContext.current

            val database = remember {
                AppDatabase.getDatabase(context)
            }

            val coroutineScope = rememberCoroutineScope()

            val navController = rememberNavController()

            var selectedLanguage by remember {
                mutableStateOf(AppLanguage.ENGLISH)
            }

            // CURRENT USER
            var currentUserPhone by remember {
                mutableStateOf("")
            }

            var customerList by remember {
                mutableStateOf(listOf<Customer>())
            }

            var transactions by remember {
                mutableStateOf(listOf<Transaction>())
            }

            GramaKhataTheme {

                NavHost(
                    navController = navController,
                    startDestination = Routes.LOGIN
                ) {

                    // LOGIN SCREEN
                    composable(Routes.LOGIN) {

                        LoginScreen(

                            navController = navController,

                            selectedLanguage = selectedLanguage,

                            onLanguageChange = {
                                selectedLanguage = it
                            },

                            onLoginSuccess = { phone ->

                                currentUserPhone = phone

                                coroutineScope.launch {

                                    customerList =
                                        database.customerDao()
                                            .getAllCustomers(phone)

                                    transactions =
                                        database.transactionDao()
                                            .getAllTransactions(phone)
                                }

                                navController.navigate(Routes.HOME)
                            }
                        )
                    }

                    // SIGNUP SCREEN
                    composable(Routes.SIGNUP) {
                        SignupScreen(navController)
                    }

                    // FORGOT PASSWORD SCREEN
                    composable(Routes.FORGOT_PASSWORD) {
                        ForgotPasswordScreen(navController)
                    }

                    // HOME SCREEN
                    composable(Routes.HOME) {

                        HomeScreen(

                            selectedLanguage = selectedLanguage,

                            onLanguageChange = {
                                selectedLanguage = it
                            },

                            onBackClick = {
                                navController.popBackStack()
                            },

                            customers = customerList,

                            onAddClick = {
                                navController.navigate(Routes.ADD)
                            },

                            onCustomerClick = { customer ->

                                val index = customerList.indexOf(customer)

                                navController.navigate("${Routes.DETAIL}/$index")
                            },

                            onReportClick = {
                                navController.navigate(Routes.REPORT)
                            }
                        )
                    }

                    // ADD CUSTOMER SCREEN
                    composable(Routes.ADD) {

                        AddCustomerScreen(

                            navController = navController,

                            currentUserPhone = currentUserPhone,

                            onSave = { customer ->

                                coroutineScope.launch {

                                    database.customerDao()
                                        .insertCustomer(customer)

                                    customerList =
                                        database.customerDao()
                                            .getAllCustomers(currentUserPhone)

                                    navController.popBackStack()
                                }
                            }
                        )
                    }

                    // CUSTOMER DETAIL SCREEN
                    composable("${Routes.DETAIL}/{index}") { backStackEntry ->

                        val index = backStackEntry.arguments
                            ?.getString("index")
                            ?.toIntOrNull() ?: 0

                        if (
                            customerList.isNotEmpty() &&
                            index < customerList.size
                        ) {

                            CustomerDetailScreen(

                                customer = customerList[index],

                                transactions = transactions,

                                onUpdate = { updatedCustomer, transaction ->

                                    coroutineScope.launch {

                                        database.customerDao()
                                            .updateCustomer(updatedCustomer)

                                        database.transactionDao()
                                            .insertTransaction(transaction)

                                        customerList =
                                            database.customerDao()
                                                .getAllCustomers(currentUserPhone)

                                        transactions =
                                            database.transactionDao()
                                                .getAllTransactions(currentUserPhone)
                                    }
                                },

                                onDelete = { customer ->

                                    coroutineScope.launch {

                                        database.customerDao()
                                            .deleteCustomer(customer)

                                        customerList =
                                            database.customerDao()
                                                .getAllCustomers(currentUserPhone)

                                        navController.popBackStack()
                                    }
                                },

                                onBack = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }

                    // DAILY REPORT SCREEN
                    composable(Routes.REPORT) {

                        DailyReportScreen(

                            transactions = transactions,

                            customers = customerList,

                            onBack = {
                                navController.popBackStack()
                            },

                            onCustomerClick = { customer ->

                                val index = customerList.indexOf(customer)

                                navController.navigate("${Routes.DETAIL}/$index")
                            }
                        )
                    }
                }
            }
        }
    }
}