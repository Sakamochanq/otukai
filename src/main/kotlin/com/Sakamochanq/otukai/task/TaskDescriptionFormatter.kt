package com.Sakamochanq.otukai.task

import com.Sakamochanq.otukai.task.breakblock.BreakBlockTask
import com.Sakamochanq.otukai.task.craft.CraftTask
import com.Sakamochanq.otukai.task.fish.FishTask
import com.Sakamochanq.otukai.task.item.ItemTask
import com.Sakamochanq.otukai.task.kill.KillTask
import com.Sakamochanq.otukai.task.use.UseItemTask

object TaskDescriptionFormatter {

    fun create(
        task: Task,
        targetAmount: Int
    ): String {
        return when (task) {
            is ItemTask -> "${task.displayName}を${targetAmount}個手に入れよう！"
            is KillTask -> "${task.displayName}を${targetAmount}体倒そう！"
            is UseItemTask -> "${task.displayName}を${targetAmount}回使おう！"
            is BreakBlockTask -> "${task.displayName}を${targetAmount}個壊そう！"
            is CraftTask -> "${task.displayName}を${targetAmount}個クラフトしよう！"
            is FishTask -> "${task.displayName}を${targetAmount}匹釣ろう！"
            else -> task.description
        }
    }
}