package world.oasismc.combatsystem.event.vision;

import org.bukkit.entity.Entity;
import world.oasismc.combatsystem.event.BaseCustomEvent;
import world.oasismc.combatsystem.vision.VisionAttack;
import world.oasismc.combatsystem.vision.VisionType;

public class BloomReactEvent extends VisionReactEvent {

    /**
     * 绽放反应
     * @param entity            触发的实体，即发生反应的实体
     * @param trigger           触发者，大部分情况是攻击者，也可能和entity相同
     * @param reactedVision     被反应的元素，通常为反应实体身上的元素
     * @param reactVisionAttack 触发反应的元素攻击
     */
    protected BloomReactEvent(Entity entity, Entity trigger, VisionType reactedVision, VisionAttack reactVisionAttack) {
        super(entity, trigger, reactedVision, reactVisionAttack);
    }

    public static BloomReactEvent buildEvent(Entity entity, Entity trigger, VisionType reactedVision, VisionAttack reactVisionAttack) {
        return new BloomReactEvent(entity, trigger, reactedVision, reactVisionAttack);
    }

    @Override
    public BloomReactEvent call() {
        return (BloomReactEvent) super.call();
    }
}
