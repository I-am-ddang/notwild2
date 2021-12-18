package com.ddang_.notwild2.util

import org.bukkit.Bukkit

class Scheduler {

    private var task = 0

    fun getTask(): Int {
        return task
    }

    fun setTask(task: Int) {
        this.task = task
    }

    fun cancel() {
        Bukkit.getScheduler().cancelTask(task)
    }

}