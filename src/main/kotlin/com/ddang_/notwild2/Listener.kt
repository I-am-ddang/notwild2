package com.ddang_.notwild2

import com.destroystokyo.paper.event.server.PaperServerListPingEvent
import net.md_5.bungee.api.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

@Suppress("deprecation")
class Listener: Listener {

    @EventHandler
    fun ping(e: PaperServerListPingEvent){
        e.motd = "${ChatColor.of("#f1cf95")}§l      Project Notwild2 §f- §7Made by DDang_\n§f      Minecraft Wild Game §7[1.18.1]"
        e.version = "Notwild2 1.18.1"
    }

}