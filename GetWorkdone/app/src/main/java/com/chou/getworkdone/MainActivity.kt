package com.chou.getworkdone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.*
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.chou.getworkdone.ui.theme.GetWorkdoneTheme

import android.app.*
import android.content.*
import android.os.*
import android.provider.Settings
import android.widget.*
import com.chou.getworkdone.R
//import androidx.appcompat.app.AppCompatActivity

class MainActivity : ComponentActivity() {

    private lateinit var notificationManager: NotificationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main as Int)

        // Initialize NotificationManager
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Button to start Quiet Hour
        val startButton = findViewById<Button>(R.id.startButton)
        startButton.setOnClickListener {
            if (!notificationManager.isNotificationPolicyAccessGranted) {
                // Ask the user to grant DND access
                val intent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
                Toast.makeText(this, "Please grant Do Not Disturb access", Toast.LENGTH_LONG).show()
                startActivity(intent)
            } else {
                // Enable DND and schedule to turn it off later
                enableDNDForOneHour()
                Toast.makeText(this, "Quiet Hour started!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun enableDNDForOneHour() {
        // Enable DND: No interruptions
        notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_NONE)

        // Restore normal interruptions after 1 hour (3600000 ms)
        Handler(Looper.getMainLooper()).postDelayed({
            notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL)
            Toast.makeText(this, "Quiet Hour ended", Toast.LENGTH_SHORT).show()
        }, 60 * 60 * 1000)
    }
}
/*
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GetWorkdoneTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GetWorkdoneTheme {
        Greeting("Android")
    }
}
*/