package com.mungomash.intervalworksout

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.mungomash.intervalworksout.adapter.WorkingOutAdapter
import com.mungomash.intervalworksout.data.Datasource
import com.mungomash.intervalworksout.databinding.ActivityMainBinding
import com.mungomash.intervalworksout.model.Exercise
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.w3c.dom.Text

class WorkingOutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_working_out)

        binding = ActivityMainBinding.inflate(layoutInflater)

        val id = intent.getStringExtra("workoutId")

        // Initialize data.
        val data = Datasource().workoutBasedOnId(id!!.toInt())
        //val timerView = findViewById<RecyclerView>(R.id.countdown_view)
        //timerView.adapter = WorkingOutAdapter(this, data.exercises!!, timerView)

        //Use this setting to improve performance if you know that changes
         //in content do not change the layout size of the RecyclerView
        //timerView.setHasFixedSize(true)

        val nameText: TextView = findViewById(R.id.exercise_title)
        val timer: TextView = findViewById(R.id.timer)

        findViewById<TextView>(R.id.title_text).text = data.name
        findViewById<TextView>(R.id.type_text).text = this.getText(data.workoutType)

        for (exercise: Exercise in data.exercises!!) {
            nameText.text = exercise.name
            object : CountDownTimer(exercise.prep, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    timer.text = (millisUntilFinished / 1000).toString()
                }
                override fun onFinish() { }
            }.start()
            object : CountDownTimer(exercise.duration, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    timer.text = (millisUntilFinished / 1000).toString()
                }
                override fun onFinish() { }
            }.start()
        }

    }
}