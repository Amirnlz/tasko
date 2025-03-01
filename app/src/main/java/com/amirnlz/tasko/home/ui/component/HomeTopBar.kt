package com.amirnlz.tasko.home.ui.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.amirnlz.tasko.utils.greetingMessageByHour
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(modifier: Modifier = Modifier) {
    TopAppBar(
        modifier = modifier,
        title = { GreetingMessage() },

    )
}

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