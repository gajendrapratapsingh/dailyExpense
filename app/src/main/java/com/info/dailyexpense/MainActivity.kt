package com.info.dailyexpense

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.runtime.Composable
import com.info.dailyexpense.ui.theme.DailyExpenseTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Icon


import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.platform.LocalWindowInfo

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DailyExpenseTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AuthScreen()
                }
            }
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalComposeUiApi
@Composable
fun AuthScreen() {
    var isLogin by remember { mutableStateOf(true) }

    //val viewModel: AuthViewModel = viewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .clip(MaterialTheme.shapes.medium)
        )

        Spacer(modifier = Modifier.height(16.dp))

        //AuthForm(isLogin, onAuthTypeChange = { isLogin = it })

        Spacer(modifier = Modifier.height(16.dp))

        AuthButton(
            isLogin = isLogin,
            onAuthButtonClick = { email, password, name, mobile ->
                if (isLogin) {
                    // Login logic
                    //viewModel.loginUser(email, password)
                } else {
                    // Registration logic
                    //viewModel.registerUser(name, mobile, email, password)
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

    }
}

@ExperimentalMaterial3Api
@Composable
fun AuthForm(
    isLogin: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        if (!isLogin) {
            AuthTextField(
                label = "Name",
                onValueChange = {},
                icon = Icons.Outlined.Person
            )
            Spacer(modifier = Modifier.height(8.dp))
            AuthTextField(
                label = "Mobile",
                onValueChange = {},
                icon = Icons.Outlined.Person
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        AuthTextField(
            label = "Email",
            onValueChange = {},
            icon = Icons.Outlined.Email
        )
        Spacer(modifier = Modifier.height(8.dp))
        AuthTextField(
            label = "Password",
            onValueChange = {},
            visualTransformation = PasswordVisualTransformation(),
            icon = Icons.Outlined.Lock
        )
        Spacer(modifier = Modifier.height(8.dp))

        if (isLogin) {
            Text(
                text = "Forgot password?",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { /* Handle Forgot Password */ }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@ExperimentalMaterial3Api
@Composable
fun AuthTextField(
    label: String,
    onValueChange: (String) -> Unit,
    icon: ImageVector? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            onValueChange(it)
        },
        label = { Text(text = label) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Next
        ),
        leadingIcon = {
            if (icon != null) {
                Icon(imageVector = icon, contentDescription = null)
            }
        },
        visualTransformation = visualTransformation
    )
}

@ExperimentalComposeUiApi
@Composable
fun AuthButton(
    isLogin: Boolean,
    onAuthButtonClick: (String, String, String, String) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }

    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = {
                onAuthButtonClick(email, password, name, mobile)
                keyboardController?.hide()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(text = if (isLogin) "Login" else "Register")
        }

    }
}

@Composable
fun AuthToggleButton(
    isLogin: Boolean,
    onToggle: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = if (isLogin) "Register" else "Login",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable { onToggle() }
        )
        Icon(
            imageVector = Icons.Outlined.ArrowBack,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clickable { onToggle() }
                .size(24.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DailyExpenseTheme {
        AuthScreen()
    }
}