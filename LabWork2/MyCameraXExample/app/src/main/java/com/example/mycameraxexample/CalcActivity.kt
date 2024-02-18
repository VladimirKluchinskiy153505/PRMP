package com.example.mycameraxexample

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.util.Log
import android.view.View
import androidx.compose.ui.platform.LocalContext

class CalcActivity : AppCompatActivity() {

    private lateinit var display: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calc)

        val detectedText = intent.getStringExtra("EXPRESSION") ?: "0"
        Log.d("detectedText", detectedText)
        display = findViewById(R.id.data_tv)
        display.setText(detectedText)
    }
    fun OnButtonClick(v: View){
        val context = v.context
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
    }
}