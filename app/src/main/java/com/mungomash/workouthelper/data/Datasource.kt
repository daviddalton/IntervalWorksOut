package com.mungomash.workouthelper.data

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mungomash.workouthelper.R
import com.mungomash.workouthelper.model.Exercise
import com.mungomash.workouthelper.model.Workout
import io.grpc.internal.JsonParser

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

    fun loadExercises(): List<Exercise> {
        return listOf<Exercise>(
            Exercise("Plank", 4000, 7000, "Core"),
            Exercise("Russian Twists", 3000, 7000, "Core"),
            Exercise("Flutter Kicks", 4000, 7000, "Core"),
            Exercise("V-Ups", 3000, 7000, "Core"),
            Exercise("Crunches", 4000, 7000, "Core"),
            Exercise("Windshield Wipers", 3000, 7000, "Core"),
            Exercise("Touch Your Toes", 6000, 7000, "Stretch"),
            Exercise("Butterfly", 6000, 7000, "Stretch"),

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

    fun createExerciseForEmma(context: Context) {
        FirebaseApp.initializeApp(context)
        val db = Firebase.firestore

        val exercise = hashMapOf(
            "name" to "Plank",
            "type" to "Core",
            "duration" to 40000,
            "prepDuration" to 6000
        )

        db.collection("exercises").add(exercise)
    }

    fun getAllExercises(context: Context) {
        FirebaseApp.initializeApp(context)
        val db = Firebase.firestore

        db.collection("exercises")
            .get()
            .addOnSuccessListener { exercises ->
                for (e in exercises) {
                    Log.d(TAG, "${e.id} => ${e.data}")
                }
            }
    }

    fun getAllWorkouts(context: Context) {
        FirebaseApp.initializeApp(context)
        val db = Firebase.firestore

        db.collection("workouts")
            .get()
            .addOnSuccessListener { workouts ->
                val list = buildList<Workout> {
                    for (w in workouts) {
                        val json = JsonParser.parse(w.data.toString())
                        print(json)
                    }
                }

            }
    }

}