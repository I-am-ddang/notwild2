package com.ddang_.notwild2

import org.bukkit.Bukkit
import org.bukkit.GameRule
import org.bukkit.World
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitScheduler

@Suppress("deprecation")
class Notwild2 : JavaPlugin() {

    companion object{

        fun String.info() = Bukkit.getLogger().info(this)
        fun String.warn() = Bukkit.getLogger().warning(this)
        fun String.broad() = Bukkit.broadcastMessage(this)


        fun Long.rt(delay: Long = 1, run: Runnable) = scheduler.runTaskTimer(instance, run, delay, this)
        fun Long.rtAsync(delay: Long = 1, run: Runnable) = scheduler
            .runTaskTimerAsynchronously(instance, run, delay, this)
        fun rAsync(run: Runnable) = scheduler.runTaskAsynchronously(instance, run)
        fun r(run: Runnable) = scheduler.runTask(instance, run)
        fun Long.rl(run: Runnable) = scheduler.runTaskLater(instance, run, this)
        fun Long.rlAsync(run: Runnable) = scheduler.runTaskLaterAsynchronously(instance, run, this)

        //인스턴스 필드
        lateinit var instance: Plugin
        lateinit var scheduler: BukkitScheduler
            private set
        lateinit var players: MutableCollection<out Player>
            private set
    }

    //이벤트 목록
    private val events = arrayOf(Listener())

    private fun World.gamerule(){
        setGameRule(GameRule.REDUCED_DEBUG_INFO, false)
        setGameRule(GameRule.DO_WEATHER_CYCLE, true)
        setGameRule(GameRule.DO_MOB_SPAWNING, true)
        setGameRule(GameRule.KEEP_INVENTORY, true)
        setGameRule(GameRule.DO_TRADER_SPAWNING, true)
        setGameRule(GameRule.NATURAL_REGENERATION, true)
        setGameRule(GameRule.DO_TILE_DROPS, true)
        setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true)
        setGameRule(GameRule.FALL_DAMAGE, true)
        setGameRule(GameRule.RANDOM_TICK_SPEED, 6)
    }

    override fun onEnable() {
        //인스턴스 잡아주기
        players = server.onlinePlayers
        instance = this
        scheduler = server.scheduler
        val w = Bukkit.getWorld("world")!!

        //이벤트 등록
        server.pluginManager.apply { events.forEach { registerEvents(it, this@Notwild2) } }

        //게임룰
        w.gamerule()
    }


    override fun onDisable() {
        // Plugin shutdown logic
    }
}