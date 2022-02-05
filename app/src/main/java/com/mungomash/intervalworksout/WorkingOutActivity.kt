package com.mungomash.intervalworksout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.mungomash.intervalworksout.adapter.WorkingOutAdapter
import com.mungomash.intervalworksout.data.Datasource
import com.mungomash.intervalworksout.databinding.ActivityMainBinding

class WorkingOutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_working_out)

        binding = ActivityMainBinding.inflate(layoutInflater)

        val id = intent.getStringExtra("workoutId")

        // Initialize data.
        val data = Datasource().workoutBasedOnId(id!!.toInt())
        val timerView = findViewById<RecyclerView>(R.id.countdown_view)
        timerView.adapter = WorkingOutAdapter(this, data.exercises!!, timerView)

        //Use this setting to improve performance if you know that changes
         //in content do not change the layout size of the RecyclerView
        timerView.setHasFixedSize(true)

    }
}