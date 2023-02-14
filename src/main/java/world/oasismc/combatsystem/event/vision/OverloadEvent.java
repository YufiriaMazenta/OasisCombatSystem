package world.oasismc.combatsystem.event.vision;

import org.bukkit.entity.Entity;
import world.oasismc.combatsystem.vision.VisionAttack;
import world.oasismc.combatsystem.vision.VisionType;

public class OverloadEvent extends VisionReactEvent {

    private Entity trigger;
    private Entity entity;

    /**
     * 新建一个超载事件
     * @param entity 触发的实体，即发生反应的实体
     * @param trigger 触发者，大部分情况是攻击者，也可能和entity相同
     */
    public OverloadEvent(Entity entity, Entity trigger, VisionType reactedVision, VisionAttack reactVisionAttack) {
        this.entity = entity;
        this.trigger = trigger;
        this.setReactedVision(reactedVision);
        this.setReactVisionAttack(reactVisionAttack);
    }

    public Entity getTrigger() {
        return trigger;
    }

    public void setTrigger(Entity trigger) {
        this.trigger = trigger;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public static OverloadEvent buildEvent(Entity entity , Entity trigger, VisionType reactedVision, VisionAttack reactVisionAttack) {
        return new OverloadEvent(entity, trigger, reactedVision, reactVisionAttack);
    }

}
