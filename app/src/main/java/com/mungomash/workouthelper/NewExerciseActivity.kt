package com.mungomash.workouthelper

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.mungomash.workouthelper.data.Datasource

class NewExerciseActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_exercise)

        val button = findViewById<Button>(R.id.add_button)

        var duration = findViewById<EditText>(R.id.enterDurationTime)
        var prep = findViewById<EditText>(R.id.enterPrepTime)
        var name = findViewById<EditText>(R.id.enterExerciseName)

        button.setOnClickListener {
            if (name.text.toString().isNotEmpty()) {
                if (duration.text.toString().isEmpty()) {
                    duration.setText("0")
                }
                if (prep.text.toString().isEmpty()) {
                    prep.setText("0")
                }
                Datasource().createExercise(this, name.text.toString(), "Core", duration.text.toString().toLong() * 1000, prep.text.toString().toLong() * 1000)
            }
        }
    }
}