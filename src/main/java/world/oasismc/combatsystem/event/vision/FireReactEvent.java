package world.oasismc.combatsystem.event.vision;

import org.bukkit.entity.Entity;
import world.oasismc.combatsystem.OasisCombatSystem;
import world.oasismc.combatsystem.vision.VisionAttack;
import world.oasismc.combatsystem.vision.VisionType;

/**
 * 燃烧反应事件
 */
public class FireReactEvent extends VisionReactEvent {

    private int fireTick;

    protected FireReactEvent(Entity entity, Entity trigger, VisionType reactedVision, VisionAttack reactAttack) {
        super(entity, trigger, reactedVision, reactAttack);
        double scale = OasisCombatSystem.getInstance().getConfig().getDouble("fire_tick_scale", 100d);
        this.fireTick = (int) (getReactedVisionNum() * scale);
    }

    /**
     * 构造一个燃烧反应对象
     * @param entity 发生反应的实体
     * @param trigger 触发反应的实体
     * @param reactedVision 被反应的元素
     * @param reactAttack 触发反应的元素攻击
     */
    public static FireReactEvent buildEvent(Entity entity, Entity trigger, VisionType reactedVision, VisionAttack reactAttack) {
        return new FireReactEvent(entity, trigger, reactedVision, reactAttack);
    }

    public int getFireTick() {
        return fireTick;
    }

    public void setFireTick(int fireTick) {
        this.fireTick = fireTick;
    }

}
