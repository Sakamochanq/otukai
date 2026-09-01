package com.Sakamochanq.otukai.task

import kotlin.time.Duration

interface Task {
    val description: String
    val timeLimit: Duration

    fun isCompleted(progress: Int): Boolean
}