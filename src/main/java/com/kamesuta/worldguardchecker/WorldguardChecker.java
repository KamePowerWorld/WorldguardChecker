package com.kamesuta.worldguardchecker;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * ワールドガードの保護を持っているかチェックし、持っていない場合はログイン時にメッセージを表示してテレポートさせるプラグイン
 */
public final class WorldguardChecker extends JavaPlugin implements Listener {

    private Location location;
    private String message;

    @Override
    public void onEnable() {
        // Plugin startup logic

        // コンフィグ読み込み
        saveDefaultConfig();
        location = getConfig().getLocation("teleport_location");
        message = getConfig().getString("teleport_message");
        if (location == null || message == null) {
            getLogger().warning("コンフィグが不正です。");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // イベント登録
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // プレイヤー
        Player player = event.getPlayer();
        // プレイヤーが世界中で保護を持っているかチェック
        boolean hasRegion = Bukkit.getWorlds().stream()
                .map(world -> WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(world)))
                .filter(Objects::nonNull)
                .flatMap(manager -> manager.getRegions().values().stream())
                .flatMap(e -> Stream.concat(e.getOwners().getUniqueIds().stream(), e.getMembers().getUniqueIds().stream()))
                .anyMatch(e -> e.equals(player.getUniqueId()));
        // 保護を持っている場合は無視
        if (hasRegion) return;
        // チュートリアルに飛ばす
        player.teleport(location);
        player.sendMessage(message);
    }
}
