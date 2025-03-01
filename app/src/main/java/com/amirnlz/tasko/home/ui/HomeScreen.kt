package com.amirnlz.tasko.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amirnlz.tasko.core.ui.theme.TaskoTheme
import com.amirnlz.tasko.home.ui.component.HomeCalendar
import com.amirnlz.tasko.home.ui.component.HomeTopBar

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 12.dp, horizontal = 16.dp),
        topBar = { HomeTopBar() }
    ) { paddingValues ->
        Column(Modifier.padding(paddingValues)) {
            HomeCalendar()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPrev() {
    TaskoTheme {
        HomeScreen()
    }
}

