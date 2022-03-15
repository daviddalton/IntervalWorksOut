package com.mungomash.workouthelper.ui.exercises

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.ktx.Firebase
import com.mungomash.workouthelper.NewExerciseActivity
import com.mungomash.workouthelper.R
import com.mungomash.workouthelper.adapter.ExercisesAdapter
import com.mungomash.workouthelper.data.Datasource
import com.mungomash.workouthelper.databinding.FragmentExercisesBinding
import com.mungomash.workouthelper.model.Exercise

class ExercisesFragment : Fragment(), OnSuccessListener<QuerySnapshot> {

    private var _binding: FragmentExercisesBinding? = null
    private var recyclerView: RecyclerView? = null
    private val binding get() = _binding!!

    var auth: FirebaseAuth = Firebase.auth

    val user = auth.currentUser

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        Datasource().getAllExercises(this.requireContext(), this)

        _binding = FragmentExercisesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        recyclerView = root.findViewById(R.id.exercises_view)
        val button = root.findViewById<Button>(R.id.new_exercise_button)

        button.setOnClickListener {
            val intent = Intent(activity, NewExerciseActivity::class.java).apply {
            }
            startActivity(intent)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSuccess(exercises: QuerySnapshot) {
        val exerciseList = arrayListOf<Exercise>()

        for (e in exercises) {

            val duration = e.data["duration"] as Long
            val createdBy = e.data["createdBy"]
            val name = e.data["name"].toString()
            val prep = e.data["prepDuration"] as Long
            val type = e.data["type"].toString()

            if (createdBy != null) {
                if (createdBy == "0" || createdBy.toString() == user!!.uid) {
                    exerciseList.add(Exercise(e.id, name, duration, prep, type))
                }
            }
        }
        recyclerView?.adapter = ExercisesAdapter(exerciseList)
    }
}