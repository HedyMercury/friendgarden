package com.example.friendgarden

class Plant(
    val type: PlantType,
    var score: Int = 0 // changed from val to var, hopefully works
) {
    fun getHealthState(): HealthState = when {
        score <= -20 -> HealthState.VERY_UNHEALTHY
        score <= 0 -> HealthState.UNHEALTHY
        score <= 20 -> HealthState.HEALTHY
        else -> HealthState.VERY_HEALTHY
    }
}