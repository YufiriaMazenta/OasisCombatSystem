package world.oasismc.combatsystem;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import world.oasismc.combatsystem.cmd.PluginCommand;
import world.oasismc.combatsystem.listener.*;
import world.oasismc.combatsystem.util.LangUtil;
import world.oasismc.combatsystem.vision.VisionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class OasisCombatSystem extends JavaPlugin {

    private static OasisCombatSystem INSTANCE = null;

    public OasisCombatSystem() {
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        regListeners();
        Bukkit.getPluginCommand("combat-system").setExecutor(PluginCommand.INSTANCE);
        Bukkit.getPluginCommand("combat-system").setTabCompleter(PluginCommand.INSTANCE);
//        addTimerTask();
        LangUtil.info("message.load.finish");
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
    }

    public static OasisCombatSystem getInstance() {
        return INSTANCE;
    }

    private void regListeners() {
        Bukkit.getPluginManager().registerEvents(EntitySpawnHandler.INSTANCE, this);
        Bukkit.getPluginManager().registerEvents(EntityAttackHandler.INSTANCE, this);
        Bukkit.getPluginManager().registerEvents(PlayerEventHandler.INSTANCE, this);
        Bukkit.getPluginManager().registerEvents(VisionEventHandler.INSTANCE, this);
        Bukkit.getPluginManager().registerEvents(ReactConsumptionHandler.INSTANCE, this);
    }

    private void addTimerTask() {
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            List<UUID> updatedEntityUUID = new ArrayList<>();
            for (Player player : Bukkit.getOnlinePlayers()) {
                for (LivingEntity entity : player.getLocation().getNearbyLivingEntities(48, 16)) {
                    if (updatedEntityUUID.contains(entity.getUniqueId()))
                        continue;
                    VisionManager.updateEntityVision(entity);
                    updatedEntityUUID.add(entity.getUniqueId());
                }
            }
        }, 100L, 5L);
    }

}
