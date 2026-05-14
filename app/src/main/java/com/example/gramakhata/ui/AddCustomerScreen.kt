package com.example.gramakhata.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gramakhata.Customer
import com.example.gramakhata.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCustomerScreen(
    navController: NavController,
    currentUserPhone: String,
    onSave: (Customer) -> Unit
) {

    var name by remember { mutableStateOf("") }

    var phone by remember { mutableStateOf("") }

    // NOTES
    var notes by remember {
        mutableStateOf("")
    }

    // DUE DATE
    var dueDate by remember {
        mutableStateOf("")
    }

    var isError by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        // TOP APP BAR
        TopAppBar(

            title = {
                Text(text = stringResource(R.string.add_customer))
            },

            navigationIcon = {

                IconButton(
                    onClick = {
                        navController.popBackStack()
                    }
                ) {

                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        )

        // SCREEN CONTENT
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Text(
                text = stringResource(R.string.add_customer),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            // NAME FIELD
            OutlinedTextField(
                value = name,

                onValueChange = {
                    name = it
                    isError = false
                },

                label = {
                    Text(
                        text = stringResource(R.string.customer_name),
                        color = Color.Black
                    )
                },

                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                ),

                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(16.dp),

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

            Spacer(modifier = Modifier.height(16.dp))

            // PHONE FIELD
            OutlinedTextField(
                value = phone,

                onValueChange = {
                    phone = it
                    isError = false
                },

                label = {
                    Text(
                        text = stringResource(R.string.phone_number),
                        color = Color.Black
                    )
                },

                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                ),

                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(16.dp),

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

            Spacer(modifier = Modifier.height(16.dp))

            // NOTES FIELD
            OutlinedTextField(

                value = notes,

                onValueChange = {
                    notes = it
                },

                label = {

                    Text(
                        text = "Customer Notes",
                        color = Color.Black
                    )
                },

                textStyle = TextStyle(
                    color = Color.Black
                ),

                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(16.dp),

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

            Spacer(modifier = Modifier.height(16.dp))

            // DUE DATE FIELD
            OutlinedTextField(

                value = dueDate,

                onValueChange = {
                    dueDate = it
                },

                label = {

                    Text(
                        text = "Due Date",
                        color = Color.Black
                    )
                },

                placeholder = {

                    Text(
                        text = "Example: 25 May 2026"
                    )
                },

                textStyle = TextStyle(
                    color = Color.Black
                ),

                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(16.dp),

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

            if (isError) {

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Name & Phone required",
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // SAVE BUTTON
            Button(
                onClick = {

                    if (
                        name.isEmpty() ||
                        phone.isEmpty()
                    ) {

                        isError = true

                    } else {

                        onSave(

                            Customer(
                                name = name,
                                phone = phone,
                                balance = 0,
                                userPhone = currentUserPhone,

                                // NEW
                                notes = notes,

                                // NEW
                                dueDate = dueDate
                            )
                        )
                    }
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),

                shape = RoundedCornerShape(16.dp)
            ) {

                Text(
                    text = "Save",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}