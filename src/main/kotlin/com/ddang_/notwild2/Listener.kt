package com.ddang_.notwild2

import com.ddang_.notwild2.Notwild2.Companion.broad
import com.ddang_.notwild2.util.Scheduler
import com.destroystokyo.paper.event.server.PaperServerListPingEvent
import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.entity.Turtle
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerAdvancementDoneEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.player.PlayerResourcePackStatusEvent
import org.bukkit.inventory.ItemStack

@Suppress("deprecation")
class Listener: Listener {

    companion object {
        val rp = HashSet<String>()
        val inv = HashSet<String>()
    }

    @EventHandler
    fun quit(e: PlayerQuitEvent){
        val p = e.player

        //리소스팩팩
       rp.remove(p.name)
        inv.remove(p.name)
    }

    @EventHandler
    fun ping(e: PaperServerListPingEvent){
        e.motd = "${ChatColor.of("#f1cf95")}§l      Project Notwild2 §f- §7Made by DDang_\n§f      Minecraft Wild Survival Game §7[1.18.1]"
        e.version = "Notwild2 1.18.1"
    }

    @EventHandler
    fun join(e: PlayerJoinEvent){
        val p = e.player

        //리소스팩
        rp.add(p.name)
        val sc = Scheduler()
        sc.setTask(Bukkit.getScheduler().scheduleSyncRepeatingTask(Notwild2.instance, {
            if (rp.contains(p.name)){
                p.setResourcePack("https://drive.google.com/uc?export=download&id=19GDceTpmk-yDIzvlB6bIn3OyLcKCnvfd")
            } else {
                sc.cancel()
            }
        }, 0L, 20L))
    }

    @EventHandler
    fun resourcepack(e: PlayerResourcePackStatusEvent){
        val p = e.player
        when (e.status){
            PlayerResourcePackStatusEvent.Status.ACCEPTED -> {
                rp.remove(p.name)
                inv.add(p.name)
            }
            PlayerResourcePackStatusEvent.Status.DECLINED -> {
                rp.remove(p.name)
                p.kickPlayer("§c  [!] §f리소스팩을 받아야 정상적인 게임을 진행할 수 있습니다. \n §7멀티플레이 -> 서버 목록 수정 -> 서버 리소스팩: 사용 으로 설정해주세요.")
            }
            PlayerResourcePackStatusEvent.Status.FAILED_DOWNLOAD -> {
                rp.remove(p.name)
                p.kickPlayer("§c  [!] §f리소스팩을 받아야 정상적인 게임을 진행할 수 있습니다. \n §7멀티플레이 -> 서버 목록 수정 -> 서버 리소스팩: 사용 으로 설정해주세요.")
            }
            PlayerResourcePackStatusEvent.Status.SUCCESSFULLY_LOADED -> {
                rp.remove(p.name)
                inv.remove(p.name)
            }
        }
    }

    @EventHandler
    fun damage(e: EntityDamageEvent){
        val p = e.entity
        if (p is Player){
            if (rp.contains(p.name) || inv.contains(p.name)){
                e.isCancelled = true
            }
        }
    }

    @EventHandler
    fun death(e: PlayerDeathEvent){
        val p = e.entity
        for (i in 9..35) {
            val item = p.inventory.getItem(i) ?: continue
            p.location.world.dropItem(p.location, item)
            p.inventory.setItem(i, ItemStack(Material.AIR))
        }
        val list = arrayListOf(p.inventory.helmet, p.inventory.chestplate, p.inventory.leggings, p.inventory.boots)
        for (item in list){
            if (item == null) {
                continue
            }
            p.world.dropItem(p.location, item)
        }
        p.inventory.helmet = ItemStack(Material.AIR)
        p.inventory.chestplate = ItemStack(Material.AIR)
        p.inventory.leggings = ItemStack(Material.AIR)
        p.inventory.boots = ItemStack(Material.AIR)
    }

    @EventHandler
    fun death2(e: EntityDeathEvent){
        val v = e.entity
        if (v is Turtle){
            v.world.dropItem(v.location, ItemStack(Material.TURTLE_EGG))
        }
    }
}