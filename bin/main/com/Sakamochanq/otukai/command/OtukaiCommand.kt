package com.Sakamochanq.otukai.command

import com.Sakamochanq.otukai.OtukaiPlugin
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class OtukaiCommand(
    private val plugin: OtukaiPlugin
) : CommandExecutor {

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
                    sender.sendMessage("§aおつかいゲームを開始しました！")
                } else {
                    sender.sendMessage("§cすでにゲームが実行中です。")
                }
            }

            "stop" -> {
                if (plugin.gameManager.stop()) {
                    sender.sendMessage("§cおつかいゲームを終了しました。")
                } else {
                    sender.sendMessage("§c現在ゲームは実行されていません。")
                }
            }

            else -> {
                sender.sendMessage("§c不明なコマンドです。")
                sender.sendMessage("§e/otukai start")
                sender.sendMessage("§e/otukai stop")
            }
        }

        return true
    }
}