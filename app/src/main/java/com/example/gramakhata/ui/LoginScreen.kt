package com.example.gramakhata.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gramakhata.AppLanguage
import com.example.gramakhata.R
import com.example.gramakhata.Routes
import com.example.gramakhata.data.AppDatabase
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController,
    selectedLanguage: AppLanguage,
    onLanguageChange: (AppLanguage) -> Unit,
    onLoginSuccess: (String) -> Unit
) {

    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var passwordVisible by remember {
        mutableStateOf(false)
    }

    var errorMessage by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    val database = remember {
        AppDatabase.getDatabase(context)
    }

    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        // Background Image
        Image(
            painter = painterResource(id = R.drawable.app_logo),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Dark Overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.35f))
        )

        // Login Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),

            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // LANGUAGE SWITCH
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {

                TextButton(
                    onClick = {
                        onLanguageChange(AppLanguage.ENGLISH)
                    }
                ) {

                    Text(
                        text = "English",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = "|",
                    color = Color.White,
                    fontSize = 18.sp
                )

                TextButton(
                    onClick = {
                        onLanguageChange(AppLanguage.KANNADA)
                    }
                ) {

                    Text(
                        text = "ಕನ್ನಡ",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // APP NAME
            Text(
                text =
                    if (selectedLanguage == AppLanguage.KANNADA)
                        "ಗ್ರಾಮ ಖಾತಾ"
                    else
                        "Grama Khata",

                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            // STORE NAME
            Text(
                text = "Sri Ganesha Store",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(24.dp))

            // PHONE NUMBER FIELD
            OutlinedTextField(
                value = phone,
                onValueChange = {
                    phone = it
                    errorMessage = ""
                },

                label = {
                    Text(
                        text =
                            if (selectedLanguage == AppLanguage.KANNADA)
                                "ದೂರವಾಣಿ ಸಂಖ್ಯೆ"
                            else
                                "Phone Number",

                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                },

                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                ),

                shape = RoundedCornerShape(16.dp),

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

            Spacer(modifier = Modifier.height(14.dp))

            // PASSWORD FIELD
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    errorMessage = ""
                },

                label = {
                    Text(
                        text =
                            if (selectedLanguage == AppLanguage.KANNADA)
                                "ಪಾಸ್ವರ್ಡ್"
                            else
                                "Password",

                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                },

                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                ),

                shape = RoundedCornerShape(16.dp),

                modifier = Modifier.fillMaxWidth(),

                colors = OutlinedTextFieldDefaults.colors(

                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,

                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.DarkGray,

                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,

                    cursorColor = Color.Black
                ),

                visualTransformation =
                    if (passwordVisible)
                        VisualTransformation.None
                    else
                        PasswordVisualTransformation(),

                trailingIcon = {

                    val image =
                        if (passwordVisible)
                            Icons.Filled.Visibility
                        else
                            Icons.Filled.VisibilityOff

                    IconButton(
                        onClick = {
                            passwordVisible = !passwordVisible
                        }
                    ) {
                        Icon(
                            imageVector = image,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            // FORGOT PASSWORD
            TextButton(
                onClick = {
                    navController.navigate(Routes.FORGOT_PASSWORD)
                }
            ) {
                Text(
                    text =
                        if (selectedLanguage == AppLanguage.KANNADA)
                            "ಪಾಸ್ವರ್ಡ್ ಮರೆತಿರಾ?"
                        else
                            "Forgot Password",

                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // ERROR MESSAGE
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            // LOGIN BUTTON
            Button(
                onClick = {

                    coroutineScope.launch {

                        if (phone.isBlank() || password.isBlank()) {

                            errorMessage =
                                "Please enter phone number and password"

                        } else {

                            val user =
                                database.userDao().login(
                                    phone,
                                    password
                                )

                            if (user != null) {

                                onLoginSuccess(phone)

                            } else {

                                errorMessage =
                                    "Invalid phone number or password"
                            }
                        }
                    }
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),

                shape = RoundedCornerShape(16.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2E7D32)
                )
            ) {
                Text(
                    text =
                        if (selectedLanguage == AppLanguage.KANNADA)
                            "ಲಾಗಿನ್"
                        else
                            "Login",

                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // SIGNUP NAVIGATION
            TextButton(
                onClick = {
                    navController.navigate(Routes.SIGNUP)
                }
            ) {
                Text(
                    text =
                        if (selectedLanguage == AppLanguage.KANNADA)
                            "ಸೈನ್ ಅಪ್"
                        else
                            "Signup",

                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}