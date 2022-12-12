package com.example.app2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn = findViewById<Button>(R.id.button)
        val messageText=findViewById<EditText>(R.id.message)
        btn.setOnClickListener{
            val message=messageText.text.toString()
            val intent =Intent(this,ResultActivity::class.java)
            intent.putExtra("message",message)
            startActivity(intent)
        }
    }
}