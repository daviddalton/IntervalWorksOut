package com.mungomash.workouthelper.ui.home

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.QuerySnapshot
import com.mungomash.workouthelper.R
import com.mungomash.workouthelper.WorkoutActivity
import com.mungomash.workouthelper.adapter.WorkoutAdapter
import com.mungomash.workouthelper.data.Datasource
import com.mungomash.workouthelper.databinding.ExercisingViewBinding
import com.mungomash.workouthelper.databinding.FragmentHomeBinding
import com.mungomash.workouthelper.model.ExerciseRef
import com.mungomash.workouthelper.model.Workout

class HomeFragment : Fragment(), OnSuccessListener<QuerySnapshot> {

    private var _binding: FragmentHomeBinding? = null
    private var recyclerView: RecyclerView? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        Datasource().getAllWorkouts(this.requireContext(), this)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        recyclerView = root.findViewById(R.id.workouts_view)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSuccess(workouts: QuerySnapshot) {
        var workoutList = arrayListOf<Workout>()

        for (w in workouts) {
            val name = w.data["name"].toString()
            //probably not going to work...need to make sure we build this out right
            val exerciseRef = w.data["exercises"]

            Log.d(TAG, "$exerciseRef")

            workoutList.add(Workout(w.id, name, arrayOf(ExerciseRef("exercises/1PaZVGd0oPMjccTBpO2O"))))
        }
        recyclerView?.adapter = WorkoutAdapter(WorkoutAdapter.OnClickListener { item ->
            handleClick(item)
        },this.requireContext(), workoutList)
    }

    private fun handleClick(item: Workout) {
        if (item.id != "9999") {
            val intent = Intent(this.context, WorkoutActivity::class.java).apply {
                putExtra("workoutId", item.id)
            }
            startActivity(intent)
        } else {
            //TODO: replace this with a new activity that enables the ability to actually create a new workout and save it to local storage.
            Toast.makeText(this.requireContext(), "Creating new workout", Toast.LENGTH_SHORT).show()
        }
    }
}