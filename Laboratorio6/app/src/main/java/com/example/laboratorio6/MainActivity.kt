package com.example.laboratorio6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.laboratorio6.ui.theme.Laboratorio6Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Laboratorio6Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CounterScreen()
                }
            }
        }
    }
}

@Composable
fun CounterScreen() {
    var count by remember { mutableIntStateOf(0) }
    var totalIncrements by remember { mutableIntStateOf(0) }
    var totalDecrements by remember { mutableIntStateOf(0) }
    var maxValue by remember { mutableIntStateOf(0) }
    var minValue by remember { mutableIntStateOf(0) }
    var totalChanges by remember { mutableIntStateOf(0) }

    val history = remember { mutableStateListOf<Pair<Int, Boolean>>() }

    fun updateStats(newValue: Int, isIncrement: Boolean) {
        count = newValue
        if (isIncrement) totalIncrements++ else totalDecrements++
        totalChanges++

        if (totalChanges == 1) {
            maxValue = newValue
            minValue = newValue
        } else {
            if (newValue > maxValue) maxValue = newValue
            if (newValue < minValue) minValue = newValue
        }

        history.add(Pair(newValue, isIncrement))
    }

    fun reset() {
        count = 0
        totalIncrements = 0
        totalDecrements = 0
        maxValue = 0
        minValue = 0
        totalChanges = 0
        history.clear()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = "Mauricio Corado",
            fontSize = 32.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 32.dp, top = 16.dp)
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        ) {
            IconButton(
                onClick = { updateStats(count - 1, false) },
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(0xFF4C5B84), CircleShape)
            ) {
                Icon(Icons.Default.Remove, contentDescription = "Decrementar", tint = Color.White)
            }

            Spacer(modifier = Modifier.width(32.dp))

            Text(
                text = count.toString(),
                fontSize = 80.sp,
                fontWeight = FontWeight.Light
            )

            Spacer(modifier = Modifier.width(32.dp))

            IconButton(
                onClick = { updateStats(count + 1, true) },
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(0xFF4C5B84), CircleShape)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Incrementar", tint = Color.White)
            }
        }

        HorizontalDivider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(bottom = 16.dp))

        StatRow(label = "Total incrementos:", value = totalIncrements.toString())
        StatRow(label = "Total decrementos:", value = totalDecrements.toString())
        StatRow(label = "Valor máximo:", value = maxValue.toString())
        StatRow(label = "Valor mínimo:", value = minValue.toString())
        StatRow(label = "Total cambios:", value = totalChanges.toString())

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Historial:",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )


        Box(modifier = Modifier.weight(1f)) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(5),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(history.size) { index ->
                    val item = history[index]
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (item.second) Color(0xFF2E7D32) else Color(0xFFC62828))
                    ) {
                        Text(
                            text = item.first.toString(),
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        Button(
            onClick = { reset() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4C5B84)),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text(
                text = "Reiniciar",
                fontSize = 18.sp,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun StatRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = value,
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CounterScreenPreview() {
    MaterialTheme {
        CounterScreen()
    }
}