package com.amirnlz.tasko.core.ui.components.tasks

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amirnlz.tasko.home.domain.model.TodoTask
import com.amirnlz.tasko.utils.toLocalDateFromMillis
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun TasksList(
    modifier: Modifier = Modifier,
    tasks: List<TodoTask>,
    onTaskCheckedChange: (TodoTask) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(tasks) { task ->
            TaskItem(task = task, onTaskCheckedChange = onTaskCheckedChange)
        }
    }
}

@Composable
fun TaskItem(
    task: TodoTask,
    onTaskCheckedChange: (TodoTask) -> Unit
) {
    val dividerWidth = LocalConfiguration.current.screenWidthDp.dp * (0.9f)
    val dateFormatter =
        DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.getDefault())

    ListItem(
        modifier = Modifier
            .fillMaxWidth(),
        headlineContent = {
            Text(
                text = task.title,
                style = TextStyle(
                    fontSize = 18.sp,
                    textDecoration = if (task.isCompleted) TextDecoration.LineThrough
                    else TextDecoration.None
                )
            )
        },
        trailingContent = {
            Text(
                text = task.dueDateMillis.toLocalDateFromMillis()?.format(dateFormatter)
                    ?: "No Date",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Italic,
                    color = if (task.dueDateMillis.toLocalDateFromMillis()
                            ?.isBefore(LocalDate.now()) == true && !task.isCompleted
                    ) {
                        MaterialTheme.colorScheme.error
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    }
                )
            )
        },
        leadingContent = {
            CircleCheckbox(
                selected = task.isCompleted,
                onChecked = { isChecked ->
                    val toggledTask = task.copy(isCompleted = isChecked)
                    onTaskCheckedChange(toggledTask)
                }
            )
        }
    )
    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .width(dividerWidth)
            .padding(horizontal = 16.dp),
        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
    )
}


@Composable
fun CircleCheckbox(selected: Boolean, onChecked: (Boolean) -> Unit) {
    Box(
        Modifier
            .size(24.dp)
            .clip(CircleShape)
            .border(
                border = BorderStroke(
                    2.dp,
                    color = if (!selected) MaterialTheme.colorScheme.outline
                    else MaterialTheme.colorScheme.primary
                ),
                shape = CircleShape
            )
            .background(
                color = if (selected) MaterialTheme.colorScheme.primary
                else Color.Transparent,
            )
            .clickable {
                onChecked(selected.not())
            },
        contentAlignment = Alignment.Center
    ) {
        if (selected)
            Icon(
                Icons.Default.Check,
                contentDescription = "Checked",
                tint = MaterialTheme.colorScheme.onPrimary
            )
    }
}
