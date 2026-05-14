package com.example.gramakhata.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gramakhata.Customer
import com.example.gramakhata.Transaction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerDetailScreen(
    customer: Customer,
    transactions: List<Transaction>,
    onUpdate: (Customer, Transaction) -> Unit,
    onDelete: (Customer) -> Unit,
    onBack: () -> Unit
) {

    val context = LocalContext.current

    var amountInput by remember {
        mutableStateOf("")
    }

    var showReminderDialog by remember {
        mutableStateOf(false)
    }

    val customerTransactions =
        transactions.filter {
            it.customerId == customer.id
        }

    // WHATSAPP MESSAGE
    val message =

        "🙏 Sri Ganesha Store\n\n" +

                "Dear ${customer.name},\n\n" +

                "This is a reminder regarding your pending balance in Grama Khata.\n\n" +

                "Pending Amount: ₹${customer.balance}\n\n" +

                "Please clear the due amount at the earliest.\n\n" +

                "Thank you.\n\n" +

                "- Niveditha\n" +
                "Sri Ganesha Store"

    // WHATSAPP FUNCTION
    fun sendWhatsAppReminder() {

        val url =
            "https://wa.me/91${customer.phone}?text=${Uri.encode(message)}"

        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(url)
        )

        context.startActivity(intent)
    }

    // SHARE LEDGER FUNCTION
    fun shareLedger() {

        val ledgerText = buildString {

            append("Sri Ganesha Store\n\n")

            append("Customer: ${customer.name}\n")
            append("Phone: ${customer.phone}\n")

            if (customer.notes.isNotEmpty()) {
                append("Notes: ${customer.notes}\n")
            }

            if (customer.dueDate.isNotEmpty()) {
                append("Due Date: ${customer.dueDate}\n")
            }

            append("\nCurrent Balance: ₹${customer.balance}\n\n")

            append("Transactions:\n\n")

            customerTransactions.reversed().forEach {

                append("${it.dateTime}\n")
                append("${it.type} ₹${it.amount}\n\n")
            }
        }

        val intent = Intent(Intent.ACTION_SEND)

        intent.type = "text/plain"

        intent.putExtra(
            Intent.EXTRA_TEXT,
            ledgerText
        )

        context.startActivity(
            Intent.createChooser(
                intent,
                "Share Ledger"
            )
        )
    }

    // REMINDER DIALOG
    if (showReminderDialog) {

        AlertDialog(

            onDismissRequest = {
                showReminderDialog = false
            },

            title = {
                Text("⚠ High Pending Balance")
            },

            text = {
                Text(
                    "${customer.name} has crossed ₹1000 pending balance.\n\nSend WhatsApp reminder now?"
                )
            },

            confirmButton = {

                Button(
                    onClick = {

                        showReminderDialog = false

                        sendWhatsAppReminder()
                    }
                ) {

                    Text("Send Reminder")
                }
            },

            dismissButton = {

                OutlinedButton(
                    onClick = {
                        showReminderDialog = false
                    }
                ) {

                    Text("Later")
                }
            }
        )
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        // TOP BAR
        TopAppBar(

            title = {
                Text("Customer Details")
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
                text = customer.name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Phone: ${customer.phone}",
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Balance: ₹${customer.balance}",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            // NOTES
            if (customer.notes.isNotEmpty()) {

                Text(
                    text = "Notes: ${customer.notes}",
                    fontSize = 16.sp,
                    color = Color.DarkGray
                )

                Spacer(modifier = Modifier.height(6.dp))
            }

            // DUE DATE
            if (customer.dueDate.isNotEmpty()) {

                Text(
                    text = "Due Date: ${customer.dueDate}",
                    fontSize = 16.sp,
                    color = Color.Red,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // AMOUNT FIELD
            OutlinedTextField(

                value = amountInput,

                onValueChange = {
                    amountInput = it
                },

                label = {

                    Text(
                        text = "Enter Amount",
                        color = Color.Black
                    )
                },

                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                ),

                modifier = Modifier.fillMaxWidth(),

                colors = OutlinedTextFieldDefaults.colors(

                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,

                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.DarkGray,

                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,

                    cursorColor = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(18.dp))

            // GIVE BUTTON
            Button(
                onClick = {

                    val amt =
                        amountInput.toIntOrNull() ?: 0

                    val newBalance =
                        customer.balance + amt

                    val updated =
                        customer.copy(
                            balance = newBalance
                        )

                    val transaction =
                        Transaction(
                            customerId = customer.id,
                            amount = amt,
                            type = "GIVE",
                            userPhone = customer.userPhone,

                            dateTime =
                                java.text.SimpleDateFormat(
                                    "dd MMM yyyy • hh:mm a",
                                    java.util.Locale.getDefault()
                                ).format(
                                    java.util.Date()
                                )
                        )

                    onUpdate(updated, transaction)

                    if (newBalance > 1000) {

                        showReminderDialog = true
                    }

                    amountInput = ""
                },

                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = "Give",
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // TAKE BUTTON
            Button(
                onClick = {

                    val amt =
                        amountInput.toIntOrNull() ?: 0

                    val updated =
                        customer.copy(
                            balance = customer.balance - amt
                        )

                    val transaction =
                        Transaction(
                            customerId = customer.id,
                            amount = amt,
                            type = "TAKE",
                            userPhone = customer.userPhone,

                            dateTime =
                                java.text.SimpleDateFormat(
                                    "dd MMM yyyy • hh:mm a",
                                    java.util.Locale.getDefault()
                                ).format(
                                    java.util.Date()
                                )
                        )

                    onUpdate(updated, transaction)

                    amountInput = ""
                },

                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = "Take",
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // WHATSAPP REMINDER BUTTON
            Button(
                onClick = {

                    sendWhatsAppReminder()
                },

                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = "Send WhatsApp Reminder"
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // SHARE LEDGER BUTTON
            Button(
                onClick = {

                    shareLedger()
                },

                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = "Share Customer Ledger"
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // DELETE BUTTON
            Button(
                onClick = {
                    onDelete(customer)
                },

                modifier = Modifier.fillMaxWidth(),

                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {

                Text("Delete Customer")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Transaction History",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn {

                items(customerTransactions.reversed()) { tx ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp)
                    ) {

                        Column(
                            modifier = Modifier.padding(12.dp)
                        ) {

                            Text(
                                text = tx.dateTime,
                                color = Color.Gray,
                                fontSize = 12.sp
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "${tx.type} ₹${tx.amount}",

                                fontSize = 18.sp,

                                fontWeight = FontWeight.Bold,

                                color =
                                    if (tx.type == "GIVE")
                                        Color(0xFF2E7D32)
                                    else
                                        Color.Red
                            )
                        }
                    }
                }
            }
        }
    }
}