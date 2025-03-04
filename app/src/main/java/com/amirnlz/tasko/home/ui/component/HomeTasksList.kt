package com.amirnlz.tasko.home.ui.component

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amirnlz.tasko.core.ui.components.tasks.TasksList
import com.amirnlz.tasko.home.domain.model.TodoTask
import com.amirnlz.tasko.utils.toLocalDateFromMillis
import java.time.LocalDate

@Composable
fun HomeTasksList(
    modifier: Modifier = Modifier,
    tasks: List<TodoTask>,
    onTaskCheckedChange: (TodoTask) -> Unit
) {
    val today = LocalDate.now()
    val todayTasks = tasks.filter {
        it.dueDateMillis.toLocalDateFromMillis() == today
    }
    val upcomingTasks = tasks.filter {
        it.dueDateMillis.toLocalDateFromMillis()?.isAfter(today) == true
    }

    Log.d(
        "TAG",
        "HomeTasksList() called with: modifier = $modifier, tasks = $tasks"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        // Today Tasks Section
        if (todayTasks.isNotEmpty()) {
            Text(
                text = "Today's Tasks",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            TasksList(tasks = todayTasks, onTaskCheckedChange = onTaskCheckedChange)
        }

        // Upcoming Tasks Section
        if (upcomingTasks.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Upcoming Tasks",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            TasksList(tasks = upcomingTasks, onTaskCheckedChange = onTaskCheckedChange)
        } else if (todayTasks.isNotEmpty()) { // Show message if no upcoming tasks and there are today tasks to avoid lonely "today" section
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "No Upcoming Tasks",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f) // Muted color
                ),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}
