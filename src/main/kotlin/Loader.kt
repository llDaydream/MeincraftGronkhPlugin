package main

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin

public  class Loader : JavaPlugin(), Listener, CommandExecutor {

    override fun onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this)
        Bukkit.getScheduler().runTaskTimer(this, showGronkhTvMessage(), 20*3, 20*10)
    }

    @EventHandler
    public fun joinEvent(event: PlayerJoinEvent){
        event.player.sendMessage("Herzlich Willkommen auf dem Gronkh Minecraft Testserver")
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
                if (sender is Player) {
                    when (command.name) {
                        "hug" -> {
                            if (args.isEmpty()) {
                                sender.sendMessage("Du musst einen Spieler angeben")

                            } else {

                               var targetPlayer = Bukkit.getServer().getPlayerExact(args[0])

                                if (targetPlayer == null){
                                    sender.sendMessage("Spieler ist derzeit offline")
                                }
                                else {
                                    sender.sendMessage("Du hast " + targetPlayer.displayName + "Umarmt!")
                                    targetPlayer.sendMessage(sender.displayName + " hat dich umarmt")
                                }

                            }
                        }
                    }
                }

        return true
    }

    private fun showGronkhTvMessage() : Runnable {
        var runnable : Runnable = Runnable {
            Bukkit.broadcastMessage("Besuche https://gronkh.tv")
        }
        return runnable
    }
}