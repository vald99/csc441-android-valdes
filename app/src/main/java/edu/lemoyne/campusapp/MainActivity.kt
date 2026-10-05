package edu.lemoyne.campusapp

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberOverscrollEffect
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.lemoyne.campusapp.ui.theme.CampusAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CampusAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// --- Class 7: Step 1: a counter that remembers ---
@Composable
fun CounterDemo() {
    var count by remember { mutableStateOf(0) }

    Button(
        onClick = { count++ }
    ) {
        Text(text = "Tapped $count times")

    }
}


// --- Class 6: Step 1: my own screen ---
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    // --- Class 7: Step 2: the list lives in state
    val welcomeMessages = remember {
        mutableStateListOf(
            "Try out one of our various testing suites!",
            "These aid in the testing of optimal performance.",
            "You may find that some features function differently than expected."
        )
    }

    // --- CLass 7: Step 3: what's typed lives in state ---
    var newMessage by remember { mutableStateOf("") }
    // --- Class 8: Step 2: the error message lives in state too ---
    var error by remember { mutableStateOf<String?>(null)}

    // --- Class 6: Step 3: a column, so things stack ---
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
//        CounterDemo()
        // --- Lab 6: Task 3: a picture of my own ---
        Image(
            painter = painterResource(id = R.drawable.smiley),
            contentDescription = "A hand-drawn smiley face.",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- Class 6: Step 4: real styling ---
        Text(
            text = "Lab App",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Welcome to the testing lab!",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        // --- Class 7: Step 3: the text field ---
        OutlinedTextField(
            value = newMessage,
            // --- Class 8: Step 3: the field itself pushes back ---
            onValueChange = {
                newMessage = it.take(n = MAX_MESSAGE_LENGTH)
                error = null
            },
            label = { Text("Message") },
            singleLine = true,
            isError = error != null,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // --- Lab 7: Task 4: a live character counter ---
        Text(
            text = "${newMessage.length} / 40",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(8.dp))

        error?.let { errorMessage ->
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                fontSize = 14.sp
            )
        }

        // --- Class 7: Step 4: the button changes the state ---
        Button(onClick =  {
            // --- Class 8: Step 3: check before you add ---
            val problem = validateMessage(input = newMessage, existingMessages = welcomeMessages)
            if (problem == null) {
                welcomeMessages.add(newMessage)
                newMessage = ""
            } else {
                error = problem
            }
        },
            // --- Class 8: Step 4: the sign on the door, not the lock ---
            enabled = newMessage.isNotBlank()
            ) {
            Text("Add message")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // --- Lab 7: Task 1: remove the last item ---
        Button(onClick = {
            if (welcomeMessages.isNotEmpty()) {
                welcomeMessages.removeAt(welcomeMessages.lastIndex)
            }
        }) {
            Text("Remove last")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            welcomeMessages.clear()
        }) {
            Text("Clear")
        }

        // Lab 6: Task 1: Make the screen properly yours.
        Text(text = "Here's all the messages: ")

        Text(
            // Lab 7: Task 2: singular and plural ---
            text = if (welcomeMessages.size == 1) {"1 message: "} else "${welcomeMessages.size} messages",
            fontWeight = FontWeight.Bold
        )

        for (message in welcomeMessages) {
            Text(text = message, fontSize = 18.sp)
        }

        // Lab 6: Task 2: footer ---
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Last updated September 2026",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

const val MAX_MESSAGE_LENGTH = 30

// --- Class 8: Step 1: one rulebook for message names ---
fun validateMessage(input: String, existingMessages: List<String>): String? {
    val message = input.trim()
    return when {
        message.isEmpty() -> "Enter a message"
        // --- Lab 8: Task 1: minimum length ---
        message.length < 3 -> "Too short — at least 3 characters"
        message.length > MAX_MESSAGE_LENGTH -> "Keep it to $MAX_MESSAGE_LENGTH characters or less"
        existingMessages.any { it.equals( message, ignoreCase = true) } -> "$message is already on the list"
        // --- Lab 8: Task 2: my own rule ---
        !message.first().isLetter() -> "Message must start with a letter!"
        else -> null
    }
}

// --- Class 6: Step 2: Preview ---
@Preview
@Composable
fun HomeScreenPreview() {
    CampusAppTheme {
        HomeScreen()
    }
}

// --- Lab 6: Task 4: dark mode preview ---
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenDarkPreview() {
    CampusAppTheme {
        Surface {
            HomeScreen()
        }
    }
}