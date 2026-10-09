package edu.lemoyne.campusapp

import android.content.res.Configuration
import android.widget.Space
import androidx.activity.compose.BackHandler
import androidx.arch.core.internal.SafeIterableMap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.lemoyne.campusapp.ui.theme.CampusAppTheme

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

// --- Class 9: Step 2: one owner for the data ---
@Composable
fun CampusAppScreen(modifier: Modifier = Modifier) {
    // --- Class 7: Step 2: the list lives in state
    val welcomeMessages = remember {
        mutableStateListOf(
            "Try out one of our various testing suites!",
            "These aid in the testing of optimal performance.",
            "You may find that some features function differently than expected.",
            "Consider this the fourth test message!",
            "And now, the final message for Checkpoint 1!"
        )
    }
//    val welcomeMessages = remember {
//        (1..60).map { "Test message $it"}.toMutableStateList()
//    }

    // --- Class 9: Step 4: which screen is showing its just state ---
    var currentScreen by rememberSaveable { mutableStateOf("home")}

    when (currentScreen) {
        "home" -> HomeScreen(
            welcomeMessages = welcomeMessages,
            onAddMessage = {welcomeMessages.add(it)},
            onSeeAll = { currentScreen = "list" },
            // --- Lab 9: Task 2: pass onAbout case ---
            onAbout = { currentScreen = "about" }
        )
        "list" -> ListScreen(
            welcomeMessages = welcomeMessages,
            onBack = { currentScreen = "home" },
            // --- Class 10: Step 4: only the owner changes the list ---
            onRemove = { welcomeMessages.remove(it) },
            modifier = modifier
        )
        // --- Lab 9: Task 2: add about case ---
        "about" -> AboutScreen(
            onBack = { currentScreen = "home" },
            modifier = modifier
        )
    }
}

// --- Class 6: Step 1: my own screen ---
@Composable
fun HomeScreen(
    welcomeMessages: MutableList<String>,
    onAddMessage: (String) -> Unit,
    onSeeAll: () -> Unit,
    // --- Lab 9: Task 2: give home screen new parameter ---
    onAbout: () -> Unit,
    modifier: Modifier = Modifier
) {
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
                // --- Class 9: Step 2: ask the owner to add it ---
                onAddMessage(newMessage.trim())
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

        // --- Class 9: Step 5: a way to the second screen ---
        Button(onClick = onSeeAll) {
            Text(text = "See all messages")
        }

        Spacer (modifier = Modifier.height(8.dp))

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

        Spacer (modifier = Modifier.height(8.dp))



        // --- Lab 9: Task 2: text button that calls onAbout ---
        TextButton(onClick = onAbout) {
            Text("About")
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

// --- Class 9: Step 3: the second screen
@Composable
fun ListScreen(
    welcomeMessages: List<String>,
    onBack: () -> Unit,
    onRemove: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    // --- Class 9: Step 6: the phone's back button goes home too ---
    BackHandler { onBack() }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        TextButton(onClick = onBack) {
            Text(text = "back")

        }

        Text(
            text = "All messages",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- Lab 9: Task 1: count on the list screen
        Text(
            text = if (welcomeMessages.size == 1) {"1 message: "} else "${welcomeMessages.size} messages",
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

//        for (message in welcomeMessages) {
//            Text(text = message, fontSize = 18.sp)
//        }

        if (welcomeMessages.isEmpty()) {
            Text(
                text = "No messages yet. Add one on the home screen.",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else {
            // --- Class 10: Step 2: a list that scrolls ---
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(welcomeMessages) { message ->
                    MessageRow(
                        name = message,
                        onRemove = { onRemove(message) }
                    )
                }
            }
        }
    }
}

// --- Class 10: Step 3: one row, as its own Composable ---
@Composable
fun MessageRow(
    name: String,
    onRemove: () -> Unit
) {
    TextButton(onClick = onRemove) {
        Text("Remove")
    }

    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = name,
                fontSize = 18.sp,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

// --- Lab 9: Task 2: a third screen ---
@Composable
fun AboutScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        TextButton(onClick = onBack) {
            Text("Back")
        }

        Text(
            text = "About",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Lab App features a cool variety of tools. Only messages are implemented thus far.")
        Text(text = "Built by Yandel Valdes for CSC441")
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
        HomeScreen(
            welcomeMessages = remember {
                mutableStateListOf(
                    "Try out one of our various testing suites!",
                    "These aid in the testing of optimal performance.",
                    "You may find that some features function differently than expected.")
            },
            onAddMessage = {},
            onSeeAll = {},
            // Lab 9: Task 2: adding onAbout to home screen preview
            onAbout = {}
        )
    }
}

// Class 9: Step 7: preview the list screen ---
@Preview(showBackground = true)
@Composable
fun ListScreenPreview() {
    CampusAppTheme {
        ListScreen(
            welcomeMessages = remember {
                mutableStateListOf(
                    "Try out one of our various testing suites!",
                    "These aid in the testing of optimal performance.",
                    "You may find that some features function differently than expected.")
            },
            onBack = {},
            onRemove = {}
        )
    }
}

// --- Class 10: Step 5: preview the empty case too ---
@Preview(showBackground = true)
@Composable
fun ListScreenEmptyPreview() {
    CampusAppTheme {
        ListScreen(
            welcomeMessages = emptyList(),
            onBack = {},
            onRemove = {}
        )
    }
}

// --- Lab 6: Task 4: dark mode preview ---
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenDarkPreview() {
    CampusAppTheme {
        Surface {
            HomeScreen(welcomeMessages = remember {
                mutableStateListOf(
                    "Try out one of our various testing suites!",
                    "These aid in the testing of optimal performance.",
                    "You may find that some features function differently than expected.")
            },
                onAddMessage = {},
                onSeeAll = {},
                // Lab 9: Task 2: adding onAbout to home screen preview
                onAbout = {}
            )
        }
    }
}