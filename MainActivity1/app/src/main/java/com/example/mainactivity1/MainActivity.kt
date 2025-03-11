package com.example.mainactivity1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AgeCheckScreen()
        }
    }
}

@Composable
fun AgeCheckScreen() {
    // State để lưu giá trị nhập và kết quả
    var hoTen by remember { mutableStateOf("") }
    var tuoi by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Tiêu đề
        Text(
            text = "THỰC HÀNH 01",
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // TextField cho Họ và tên
        OutlinedTextField(
            value = hoTen,
            onValueChange = { hoTen = it },
            label = { Text("Họ và tên") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // TextField cho Tuổi
        OutlinedTextField(
            value = tuoi,
            onValueChange = {
                // Chỉ cho phép nhập số
                if (it.all { char -> char.isDigit() } || it.isEmpty()) {
                    tuoi = it
                    error = ""
                } else {
                    error = "Vui lòng nhập số hợp lệ"
                }
            },
            label = { Text("Tuổi") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            isError = error.isNotEmpty()
        )

        // Hiển thị thông báo lỗi nếu có
        if (error.isNotEmpty()) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Nút Kiểm tra
        Button(
            onClick = {
                // Kiểm tra dữ liệu đầu vào
                if (hoTen.isEmpty()) {
                    error = "Vui lòng nhập họ và tên"
                    return@Button
                }
                if (tuoi.isEmpty()) {
                    error = "Vui lòng nhập tuổi"
                    return@Button
                }

                val age = tuoi.toIntOrNull()
                if (age == null || age < 0) {
                    error = "Tuổi không hợp lệ"
                    return@Button
                }

                // Xác định nhóm tuổi
                result = when {
                    age > 65 -> "$hoTen là Ngữ giới"
                    age in 6..65 -> "$hoTen là Ngữ lơn"
                    age in 2..6 -> "$hoTen là Trẻ em"
                    age < 2 -> "$hoTen là Em bé"
                    else -> "Tuổi không hợp lệ"
                }
                error = "" // Xóa lỗi nếu kiểm tra thành công
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Kiểm tra")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Hiển thị kết quả
        if (result.isNotEmpty()) {
            Text(
                text = result,
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}