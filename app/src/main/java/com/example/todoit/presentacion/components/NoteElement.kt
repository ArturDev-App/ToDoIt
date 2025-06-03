package com.example.todoit.presentacion.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoit.R
import com.example.todoit.presentacion.screens.home.HomeViewModel

@Composable
fun NoteElement(
    title: String,
    date: String,
    priority: String,
    status: String,
    statusColor: Color,
    viewModel: HomeViewModel = viewModel(),
    onStatusClick: () -> Unit,
    onNoteClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(color = Color.White)
            .border(3.dp, Color.Gray, RoundedCornerShape(50))
            .padding(horizontal = 15.dp, vertical = 15.dp)
            .clickable { onNoteClick() }
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(text = title, fontSize = 16.sp)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                Text(text = date, fontSize = 15.sp)
                Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                    Icon(
                        painter = painterResource(R.drawable.flag_solid),
                        contentDescription = null,
                        tint= viewModel.getPriorityColor(priority),
                        modifier = Modifier.size(15.dp)
                    )

                    Text(text = priority, fontSize = 15.sp, color = viewModel.getPriorityColor(priority))
                }
            }
        }

        ChipSelectElement(
            status = status,
            color = statusColor,
            onClick = {
                onStatusClick()
            }
        )
    }
}