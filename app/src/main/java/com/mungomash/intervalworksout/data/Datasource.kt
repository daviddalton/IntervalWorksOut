package com.mungomash.intervalworksout.data

import com.mungomash.intervalworksout.R
import com.mungomash.intervalworksout.model.Exercise
import com.mungomash.intervalworksout.model.Workout

class Datasource {

    fun loadSets(): List<Workout> {
        return listOf<Workout>(
            //TODO: create custom sets up exercises for each one of these
            baseCoreWorkout(),
            baseStretchingWorkout(),
            baseBodyweightWorkout(),
            baseFormDrillWorkout(),
            Workout(9999,"New Workout", R.string.new_set_hint, null)
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
        return Workout(5,"Invalid Workout", R.string.new_set_hint, null)
    }

    fun baseCoreWorkout(): Workout {
        return Workout(1, "Easy and Short Core Workout", R.string.core, loadExercises())
    }

    fun baseStretchingWorkout(): Workout {
        return Workout(2,"After Run Stretching", R.string.stretch, loadExercises())
    }

    fun baseBodyweightWorkout(): Workout {
        return Workout(3,"Bodyweight Lifting", R.string.bodyweight, loadExercises())
    }

    fun baseFormDrillWorkout(): Workout {
        return Workout(4,"Standard Form Drills", R.string.formDrill, loadExercises())
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
}