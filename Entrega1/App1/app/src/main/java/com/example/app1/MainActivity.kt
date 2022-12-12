package com.example.app1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val num1Text =findViewById<EditText>(R.id.num1)
        val num2Text =findViewById<EditText>(R.id.num2)
        val btn= findViewById<Button>(R.id.button)
        val resultText=findViewById<TextView>(R.id.result)
        btn.setOnClickListener {
            try{
            val num1=num1Text.text.toString().toInt()
            val num2=num2Text.text.toString().toInt()
            resultText.text=("Result: "+(num1+num2))
            }
            catch (e:java.lang.NumberFormatException) {
                resultText.text = ("Input two integers")
            }
        };
    }

}