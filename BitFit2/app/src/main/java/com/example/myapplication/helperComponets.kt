package com.example.myapplication


import androidx.compose.material3.TextField
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun SubmitButton(title: String, onClick: () -> Unit) {
    Spacer(modifier = Modifier.padding(4.dp))
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        shape = RoundedCornerShape(4.dp),
        content = { Text(text = title) },
        onClick = { onClick() },
    )
}

@Composable
fun HeadingTextForFormData(heading: String) {
    Spacer(modifier = Modifier.padding(8.dp))
    Text(
        text = heading,
        style = MaterialTheme.typography.bodyLarge
    )
}

@Composable
fun FormTextInput(
    heading: String,
    placeholder: String,
    taskName: String,
    onTaskNameChange: (String) -> Unit
) {
    HeadingTextForFormData(heading)
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = taskName,
        onValueChange = { onTaskNameChange(it) },
        placeholder = { Text(text = placeholder) },
    )
}

@Composable
fun FormTextInputNumber(
    heading: String,
    placeholder: String,
    taskName: String,
    onTaskNameChange: (String) -> Unit
) {
    HeadingTextForFormData(heading)
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = taskName,
        onValueChange = { onTaskNameChange(it) },
        placeholder = { Text(text = placeholder) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}



@Composable
fun DropDownSelect(
    heading: String,
    options: List<String>,
    selectedOption: String,
    changeSelectedOpt: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    HeadingTextForFormData(heading)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .height(42.dp)
            .background(Color("#dde3ea".toColorInt()))
            .wrapContentSize(Alignment.TopStart)
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize()
                .clickable(onClick = { expanded = true }),
            horizontalArrangement = Arrangement.SpaceBetween, // This ensures space distribution between elements
            verticalAlignment = Alignment.CenterVertically // This centers the elements vertically in the Row
        ) {
            Text(
                text = selectedOption,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(8.dp)
            )
            Icon(
                imageVector = Icons.Filled.KeyboardArrowDown,
                contentDescription = "down arrow",
                modifier = Modifier.size(24.dp) // Ensures the Icon has a specific size, making it more likely to be visible
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth(),
            content = {
                options.forEachIndexed { index, option ->
                    DropdownMenuItem(
                        text = { Text(text = option) },
                        onClick = {
                            // selectedIndex = index
                            changeSelectedOpt(option)
                            expanded = false
                        }
                    )
                }
            }
        )
    }
}