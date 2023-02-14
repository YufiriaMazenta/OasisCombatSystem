package world.oasismc.combatsystem.vision;

import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.mobs.ActiveMob;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import world.oasismc.combatsystem.OasisCombatSystem;
import world.oasismc.combatsystem.event.vision.*;
import world.oasismc.combatsystem.function.Function4;
import world.oasismc.combatsystem.function.vision.VisionReactFunction;
import world.oasismc.combatsystem.listener.VisionEventHandler;
import world.oasismc.combatsystem.util.LangUtil;
import world.oasismc.combatsystem.util.NamespacedKeyUtil;

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

    public static VisionAttack getEntityVisionAttack(Entity entity, double damage) {
        PersistentDataContainer dataContainer = entity.getPersistentDataContainer();
        NamespacedKey attackVisionKey = NamespacedKeyUtil.ATTACK_VISION_KEY;
        PersistentDataContainer attackVisionContainer = dataContainer.get(attackVisionKey, PersistentDataType.TAG_CONTAINER);
        if (attackVisionContainer == null)
            return null;
        String typeStr = attackVisionContainer.get(NamespacedKeyUtil.ATTACK_VISION_TYPE_KEY, PersistentDataType.STRING);
        VisionType type = VisionType.valueOf(typeStr);
        Double visionNum = attackVisionContainer.get(NamespacedKeyUtil.ATTACK_VISION_NUM_KEY, PersistentDataType.DOUBLE);
        return new VisionAttack(type, visionNum, damage);
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
            VisionType visionType = VisionType.valueOf(key.getKey().toUpperCase(Locale.ROOT));
            if (!NamespacedKeyUtil.VISION_KEY_MAP.containsKey(visionType))
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
     * @param event 触发反应的事件
     * @return 返回反应后的伤害数字
     */
    public static double visionReaction(EntityTrigVisionReactEvent event) {
        Entity entity = event.getEntity();
        Double damage = event.getDamage();
        VisionAttack attack = event.getVisionAttack();
        PersistentDataContainer dataContainer = entity.getPersistentDataContainer();
        PersistentDataContainer visionContainer = dataContainer.get(NamespacedKeyUtil.VISION_KEY, PersistentDataType.TAG_CONTAINER);
        if (visionContainer == null) {
            VisionAttachmentEvent.buildEvent(attack, entity, VisionAttachmentEvent.AttachCause.ENTITY_ATTACK).call();
            return damage;
        }

        for (VisionType type : NamespacedKeyUtil.VISION_KEY_MAP.keySet()) {
            NamespacedKey key = NamespacedKeyUtil.getVisionKey(type);
            PersistentDataContainer vision = visionContainer.get(key, PersistentDataType.TAG_CONTAINER);
            if (vision == null)
                continue;
            VisionType reactedVision = VisionType.valueOf(key.getKey().toUpperCase(Locale.ROOT));
            if (reactedVision.equals(attack.getType())) {
                VisionAttachmentEvent.buildEvent(attack, entity, VisionAttachmentEvent.AttachCause.ENTITY_ATTACK).call();
            } else {
                VisionReactFunction function = visionReactFuncMap.get(attack.getType()).get(reactedVision);
                if (function == null) {
                    VisionAttachmentEvent.buildEvent(attack, entity, VisionAttachmentEvent.AttachCause.VISION_REACT).call();
                } else {
                    damage = function.execute(entity, event.getDamager(), reactedVision, attack);
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
        VisionReactFunction fireReactFunc = (entity, trigger, visionType, visionAttack) -> {
            FireReactEvent.buildEvent(entity, trigger, visionType, visionAttack).call();
            return visionAttack.getDamage();
        };
        pyroMap.put(VisionType.DENDRO, fireReactFunc);
        pyroMap.put(VisionType.SURGE, fireReactFunc);
        VisionReactFunction overloadFunc = (entity, trigger, visionType, visionAttack) -> {
            OverloadEvent.buildEvent(entity, trigger, visionType, visionAttack).call();
            return visionAttack.getDamage();
        };
        pyroMap.put(VisionType.ELECTRO, overloadFunc);
        VisionReactFunction evaporationFunc = (entity, trigger, visionType, visionAttack) -> {
            EvaporationEvent event = EvaporationEvent.buildEvent(entity, trigger, visionType, visionAttack).call();
            return event.getDamage();
        };
        pyroMap.put(VisionType.HYDRO, evaporationFunc);
        VisionReactFunction meltReactFunc = (entity, trigger, visionType, visionAttack) -> {
            MeltReactEvent event = MeltReactEvent.buildEvent(entity, trigger, visionType, visionAttack).call();
            return event.getDamage();
        };
        pyroMap.put(VisionType.CRYO, meltReactFunc);
        pyroMap.put(VisionType.ICE, meltReactFunc);
        VisionReactFunction diffusionFunc = (entity, trigger, visionType, visionAttack) -> {
            DiffusionEvent.buildEvent(entity, trigger, visionType, visionAttack).call();
            return visionAttack.getDamage();
        };
        pyroMap.put(VisionType.ANEMO, diffusionFunc);
        VisionReactFunction crystallizedFunc = (entity, trigger, visionType, visionAttack) -> {
            CrystallizedEvent.buildEvent(entity, trigger, visionType, visionAttack).call();
            return visionAttack.getDamage();
        };
        pyroMap.put(VisionType.GEO, crystallizedFunc);
        visionReactFuncMap.put(VisionType.PYRO, pyroMap);

        Map<VisionType, VisionReactFunction> hydroMap = new HashMap<>();
        hydroMap.put(VisionType.PYRO, evaporationFunc);
        VisionReactFunction bloomFunc = (entity, trigger, visionType, visionAttack) -> {
            BloomReactEvent.buildEvent(entity, trigger, visionType, visionAttack).call();
            return visionAttack.getDamage();
        };
        hydroMap.put(VisionType.DENDRO, bloomFunc);
        visionReactFuncMap.put(VisionType.HYDRO, hydroMap);

        Map<VisionType, VisionReactFunction> electroMap = new HashMap<>();
        electroMap.put(VisionType.PYRO, overloadFunc);
        visionReactFuncMap.put(VisionType.ELECTRO, electroMap);

        Map<VisionType, VisionReactFunction> cryoMap = new HashMap<>();
        cryoMap.put(VisionType.PYRO, meltReactFunc);
        visionReactFuncMap.put(VisionType.CRYO, cryoMap);

        Map<VisionType, VisionReactFunction> dendroMap = new HashMap<>();
        dendroMap.put(VisionType.HYDRO, bloomFunc);
        visionReactFuncMap.put(VisionType.DENDRO, dendroMap);
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
