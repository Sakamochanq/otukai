package com.Sakamochanq.otukai.command

import com.Sakamochanq.otukai.OtukaiPlugin
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class OtukaiCommand(
    private val plugin: OtukaiPlugin
) : CommandExecutor, TabCompleter {

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {

        if (args.isEmpty()) {
            sender.sendMessage("§e/otukai start")
            sender.sendMessage("§e/otukai stop")
            return true
        }

        when (args[0].lowercase()) {
            "start" -> {
                if (plugin.gameManager.start()) {
                    sender.sendMessage("§a§l[おつかい] §aゲームを開始しました！")
                } else {
                    sender.sendMessage("§a§l[おつかい] §cすでにゲームが実行中です。")
                }
            }

            "stop" -> {
                if (plugin.gameManager.stop()) {
                    sender.sendMessage("§a§l[おつかい] §cゲームを終了しました。")
                } else {
                    sender.sendMessage("§a§l[おつかい] §c現在ゲームは実行されていません。")
                }
            }

            else -> {
                sender.sendMessage("§a§l[おつかい] §c不明なコマンドです。")
                sender.sendMessage("§a§l[おつかい] §e/otukai start")
                sender.sendMessage("§a§l[おつかい] §e/otukai stop")
            }
        }

        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): List<String> {

        if (args.size == 1) {
            return listOf("start", "stop")
                .filter { it.startsWith(args[0], ignoreCase = true) }
        }

        return emptyList()
    }
}