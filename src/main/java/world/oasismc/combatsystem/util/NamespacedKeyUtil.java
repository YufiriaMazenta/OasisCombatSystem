package world.oasismc.combatsystem.util;

import org.bukkit.NamespacedKey;
import world.oasismc.combatsystem.OasisCombatSystem;
import world.oasismc.combatsystem.vision.VisionType;

import java.util.ArrayList;
import java.util.List;

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
    public static final List<NamespacedKey> VISION_KEY_LIST;

    static {
        VISION_KEY_LIST = new ArrayList<>();
        VISION_KEY_LIST.add(FIRE_KEY);
        VISION_KEY_LIST.add(PYRO_KEY);
        VISION_KEY_LIST.add(HYDRO_KEY);
        VISION_KEY_LIST.add(ICE_KEY);
        VISION_KEY_LIST.add(CRYO_KEY);
        VISION_KEY_LIST.add(SURGE_KEY);
        VISION_KEY_LIST.add(ELECTRO_KEY);
        VISION_KEY_LIST.add(DENDRO_KEY);
        VISION_KEY_LIST.add(ANEMO_KEY);
        VISION_KEY_LIST.add(GEO_KEY);
    }

    public static NamespacedKey newKey(String keyStr) {
        return new NamespacedKey(OasisCombatSystem.getInstance(), keyStr);
    }

}
