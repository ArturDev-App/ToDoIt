package com.example.todoit.presentacion.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ChipSelectElement(status: String, color: Color, onClick: () -> Unit){
    //status cambiara dinamicamente dependiendo la seleccion del DropDownMenuNote()
    //color cambiara dinamicamente dependiendo la seleccion del DropDownMenuNote()
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(color)
            .border(2.dp, Color.Gray, RoundedCornerShape(50))
            .padding(horizontal = 1.dp, vertical = 1.dp)
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = status, modifier = Modifier.padding(start = 5.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Icon(Icons.Default.ArrowDropDown, contentDescription = "dropDown")
    }
}