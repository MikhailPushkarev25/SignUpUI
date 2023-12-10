package com.example.signupui

import android.graphics.drawable.AnimatedImageDrawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.signupui.ui.theme.SignUpUITheme

private val String.isValidName: Boolean
    get() {
        return this.length > 4
    }

private val String.isValidEmail: Boolean
    get() {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

private val String.isValidPassword: Boolean
    get() {
        return this.length >= 8
    }
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SignUpUITheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xff101010)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(24.dp)
                            .fillMaxSize()
                    ) {
                        Row {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .background(
                                        color = Color(0xff171717)
                                    )
                                    .border(
                                        border = BorderStroke(
                                            width = 2.dp,
                                            color = Color(0xff3E3E3E)
                                        ),
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .clickable {
                                        finish()
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowLeft,
                                    contentDescription = "Back",
                                    tint = Color.White,
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Sign up",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 28.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(15.dp))
                        Text(
                            text = "Create an account for free to get started!",
                            color = Color.White,
                            fontSize = 15.sp
                        )
                        /////////////////////////////////////////
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = "Name",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(end = 9.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        NameField()
                        /////////////////////////////////////////
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = "Email",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(end = 9.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        EmailField()
                        /////////////////////////////////////////
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = "Password",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(end = 9.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        PasswordField()
                        //////////////////////////////////////////
                        Spacer(modifier = Modifier.height(10.dp))

                        val buttonPressed = remember { mutableStateOf(false) }
                        val buttonLeftColor = animateColorAsState(
                            targetValue = if (buttonPressed.value)
                                Color(0xff480F63)
                            else
                                Color(0xff3C28C6),
                            label = "",
                            animationSpec = tween(
                                durationMillis = 100,
                                easing = LinearEasing
                            )
                        )
                        val buttonRightColor = animateColorAsState(
                            targetValue = if (buttonPressed.value)
                                Color(0xff3C28C6)
                            else
                                Color(0xff480F63),
                            label = "",
                            animationSpec = tween(
                                durationMillis = 100,
                                easing = LinearEasing
                            )
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(64.dp)
                                .clip(shape = RoundedCornerShape(12.dp))
                                .background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(
                                            buttonLeftColor.value,
                                            buttonRightColor.value
                                        )
                                    )
                                )
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onPress = {
                                            buttonPressed.value = true
                                        },
                                        onTap = {
                                            buttonPressed.value = false
                                        }
                                    )
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "SIGN UP",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Divider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                color = Color.Gray,
                                thickness = 2.dp
                            )
                            Text(
                                text = "OR",
                                modifier = Modifier.padding(16.dp),
                                color = Color.Gray
                            )
                            Divider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                color = Color.Gray,
                                thickness = 2.dp
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(30.dp)
                                    .weight(1f)
                                    .clip(shape = RoundedCornerShape(12.dp))
                                    .clickable { }
                                    .background(color = Color(0xff171717))
                                    .border(
                                        width = 2.dp,
                                        color = Color(0xff252525),
                                        shape = RoundedCornerShape(12.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.google),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(20.dp),
                                    tint = Color.White
                                )
                            }
                            Spacer(modifier = Modifier.width(40.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(30.dp)
                                    .weight(1f)
                                    .clip(shape = RoundedCornerShape(12.dp))
                                    .clickable { }
                                    .background(color = Color(0xff171717))
                                    .border(
                                        width = 2.dp,
                                        color = Color(0xff252525),
                                        shape = RoundedCornerShape(12.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.twitter),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(20.dp),
                                    tint = Color.White
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                        Row(
                            modifier = Modifier
                                .align(alignment = Alignment.CenterHorizontally),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Already have an account?",
                                color = Color.Gray,
                            )
                            TextButton(onClick = { /*TODO*/ },
                                colors = ButtonDefaults.textButtonColors(
                                    contentColor = Color.White
                                )
                                ) {
                                Text(
                                    text = "Login in",
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun NameField() {
        val name = remember { mutableStateOf("") }
        val nameFocused = remember { mutableStateOf(false) }
        val nameFieldUpperBorderColor = animateColorAsState(
            targetValue = if (nameFocused.value)
                Color(0xff5133FF)
            else
                Color(0xff282828),
            label = "",
            animationSpec = tween(
                durationMillis = 300,
                easing = LinearEasing
            )
        )
        val nameFieldButtonBorderColor = animateColorAsState(
            targetValue = if (nameFocused.value)
                Color(0xff100F6C)
            else
                Color(0xff282828),
            label = "",
            animationSpec = tween(
                durationMillis = 300,
                easing = LinearEasing
            )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .clip(shape = RoundedCornerShape(12.dp))
                .background(color = Color(0xff171717))
                .border(
                    border = BorderStroke(
                        width = 2.dp,
                        brush = Brush.verticalGradient(
                            listOf(
                                nameFieldUpperBorderColor.value,
                                nameFieldButtonBorderColor.value
                            )
                        )
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 18.dp)
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    if (name.value.isEmpty()) {
                        Text(
                            text = "Name",
                            color = Color.Gray
                        )
                    }
                    BasicTextField(
                        value = name.value,
                        onValueChange = {
                            name.value = it
                        },
                        textStyle = TextStyle(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        ),
                        cursorBrush = SolidColor(Color.White),
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged {
                                nameFocused.value = it.isFocused
                            }
                    )
                }
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(shape = RoundedCornerShape(6.dp))
                        .background(
                            if (name.value.isValidName) Color(0xff282828) else Color.Transparent
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    AnimatorVectorExample(name.value.isValidName)
                }
            }
        }
    }

    @Composable
    private fun EmailField()    {
        val email = remember { mutableStateOf("") }
        val emailFocused = remember { mutableStateOf(false) }
        val emailFieldUpperBorderColor = animateColorAsState(
            targetValue = if (emailFocused.value)
                Color(0xff5133FF)
            else
                Color(0xff282828),
            label = "",
            animationSpec = tween(
                durationMillis = 300,
                easing = LinearEasing
            )
        )
        val emailFieldButtonBorderColor = animateColorAsState(
            targetValue = if (emailFocused.value)
                Color(0xff100F6C)
            else
                Color(0xff282828),
            label = "",
            animationSpec = tween(
                durationMillis = 300,
                easing = LinearEasing
            )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .clip(shape = RoundedCornerShape(12.dp))
                .background(color = Color(0xff171717))
                .border(
                    border = BorderStroke(
                        width = 2.dp,
                        brush = Brush.verticalGradient(
                            listOf(
                                emailFieldUpperBorderColor.value,
                                emailFieldButtonBorderColor.value
                            )
                        )
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 18.dp)
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    if (email.value.isEmpty()) {
                        Text(
                            text = "Type your email address",
                            color = Color.Gray
                        )
                    }
                    BasicTextField(
                        value = email.value,
                        onValueChange = {
                            email.value = it
                        },
                        textStyle = TextStyle(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        ),
                        cursorBrush = SolidColor(Color.White),
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged {
                                emailFocused.value = it.isFocused
                            },

                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        )
                    )
                }
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(shape = RoundedCornerShape(6.dp))
                        .background(
                            if (email.value.isValidEmail) Color(0xff282828) else Color.Transparent
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    AnimatorVectorExample(email.value.isValidEmail)
                }
            }
        }
    }

    @Composable
    private fun PasswordField() {
        val password = remember { mutableStateOf("") }
        val passwordFocused = remember { mutableStateOf(false) }
        val passwordFieldUpperBorderColor = animateColorAsState(
            targetValue = if (passwordFocused.value)
                Color(0xff5133FF)
            else
                Color(0xff282828),
            label = "",
            animationSpec = tween(
                durationMillis = 300,
                easing = LinearEasing
            )
        )
        val passwordFieldButtonBorderColor = animateColorAsState(
            targetValue = if (passwordFocused.value)
                Color(0xff100F6C)
            else
                Color(0xff282828),
            label = "",
            animationSpec = tween(
                durationMillis = 300,
                easing = LinearEasing
            )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .clip(shape = RoundedCornerShape(12.dp))
                .background(color = Color(0xff171717))
                .border(
                    border = BorderStroke(
                        width = 2.dp,
                        brush = Brush.verticalGradient(
                            listOf(
                                passwordFieldUpperBorderColor.value,
                                passwordFieldButtonBorderColor.value
                            )
                        )
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 18.dp)
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    if (password.value.isEmpty()) {
                        Text(
                            text = "Type your password",
                            color = Color.Gray
                        )
                    }
                    BasicTextField(
                        value = password.value,
                        onValueChange = {
                            password.value = it
                        },
                        textStyle = TextStyle(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        ),
                        cursorBrush = SolidColor(Color.White),
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged {
                                passwordFocused.value = it.isFocused
                            },
                        visualTransformation = PasswordVisualTransformation()
                    )
                }
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(shape = RoundedCornerShape(6.dp))
                        .background(
                            if (password.value.isValidPassword) Color(0xff282828) else Color.Transparent
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    AnimatorVectorExample(password.value.isValidPassword)
                }
            }
        }
    }

    @OptIn(ExperimentalAnimationGraphicsApi::class)
    @Composable
    private fun AnimatorVectorExample(on: Boolean) {
        val painter = rememberAnimatedVectorPainter(
            animatedImageVector = AnimatedImageVector.animatedVectorResource(id = R.drawable.avd_anim_2),
            atEnd = on
        )
        Image(
            painter = painter,
            contentDescription = "",
            modifier = Modifier
                .size(18.dp)
        )
    }
}
