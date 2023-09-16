package main

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin

public  class Loader : JavaPlugin(), Listener, CommandExecutor {

    var setDay = false;

    override fun onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this)
        Bukkit.getScheduler().runTaskTimer(this, showGronkhTvMessage(), 20*3, 20*10)
        Bukkit.getScheduler().runTaskTimer(this, setDay(),  20*10, 20*30)
    }

    @EventHandler
    public fun joinEvent(event: PlayerJoinEvent){
        event.player.sendMessage("Herzlich Willkommen auf dem Gronkh Minecraft Testserver")
    }

    @EventHandler
    public fun blockBreak(event: BlockBreakEvent){
        event.player.sendMessage("Abgebauter block: " +  event.block.type.name)
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

                        "keepday" -> {
                            if (!setDay){
                                setDay = true;
                                setDay()
                                sender.sendMessage("Dauerhafter Tag aktiviert")
                            }
                            else {
                                setDay = false
                                sender.sendMessage("Dauerhafter Tag deaktiviert")
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

    private fun setDay() : Runnable {

        var r : Runnable = Runnable {
            if (setDay){
                val worlds = Bukkit.getWorlds()
                for(world in worlds){
                    if (world.time > 13000){
                        world.time = 0
                    }
                }
            }
        }

        return r;
    }
}