package iutlr.butinfo3.jmalki.activitylifecycle

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import iutlr.butinfo3.jmalki.activitylifecycle.ui.theme.ActivityLifeCycleTheme

class MainActivity : ComponentActivity() {

    // List to store lifecycle event logs
    var lifecycleEvents = mutableStateListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Log the onCreate event
        logEvent("onCreate")

        setContent {
            ActivityLifeCycleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ComposeState(
                        lifecycleEvents,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    // Function to log events and update the UI
    private fun logEvent(event: String) {
        Log.d("ActivityLifecycleLogger : ", event)
        lifecycleEvents.add(event)
    }
}

@Composable
fun ComposeState(
    lifecycleEvents: SnapshotStateList<String>,
    modifier: Modifier = Modifier) {

    var counter by remember { mutableStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(36.dp)
    ) {
        // Incrementable Counter Section
        CounterSection(value = counter, onClick = { counter++ })

        // Divider
        HorizontalDivider(
            modifier = modifier.padding(vertical = 10.dp),
            thickness = 2.dp,
            color = Color.Gray
        )

        // Lifecycle Logs Section
        LifecycleLoggerScreen(lifecycleEvents)
    }
}

@Composable
fun CounterSection(
    value: Int,
    onClick: () -> Unit,
) {
    // State variable for the counter
    Log.d("ComposeState -> counter = ", value.toString())

    Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
        Text(
            text = "Counter: ${value}",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onClick ) {
            Text(text = "Increment Counter")
        }
    }
}

@Composable
fun LifecycleLoggerScreen(events: List<String>) {
    Column {
        Text(
            text = "Activity Lifecycle Events",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn {
            items(events) { event ->
                Text(
                    text = event,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

    val lifecycleEvents = listOf<String>()

    ActivityLifeCycleTheme {
        var counter by remember { mutableStateOf(0) }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Incrementable Counter Section
            CounterSection(counter, {})

            // Divider
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp),
                thickness = 2.dp,
                color = Color.Gray
            )

            // Lifecycle Logs Section (with example logs)
            LifecycleLoggerScreen(listOf("onCreate", "onStart", "onResume"))
        }
    }
}
