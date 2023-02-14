package world.oasismc.combatsystem.vision;

import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.mobs.ActiveMob;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import world.oasismc.combatsystem.OasisCombatSystem;
import world.oasismc.combatsystem.event.vision.EntityTrigVisionReactEvent;
import world.oasismc.combatsystem.event.vision.OverloadEvent;
import world.oasismc.combatsystem.function.vision.VisionReactFunction;
import world.oasismc.combatsystem.util.LangUtil;
import world.oasismc.combatsystem.util.NamespacedKeyUtil;
import world.oasismc.combatsystem.vision.function.*;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class VisionManager {

    private static final Map<VisionType, Map<VisionType, Double>> visionReactNumMap;
    private static final Map<VisionType, Map<VisionType, VisionReactFunction>> visionReactFuncMap;

    static {
        visionReactNumMap = new HashMap<>();
        visionReactFuncMap = new HashMap<>();
        loadVisionReactNumMap();
        loadVisionReactFuncMap();
    }

    public static void addVision(Entity entity, VisionType visionType, Double num) {
        addVision(entity, visionType, num, false);
    }

    /**
     * 忽略元素反应，直接添加元素的方法
     * @param entity 添加元素的实体
     * @param visionType 添加的元素类型
     * @param addVisionNum 添加的元素量，若玩家身上已经有此元素，添加后的元素量将会取两者中的最大值
     * @param infinite 是否无限元素量
     */
    public static void addVision(Entity entity, VisionType visionType, Double addVisionNum, boolean infinite) {
        PersistentDataContainer dataContainer = entity.getPersistentDataContainer();
        NamespacedKey visionKey = NamespacedKeyUtil.newKey("vision");
        String visionTypeStr = visionType.name();

        PersistentDataContainer visionContainer = dataContainer.get(NamespacedKeyUtil.VISION_KEY, PersistentDataType.TAG_CONTAINER);
        if (visionContainer == null) {
            visionContainer = dataContainer.getAdapterContext().newPersistentDataContainer();
        }

        PersistentDataContainer typeContainer = visionContainer.get(NamespacedKeyUtil.newKey(visionTypeStr), PersistentDataType.TAG_CONTAINER);
        Double visionNum;
        if (typeContainer == null) {
            typeContainer = visionContainer.getAdapterContext().newPersistentDataContainer();
            visionNum = addVisionNum;
        } else {
            double oldVisionNum = typeContainer.getOrDefault(NamespacedKeyUtil.VISION_NUM_KEY, PersistentDataType.DOUBLE, 0.0);
            if (addVisionNum < 0) {
                visionNum = oldVisionNum + addVisionNum;
            } else {
                visionNum = Math.max(oldVisionNum, addVisionNum);
            }
        }

        if (visionNum <= 0) {
            visionContainer.remove(NamespacedKeyUtil.newKey(visionTypeStr));
        } else {
            typeContainer.set(NamespacedKeyUtil.VISION_UPDATE_TIME_KEY, PersistentDataType.LONG, System.currentTimeMillis());
            if (infinite) {
                typeContainer.set(NamespacedKeyUtil.VISION_INFINITE_KEY, PersistentDataType.BYTE, (byte) 1);
                typeContainer.set(NamespacedKeyUtil.VISION_NUM_KEY, PersistentDataType.DOUBLE, (double) Double.MAX_EXPONENT);
            } else {
                typeContainer.set(NamespacedKeyUtil.VISION_NUM_KEY, PersistentDataType.DOUBLE, visionNum);
            }
            visionContainer.set(NamespacedKeyUtil.newKey(visionTypeStr), PersistentDataType.TAG_CONTAINER, typeContainer);
        }
        dataContainer.set(visionKey, PersistentDataType.TAG_CONTAINER, visionContainer);
    }

    /**
     * 设置实体造成伤害时附加的元素
     * @param entity 设置的实体
     * @param visionType 元素类型
     * @param num 每次攻击附加的元素量
     */
    public static void setAttackVision(Entity entity, VisionType visionType, Double num) {
        PersistentDataContainer dataContainer = entity.getPersistentDataContainer();
        String visionTypeStr = visionType.name();

        PersistentDataContainer visionContainer = dataContainer.getAdapterContext().newPersistentDataContainer();
        visionContainer.set(NamespacedKeyUtil.ATTACK_VISION_TYPE_KEY, PersistentDataType.STRING, visionTypeStr);
        visionContainer.set(NamespacedKeyUtil.ATTACK_VISION_NUM_KEY, PersistentDataType.DOUBLE, num);

        dataContainer.set(NamespacedKeyUtil.ATTACK_VISION_KEY, PersistentDataType.TAG_CONTAINER, visionContainer);
    }

    public static VisionAttack getEntityVisionAttack(Entity entity) {
        PersistentDataContainer dataContainer = entity.getPersistentDataContainer();
        NamespacedKey attackVisionKey = NamespacedKeyUtil.ATTACK_VISION_KEY;
        PersistentDataContainer attackVisionContainer = dataContainer.get(attackVisionKey, PersistentDataType.TAG_CONTAINER);
        if (attackVisionContainer == null)
            return null;
        String typeStr = attackVisionContainer.get(NamespacedKeyUtil.ATTACK_VISION_TYPE_KEY, PersistentDataType.STRING);
        VisionType type = VisionType.valueOf(typeStr);
        Double visionNum = attackVisionContainer.get(NamespacedKeyUtil.ATTACK_VISION_NUM_KEY, PersistentDataType.DOUBLE);
        return new VisionAttack(type, visionNum);
    }

    /**
     * 更新实体身上的元素量
     * @param entity 更新的实体
     */
    public static void updateEntityVision(Entity entity) {
        if (entity == null)
            return;
        PersistentDataContainer dataContainer = entity.getPersistentDataContainer();
        PersistentDataContainer visionContainer = dataContainer.get(NamespacedKeyUtil.VISION_KEY, PersistentDataType.TAG_CONTAINER);
        if (visionContainer == null)
            return;
        long updateTime = System.currentTimeMillis();
        for (NamespacedKey key : visionContainer.getKeys()) {
            if (!NamespacedKeyUtil.VISION_KEY_LIST.contains(key))
                continue;
            PersistentDataContainer vision = visionContainer.get(key, PersistentDataType.TAG_CONTAINER);
            if (vision == null)
                continue;
            byte infinite = vision.getOrDefault(NamespacedKeyUtil.VISION_INFINITE_KEY, PersistentDataType.BYTE, (byte) 0);
            if (infinite == (byte) 1) {
                continue;
            }
            double visionNum = vision.getOrDefault(NamespacedKeyUtil.VISION_NUM_KEY, PersistentDataType.DOUBLE, 0.0);
            long lastUpdateTime = vision.getOrDefault(NamespacedKeyUtil.VISION_UPDATE_TIME_KEY, PersistentDataType.LONG, updateTime);
            long time = lastUpdateTime - updateTime;
            double updateNum = time * 0.0001;
            visionNum += updateNum;
            vision.set(NamespacedKeyUtil.VISION_UPDATE_TIME_KEY, PersistentDataType.LONG, updateTime);
            vision.set(NamespacedKeyUtil.VISION_NUM_KEY, PersistentDataType.DOUBLE, visionNum);
            if (visionNum <= 0) {
                visionContainer.remove(key);
            } else {
                visionContainer.set(key, PersistentDataType.TAG_CONTAINER, vision);
            }
        }
        dataContainer.set(NamespacedKeyUtil.VISION_KEY, PersistentDataType.TAG_CONTAINER, visionContainer);
    }

    /**
     *
     * @param entity 反应实体
     * @param attack 元素攻击对象
     * @return 返回反应后的伤害数字
     */
    public static double visionReaction(EntityTrigVisionReactEvent event) {
        PersistentDataContainer dataContainer = entity.getPersistentDataContainer();
        PersistentDataContainer visionContainer = dataContainer.get(NamespacedKeyUtil.VISION_KEY, PersistentDataType.TAG_CONTAINER);
        if (visionContainer == null)
            return damage;
        for (NamespacedKey key : NamespacedKeyUtil.VISION_KEY_LIST) {
            PersistentDataContainer vision = visionContainer.get(key, PersistentDataType.TAG_CONTAINER);
            if (vision == null)
                continue;
            VisionType reactedVision = VisionType.valueOf(key.getKey().toUpperCase(Locale.ROOT));
            if (reactedVision.equals(attack.getType())) {
                addVision(entity, attack.getType(), attack.getVisionNum());
            } else {
                double reactVisionNum = visionReactNumMap.get(attack.getType()).getOrDefault(reactedVision, 0.0);
                if (reactVisionNum == 0.0) {
                    addVision(entity, attack.getType(), attack.getVisionNum());
                } else {
                    double actualVisionNum = attack.getVisionNum() * reactVisionNum;
                    damage = visionReactFuncMap.get(attack.getType()).get(reactedVision).execute(entity, null, attack, damage);
                    addVision(entity, reactedVision, - actualVisionNum);
                }
            }
        }
        return damage;
    }

    public static void loadVisionReactNumMap() {
        visionReactNumMap.clear();
        for (VisionType reactVision : VisionType.values()) {
            Map<VisionType, Double> map = new HashMap<>();
            for (VisionType reactedVision : VisionType.values()) {
                if (reactVision.equals(reactedVision))
                    continue;
                String configPath = "vision_react_num." + reactVision.name() + "." + reactedVision.name();
                double val = OasisCombatSystem.getInstance().getConfig().getDouble(configPath, 0);
                map.put(reactedVision, val);
            }
            visionReactNumMap.put(reactVision, map);
        }
    }

    public static void loadVisionReactFuncMap() {
        Map<VisionType, VisionReactFunction> pyroMap = new HashMap<>();
        pyroMap.put(VisionType.DENDRO, (entity, trigger, visionType, visionAttack) -> {return d});
        pyroMap.put(VisionType.SURGE, FireFunction.INSTANCE);
        pyroMap.put(VisionType.ELECTRO, OverloadEvent.buildEvent());
        pyroMap.put(VisionType.HYDRO, EvaporationFunc.INSTANCE);
        pyroMap.put(VisionType.CRYO, MeltFunc.INSTANCE);
        pyroMap.put(VisionType.ICE, MeltFunc.INSTANCE);
        pyroMap.put(VisionType.ANEMO, CrystallizedFunc.INSTANCE);
        pyroMap.put(VisionType.GEO, DiffusionFunc.INSTANCE);
        visionReactFuncMap.put(VisionType.PYRO, pyroMap);

        Map<VisionType, VisionReactFunc> hydroMap = new HashMap<>();
        hydroMap.put(VisionType.PYRO, EvaporationFunc.INSTANCE);
        visionReactFuncMap.put(VisionType.HYDRO, hydroMap);

        Map<VisionType, VisionReactFunc> electroMap = new HashMap<>();
        electroMap.put(VisionType.PYRO, OverloadFunc.INSTANCE);
        visionReactFuncMap.put(VisionType.ELECTRO, electroMap);

        Map<VisionType, VisionReactFunc> cryoMap = new HashMap<>();
        cryoMap.put(VisionType.PYRO, MeltFunc.INSTANCE);
        visionReactFuncMap.put(VisionType.CRYO, cryoMap);
    }

    public static void setEntityName(Entity entity) {
        String format = LangUtil.lang("entity_display_format");
        String entityName;
        if (Bukkit.getPluginManager().getPlugin("MythicMobs") != null) {
            ActiveMob mob = MythicBukkit.inst().getMobManager().getActiveMob(entity.getUniqueId()).orElse(null);
            if (mob != null)
                entityName = mob.getDisplayName();
            else
                entityName = LangUtil.lang("entity_name." + entity.getType().name());
        } else {
            entityName = LangUtil.lang("entity_name." + entity.getType().name());
        }

        StringBuilder visionBuilder = new StringBuilder();
    }

    public static double getVisionNum(Entity entity, VisionType visionType) {
        PersistentDataContainer dataContainer = entity.getPersistentDataContainer();
        PersistentDataContainer visionContainer = dataContainer.get(NamespacedKeyUtil.VISION_KEY, PersistentDataType.TAG_CONTAINER);
        if (visionContainer == null)
            return 0;
        NamespacedKey visionKey = NamespacedKeyUtil.newKey(visionType.name());
        PersistentDataContainer vision = visionContainer.get(visionKey, PersistentDataType.TAG_CONTAINER);
        if (vision == null)
            return 0;
        return vision.getOrDefault(NamespacedKeyUtil.VISION_NUM_KEY, PersistentDataType.DOUBLE, 0.0);
    }

    public static Map<VisionType, Map<VisionType, Double>> getVisionReactNumMap() {
        return visionReactNumMap;
    }

    public static void clearVision(Entity entity) {
        PersistentDataContainer dataContainer = entity.getPersistentDataContainer();
        dataContainer.remove(NamespacedKeyUtil.VISION_KEY);
    }

}
