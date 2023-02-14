package world.oasismc.combatsystem.util;

import org.bukkit.NamespacedKey;
import world.oasismc.combatsystem.OasisCombatSystem;
import world.oasismc.combatsystem.vision.VisionType;

import java.util.HashMap;
import java.util.Map;

public class NamespacedKeyUtil {

    public static final NamespacedKey VISION_KEY = newKey("vision");
    public static final NamespacedKey VISION_NUM_KEY = newKey("vision.num");
    public static final NamespacedKey VISION_UPDATE_TIME_KEY = newKey("vision.update_time");
    public static final NamespacedKey VISION_INFINITE_KEY = newKey("vision.infinite");
    public static final NamespacedKey ATTACK_VISION_KEY = newKey("attack_vision");
    public static final NamespacedKey ATTACK_VISION_TYPE_KEY = newKey("attack_vision.type");
    public static final NamespacedKey ATTACK_VISION_NUM_KEY = newKey("attack_vision.num");
    public static final NamespacedKey DENDRO_KEY = newKey(VisionType.DENDRO.name());
    public static final NamespacedKey ELECTRO_KEY = newKey(VisionType.ELECTRO.name());
    public static final NamespacedKey HYDRO_KEY = newKey(VisionType.HYDRO.name());
    public static final NamespacedKey CRYO_KEY = newKey(VisionType.CRYO.name());
    public static final NamespacedKey ANEMO_KEY = newKey(VisionType.ANEMO.name());
    public static final NamespacedKey PYRO_KEY = newKey(VisionType.PYRO.name());
    public static final NamespacedKey GEO_KEY = newKey(VisionType.GEO.name());
    public static final NamespacedKey FIRE_KEY = newKey(VisionType.FIRE.name());
    public static final NamespacedKey ICE_KEY = newKey(VisionType.ICE.name());
    public static final NamespacedKey SURGE_KEY = newKey(VisionType.SURGE.name());
    public static final Map<VisionType, NamespacedKey> VISION_KEY_MAP;

    static {
        VISION_KEY_MAP = new HashMap<>();
        VISION_KEY_MAP.put(VisionType.FIRE, FIRE_KEY);
        VISION_KEY_MAP.put(VisionType.PYRO, PYRO_KEY);
        VISION_KEY_MAP.put(VisionType.HYDRO, HYDRO_KEY);
        VISION_KEY_MAP.put(VisionType.ICE, ICE_KEY);
        VISION_KEY_MAP.put(VisionType.CRYO, CRYO_KEY);
        VISION_KEY_MAP.put(VisionType.SURGE, SURGE_KEY);
        VISION_KEY_MAP.put(VisionType.ELECTRO, ELECTRO_KEY);
        VISION_KEY_MAP.put(VisionType.DENDRO, DENDRO_KEY);
        VISION_KEY_MAP.put(VisionType.ANEMO, ANEMO_KEY);
        VISION_KEY_MAP.put(VisionType.GEO, GEO_KEY);
    }

    public static NamespacedKey newKey(String keyStr) {
        return new NamespacedKey(OasisCombatSystem.getInstance(), keyStr);
    }

    public static NamespacedKey getVisionKey(VisionType visionType) {
        return VISION_KEY_MAP.get(visionType);
    }

}
