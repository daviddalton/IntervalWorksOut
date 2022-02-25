package com.mungomash.workouthelper

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.mungomash.workouthelper.data.Datasource

class NewExerciseActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_exercise)

        val button = findViewById<Button>(R.id.add_button)

        var duration = findViewById<EditText>(R.id.enterDurationTime).text.toString()
        var prep = findViewById<EditText>(R.id.enterPrepTime).text.toString()
        var name = findViewById<EditText>(R.id.enterExerciseName).text.toString()

        button.setOnClickListener {
            if (name.isNotEmpty()) {
                if (duration.isEmpty()) {
                    duration = "0"
                }
                if (prep.isEmpty()) {
                    prep = "0"
                }
                Datasource().createExercise(this, name, "Core", duration.toLong() * 1000, prep.toLong() * 1000)
            }
        }
    }
}