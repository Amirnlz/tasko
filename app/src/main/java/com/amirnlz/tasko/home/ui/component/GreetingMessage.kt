package com.amirnlz.tasko.home.ui.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.amirnlz.tasko.core.ui.theme.TaskoTheme
import com.amirnlz.tasko.utils.greetingMessageByHour
import java.util.Calendar

@Composable
fun GreetingMessage(modifier: Modifier = Modifier) {
    Text(
        greetMessageText(),
        style = MaterialTheme.typography.titleLarge,
    )

}


private fun greetMessageText(): String {
    val calendar = Calendar.getInstance()
    val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
    return currentHour.greetingMessageByHour()

}

@Preview(showBackground = true)
@Composable
private fun GreetingMessagePrev() {
    TaskoTheme {
        GreetingMessage()
    }
}



