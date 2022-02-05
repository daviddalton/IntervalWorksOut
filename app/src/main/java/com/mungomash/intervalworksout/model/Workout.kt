package com.mungomash.intervalworksout.model

data class Workout(val id: Int, val name: String, val workoutType: Int, val exercises: List<Exercise>?) {

}
