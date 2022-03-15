package com.mungomash.workouthelper.data

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mungomash.workouthelper.model.Exercise

class Datasource {

    fun createWorkout(context: Context) {
        FirebaseApp.initializeApp(context)
        val db = Firebase.firestore

        val workout = hashMapOf(
            "name" to "Harder Core Workout",
            "createdBy" to 0,
            "exercises" to listOf("1PaZVGd0oPMjccTBpO2O","5n1aflrVCJmrUSOWLWtJ")
        )

        db.collection("workouts").add(workout)
    }

    fun createExercise(context: Context, name: String, type: String, duration: Long, prepDuration: Long) {

        FirebaseApp.initializeApp(context)
        val db = Firebase.firestore

        val auth: FirebaseAuth = Firebase.auth

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

    fun getSingleExercise(context: Context, id: String): Exercise? {
        FirebaseApp.initializeApp(context)
        val db = Firebase.firestore

        var e: Exercise? = null

        Log.d(TAG, id)

        db.collection("exercises").document(id).get().addOnSuccessListener {
            if (it != null) {
                e = Exercise(
                    it.id,
                    it.data!!["name"].toString(),
                    it.data!!["duration"].toString().toLong(),
                    it.data!!["prepDuration"].toString().toLong(),
                    it.data!!["type"].toString())
            }
        }
        return e
    }

    fun getAllWorkouts(context: Context, listener: OnSuccessListener<QuerySnapshot>) {
        FirebaseApp.initializeApp(context)
        val db = Firebase.firestore

        db.collection("workouts").get().addOnSuccessListener(listener)
    }

    fun getSingleWorkout(context: Context, id: String, listener: OnSuccessListener<DocumentSnapshot>) {
        FirebaseApp.initializeApp(context)
        val db = Firebase.firestore

        db.collection("workouts").document(id).get().addOnSuccessListener(listener)
    }

}