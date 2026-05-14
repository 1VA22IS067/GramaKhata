package com.example.gramakhata.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gramakhata.AppLanguage
import com.example.gramakhata.Customer
import java.text.SimpleDateFormat

@Composable
fun HomeScreen(
    selectedLanguage: AppLanguage,
    onLanguageChange: (AppLanguage) -> Unit,
    customers: List<Customer>,
    onAddClick: () -> Unit,
    onCustomerClick: (Customer) -> Unit,
    onReportClick: () -> Unit,
    onBackClick: () -> Unit
) {

    // SEARCH TEXT
    var searchText by remember {
        mutableStateOf("")
    }

    // TOTAL OUTSTANDING
    val totalOutstanding =
        customers.sumOf { it.balance }

    // TOTAL CUSTOMERS
    val totalCustomers =
        customers.size

    // HIGH DUE CUSTOMERS
    val highDueCustomers =
        customers.count {
            it.balance > 1000
        }

    // FILTERED CUSTOMERS
    val filteredCustomers =
        customers.filter {

            it.name.contains(
                searchText,
                ignoreCase = true
            )

        }.sortedByDescending {
            it.balance
        }

    // CURRENT DATE
    val currentDate =
        SimpleDateFormat(
            "dd MMM yyyy"
        ).format(
            java.util.Date()
        )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // TOP BAR
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = {
                        onBackClick()
                    }
                ) {

                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text =
                        if (selectedLanguage == AppLanguage.KANNADA)
                            "ಗ್ರಾಮ ಖಾತಾ"
                        else
                            "Grama Khata",

                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // GREETING
        Text(
            text =
                if (selectedLanguage == AppLanguage.KANNADA)
                    "ನಮಸ್ಕಾರ ನಿವೆದಿತಾ 👋"
                else
                    "Hello Niveditha 👋",

            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(4.dp))

        // DATE
        Text(
            text = currentDate,
            color = Color.Gray,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(18.dp))

        // TOTAL OUTSTANDING CARD
        Card(

            modifier = Modifier.fillMaxWidth(),

            shape = RoundedCornerShape(18.dp),

            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFE8F5E9)
            )
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text =
                        if (selectedLanguage == AppLanguage.KANNADA)
                            "ಒಟ್ಟು ಬಾಕಿ"
                        else
                            "Total Outstanding",

                    fontSize = 18.sp,
                    color = Color.DarkGray
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "₹$totalOutstanding",

                    fontSize = 32.sp,

                    fontWeight = FontWeight.Bold,

                    color = Color(0xFF1B5E20)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // DASHBOARD STATS
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            // TOTAL CUSTOMERS CARD
            Card(

                modifier = Modifier.weight(1f),

                shape = RoundedCornerShape(16.dp),

                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFE3F2FD)
                )
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text =
                            if (selectedLanguage == AppLanguage.KANNADA)
                                "ಗ್ರಾಹಕರು"
                            else
                                "Customers",

                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = totalCustomers.toString(),

                        fontSize = 24.sp,

                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // HIGH DUE CARD
            Card(

                modifier = Modifier.weight(1f),

                shape = RoundedCornerShape(16.dp),

                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFEBEE)
                )
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text =
                            if (selectedLanguage == AppLanguage.KANNADA)
                                "ಹೆಚ್ಚು ಬಾಕಿ"
                            else
                                "High Due",

                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = highDueCustomers.toString(),

                        fontSize = 24.sp,

                        fontWeight = FontWeight.Bold,

                        color = Color.Red
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // BUTTONS
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Button(
                onClick = onAddClick
            ) {

                Text(
                    if (selectedLanguage == AppLanguage.KANNADA)
                        "ಗ್ರಾಹಕರನ್ನು ಸೇರಿಸಿ"
                    else
                        "Add Customer"
                )
            }

            Button(
                onClick = onReportClick
            ) {

                Text(
                    if (selectedLanguage == AppLanguage.KANNADA)
                        "ವರದಿ"
                    else
                        "Report"
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // SEARCH BAR
        OutlinedTextField(

            value = searchText,

            onValueChange = {
                searchText = it
            },

            label = {

                Text(
                    text =
                        if (selectedLanguage == AppLanguage.KANNADA)
                            "ಗ್ರಾಹಕರನ್ನು ಹುಡುಕಿ"
                        else
                            "Search Customer",

                    color = Color.Black
                )
            },

            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            ),

            modifier = Modifier.fillMaxWidth(),

            shape = RoundedCornerShape(14.dp),

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

        // CUSTOMER LIST
        if (filteredCustomers.isEmpty()) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text =
                        if (selectedLanguage == AppLanguage.KANNADA)
                            "ಯಾವುದೇ ಗ್ರಾಹಕರಿಲ್ಲ"
                        else
                            "No customers found.",

                    color = Color.Gray,
                    fontSize = 18.sp
                )
            }

        } else {

            LazyColumn(

                modifier = Modifier.fillMaxSize()

            ) {

                items(
                    filteredCustomers
                ) { customer ->

                    // CUSTOMER STATUS
                    val status =
                        when {

                            customer.balance == 0 ->
                                "🟢 Cleared"

                            customer.balance > 1000 ->
                                "🔴 High Due"

                            else ->
                                "🟠 Pending"
                        }

                    val statusColor =
                        when {

                            customer.balance == 0 ->
                                Color(0xFF2E7D32)

                            customer.balance > 1000 ->
                                Color.Red

                            else ->
                                Color(0xFFF57C00)
                        }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .clickable {
                                onCustomerClick(customer)
                            },

                        shape = RoundedCornerShape(16.dp)
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),

                            verticalAlignment = Alignment.CenterVertically,

                            horizontalArrangement =
                                Arrangement.SpaceBetween
                        ) {

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                // CUSTOMER AVATAR
                                Box(
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFBBDEFB)),

                                    contentAlignment = Alignment.Center
                                ) {

                                    Text(
                                        text =
                                            customer.name
                                                .first()
                                                .uppercase(),

                                        fontWeight = FontWeight.Bold,

                                        fontSize = 22.sp
                                    )
                                }

                                Spacer(
                                    modifier = Modifier.width(14.dp)
                                )

                                Column {

                                    Text(
                                        customer.name,

                                        fontWeight = FontWeight.Bold,

                                        fontSize = 18.sp
                                    )

                                    Spacer(
                                        modifier = Modifier.height(4.dp)
                                    )

                                    Text(
                                        customer.phone,

                                        fontSize = 13.sp,

                                        color = Color.Gray
                                    )

                                    Spacer(
                                        modifier = Modifier.height(6.dp)
                                    )

                                    Text(
                                        text = status,

                                        color = statusColor,

                                        fontWeight = FontWeight.Bold,

                                        fontSize = 14.sp
                                    )
                                }
                            }

                            Text(
                                text = "₹${customer.balance}",

                                fontSize = 20.sp,

                                fontWeight = FontWeight.Bold,

                                color =
                                    if (customer.balance > 1000)
                                        Color.Red
                                    else
                                        Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}