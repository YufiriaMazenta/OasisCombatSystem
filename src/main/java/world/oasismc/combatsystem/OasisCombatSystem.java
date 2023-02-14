package world.oasismc.combatsystem;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import world.oasismc.combatsystem.cmd.PluginCommand;
import world.oasismc.combatsystem.listener.EntityAttackListener;
import world.oasismc.combatsystem.listener.EntitySpawnListener;
import world.oasismc.combatsystem.listener.PlayerDeathListener;
import world.oasismc.combatsystem.util.LangUtil;

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
        addTimerTask();
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
        Bukkit.getPluginManager().registerEvents(EntitySpawnListener.INSTANCE, this);
        Bukkit.getPluginManager().registerEvents(EntityAttackListener.INSTANCE, this);
        Bukkit.getPluginManager().registerEvents(PlayerDeathListener.INSTANCE, this);
    }

    private void addTimerTask() {
//        Bukkit.getScheduler().runTaskTimer(this, () -> {
//            List<UUID> updatedEntityUUID = new ArrayList<>();
//            for (Player player : Bukkit.getOnlinePlayers()) {
//                for (LivingEntity entity : player.getLocation().getNearbyLivingEntities(48, 16)) {
//                    if (updatedEntityUUID.contains(entity.getUniqueId()))
//                        continue;
//                    VisionManager.updateEntityVision(entity);
//                    updatedEntityUUID.add(entity.getUniqueId());
//                }
//            }
//        }, 100L, 5L);
    }

}
