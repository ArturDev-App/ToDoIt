package com.example.todoit.presentacion.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.todoit.presentacion.components.NavIconButton

@Composable
fun BottomNavMenu() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 50.dp, end = 50.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        NavIconButton(Icons.Default.Home,
            onClick = {},
            contentDescription = "Home",
            size = 30.dp,
            Color.White
        )
        NavIconButton(Icons.Default.Search,
            onClick = {},
            contentDescription = "Search",
            size = 30.dp,
            Color.White
        )
        NavIconButton(Icons.Default.AccountBox,
            onClick = {},
            contentDescription = "AccountBox",
            size = 30.dp,
            Color.White
        )
    }
}