package com.example.todoit.presentacion.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun NavIconButton(
    imageVector: ImageVector,
    onClick: ()-> Unit,
    contentDescription: String,
   size: Dp = 30.dp,
    color: Color
){
    IconButton(onClick) {
        Icon(imageVector, contentDescription, modifier = Modifier.size(size), tint = color)
    }

}