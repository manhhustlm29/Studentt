package com.example.studentmanager

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class StudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        val etId = findViewById<EditText>(R.id.etStudentId)
        val etName = findViewById<EditText>(R.id.etStudentName)
        val btnSave = findViewById<Button>(R.id.btnAdd)

        val studentId = intent.getStringExtra("id")
        val studentName = intent.getStringExtra("name")

        studentId?.let {
            etId.setText(it)
        }

        studentName?.let {
            etName.setText(it)
        }

        btnSave.setOnClickListener {
            val id = etId.text.toString()
            val name = etName.text.toString()

            if (id.isNotBlank() && name.isNotBlank()) {
                val resultIntent = Intent().apply {
                    putExtra("id", id)
                    putExtra("name", name)
                }
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}
