package com.amirnlz.tasko.core.ui.components.tasks

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.amirnlz.tasko.home.domain.model.TodoTask
import com.amirnlz.tasko.utils.toLocalDateFromMillis

@Composable
fun TasksList(
    modifier: Modifier = Modifier,
    tasks: List<TodoTask>,
    onTaskCheckedChange: (TodoTask) -> Unit
) {
    val screenWidthDp = LocalConfiguration.current.screenWidthDp.dp
    val dividerWidth = screenWidthDp * (0.75f)

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(tasks) { task ->
            ListItem(
                modifier = Modifier.clip(RoundedCornerShape(8.dp)),
                headlineContent = { Text(task.title) },
                trailingContent = { Text(task.dueDateMillis.toLocalDateFromMillis().toString()) },
                leadingContent = {
                    CircleCheckbox(task.isCompleted) {
                        val toggledTask = task.copy(
                            isCompleted = it
                        )
                        onTaskCheckedChange(toggledTask)
                    }
                }
            )
            HorizontalDivider(Modifier.width(dividerWidth))
        }
    }
}

@Composable
fun CircleCheckbox(selected: Boolean, onChecked: (Boolean) -> Unit) {
    Box(
        Modifier
            .size(25.dp)
            .clip(RoundedCornerShape(15.dp))
            .border(
                border = BorderStroke(
                    1.dp,
                    color = if (selected.not()) MaterialTheme.colorScheme.surfaceVariant else Color.Transparent
                )
            )
            .background(
                color = if (selected) MaterialTheme.colorScheme.primary else Color.White,
            )
            .clickable {
                onChecked(selected.not())
            },
        contentAlignment = Alignment.Center
    ) {
        if (selected)
            Icon(Icons.Default.Check, contentDescription = "", tint = Color.White)
    }
}
