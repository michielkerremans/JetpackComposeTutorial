package be.vives.michielkerremans.composeapp

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.vives.michielkerremans.composeapp.ui.theme.ComposeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Box(Modifier.safeDrawingPadding()) {
                // Text("Hello Compose App!")
                // MessageCard(name = "Android")
                // MessageCard(Message("Android", "Hello from Compose!"))

                ComposeAppTheme {
                    Surface(modifier = Modifier.fillMaxSize()){
                        MessageCard(Message("Android", "Hello from Compose!"))
                        Conversation(SampleData.conversationSample)
                    }
                }
            }

//            ComposeAppTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
//            }
        }
    }
}

// List & Animations
@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn {
        items(messages) { message ->
            MessageCard(msg = message)
        }
    }
}
@Preview
@Composable
fun PreviewConversation() {
    ComposeAppTheme {
        Conversation(SampleData.conversationSample)
    }
}

// Preview for the MessageCard Composable
@Preview(name = "Light Mode")
@Preview(
    name = "Dark Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PreviewMessageCard() {
    ComposeAppTheme {
        Surface {
            MessageCard(msg = Message("User", "Take a look at Jetpack Compose!"))
        }
    }
}

// Message data class and MessageCard Composable
data class Message(val author: String, val body: String)

@Composable
fun MessageCard(msg: Message){
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(id = R.drawable.profile_picture),
            contentDescription = "Contact profile picture",
            modifier = Modifier
                .size(40.dp) // Size of the image
                .clip(CircleShape) // Clip to a circle shape
                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape) // Border around the image
        )

        // Horizontal space between image and text
        Spacer(modifier = Modifier.width(8.dp))

        // Keep track if the message is expanded or not
        var isExpanded by remember { mutableStateOf(false) }
        // Animate the background color based on isExpanded state
        val surfaceColor by animateColorAsState(
            if (isExpanded) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.surface,
        )

        // Toggle isExpanded when the message is clicked
        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Text(
                text = msg.author,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall
            )

            // Vertical space between author and body
            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 1.dp,
                // surfaceColor will change gradually from primary to surface color
                color = surfaceColor,
                // animateContentSize will change the Surface size gradually
                modifier = Modifier.animateContentSize().padding(1.dp),
            ) {
                Text(
                    text = "> ${msg.body}",
                    modifier = Modifier.padding(all = 4.dp),
                    // Show all lines if expanded, otherwise just one
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

//@Composable
//fun MessageCard(name: String) {
//    Text(text = "Hello $name!")
//}
//
//@Preview
//@Composable
//fun PreviewMessageCard() {
//    MessageCard(name = "Android")
//}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    ComposeAppTheme {
//        Greeting("Android")
//    }
//}