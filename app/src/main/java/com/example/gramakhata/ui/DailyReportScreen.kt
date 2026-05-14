package com.example.gramakhata.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gramakhata.Customer
import com.example.gramakhata.Transaction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyReportScreen(
    transactions: List<Transaction>,
    customers: List<Customer>,
    onBack: () -> Unit,
    onCustomerClick: (Customer) -> Unit
) {

    val totalGiven = transactions
        .filter { it.type == "GIVE" }
        .sumOf { it.amount }

    val totalTaken = transactions
        .filter { it.type == "TAKE" }
        .sumOf { it.amount }

    val net = totalGiven - totalTaken

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        // TOP BAR
        TopAppBar(

            title = {
                Text("Daily Report")
            },

            navigationIcon = {

                IconButton(
                    onClick = {
                        onBack()
                    }
                ) {

                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Text(
                text = "Daily Report",
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Total Given: ₹$totalGiven",
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Total Collected: ₹$totalTaken",
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Net Balance: ₹$net",
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Transactions",
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn {

                items(transactions.reversed()) { tx ->

                    val customer =
                        customers.find {
                            it.id == tx.customerId
                        }

                    val customerName =
                        customer?.name ?: "Unknown"

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .clickable {

                                if (customer != null) {

                                    onCustomerClick(customer)
                                }
                            }
                    ) {

                        Column(
                            modifier = Modifier.padding(12.dp)
                        ) {

                            Text(
                                text = customerName,
                                fontSize = 18.sp
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = tx.dateTime,
                                color = Color.Gray,
                                fontSize = 12.sp
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "${tx.type} ₹${tx.amount}",

                                color =
                                    if (tx.type == "GIVE")
                                        MaterialTheme.colorScheme.primary
                                    else
                                        MaterialTheme.colorScheme.error,

                                fontSize = 17.sp
                            )
                        }
                    }
                }
            }
        }
    }
}