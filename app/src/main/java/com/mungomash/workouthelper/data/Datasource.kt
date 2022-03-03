package com.mungomash.workouthelper.data

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mungomash.workouthelper.model.ExerciseRef
import com.mungomash.workouthelper.model.Workout

class Datasource {

    fun loadSets(): List<Workout> {
        return listOf<Workout>(
            //TODO: create custom sets up exercises for each one of these
            baseCoreWorkout(),
            baseStretchingWorkout(),
            baseBodyweightWorkout(),
            baseFormDrillWorkout(),
            Workout("9999","New Workout", loadExercises())
        )
    }

    fun workoutBasedOnId(id: Int): Workout{
        when (id) {
            1 -> return baseCoreWorkout()
            2 -> return baseStretchingWorkout()
            3 -> return baseBodyweightWorkout()
            4 -> return baseFormDrillWorkout()
            else -> {
                print("No workout found with that ID")
            }
        }
        return Workout("5","Invalid Workout", loadExercises())
    }

    fun baseCoreWorkout(): Workout {
        return Workout("1", "Easy and Short Core Workout", loadExercises())
    }

    fun baseStretchingWorkout(): Workout {
        return Workout("2","After Run Stretching", loadExercises())
    }

    fun baseBodyweightWorkout(): Workout {
        return Workout("3","Bodyweight Lifting", loadExercises())
    }

    fun baseFormDrillWorkout(): Workout {
        return Workout("4","Standard Form Drills", loadExercises())
    }

    fun loadExercises(): Array<ExerciseRef> {
        return arrayOf<ExerciseRef>(
            ExerciseRef("1"),
            ExerciseRef("2"),
            ExerciseRef("3"),
            ExerciseRef("4"),
            ExerciseRef("5"),
            ExerciseRef("6"),
            ExerciseRef("7"),
            ExerciseRef("8"),

        )
    }

    fun createUser(context: Context) {
        FirebaseApp.initializeApp(context)
        val db = Firebase.firestore

        val user = hashMapOf(
            "first" to "Emma",
            "last" to "Dalton",
            "born" to 2000
        )

        db.collection("users").add(user).addOnSuccessListener { documentReference ->
            Log.d(ContentValues.TAG, "Document Snapshot added with ID: ${documentReference.id}")
        }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }
    }

    fun createWorkoutForEmma(context: Context) {
        FirebaseApp.initializeApp(context)
        val db = Firebase.firestore

        val workout = hashMapOf(
            "name" to "Easy and Short Core Workout",
            "type" to "Core"
        )

        db.collection("workouts").add(workout)
    }

    fun createExercise(context: Context, name: String, type: String, duration: Long, prepDuration: Long) {

        FirebaseApp.initializeApp(context)
        val db = Firebase.firestore

        var auth: FirebaseAuth = Firebase.auth

        val user = auth.currentUser

        if (user != null) {
            //TODO: pass the user id as the createdBy
            val exercise = hashMapOf(
                "name" to name,
                "type" to type,
                "duration" to duration,
                "prepDuration" to prepDuration,
                "createdBy" to user.uid
            )

            db.collection("exercises").add(exercise)
        }

    }

    fun getAllExercises(context: Context, listener: OnSuccessListener<QuerySnapshot>) {
        FirebaseApp.initializeApp(context)
        val db = Firebase.firestore

        db.collection("exercises").get().addOnSuccessListener(listener)
    }

    fun getSingleExercise(context: Context, id: String) {
        FirebaseApp.initializeApp(context)
        val db = Firebase.firestore

        db.collection("$id")
    }

    fun getAllWorkouts(context: Context, listener: OnSuccessListener<QuerySnapshot>) {
        FirebaseApp.initializeApp(context)
        val db = Firebase.firestore

        db.collection("workouts").get().addOnSuccessListener(listener)
    }

}