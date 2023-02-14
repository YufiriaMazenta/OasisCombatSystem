package world.oasismc.combatsystem.event.vision;

import org.bukkit.entity.Entity;
import world.oasismc.combatsystem.event.BaseCustomEvent;
import world.oasismc.combatsystem.vision.VisionAttack;
import world.oasismc.combatsystem.vision.VisionManager;
import world.oasismc.combatsystem.vision.VisionType;

public class VisionReactEvent extends BaseCustomEvent {
    
    private VisionType reactedVision;
    private VisionAttack reactVisionAttack;
    private Entity entity, trigger;
    /**
     * 反应消耗的元素量
     */
    private double reactedVisionNum;

    /**
     * 元素反应事件
     * @param entity 触发的实体，即发生反应的实体
     * @param trigger 触发者，大部分情况是攻击者，也可能和entity相同
     * @param reactedVision 被反应的元素，通常为反应实体身上的元素
     * @param reactVisionAttack 触发反应的元素攻击
     */
    protected VisionReactEvent(Entity entity, Entity trigger, VisionType reactedVision, VisionAttack reactVisionAttack) {
        this.reactedVision = reactedVision;
        this.reactVisionAttack = reactVisionAttack;
        this.entity = entity;
        this.trigger = trigger;
        double reactScale = VisionManager.getVisionReactNumMap().get(reactVisionAttack.getType()).get(reactedVision);
        reactedVisionNum = Math.min(VisionManager.getVisionNum(entity, reactedVision), reactVisionAttack.getVisionNum() * reactScale);
    }

    public VisionType getReactedVision() {
        return reactedVision;
    }

    public VisionAttack getReactVisionAttack() {
        return reactVisionAttack;
    }

    public void setReactedVision(VisionType reactedVision) {
        this.reactedVision = reactedVision;
    }

    public void setReactVisionAttack(VisionAttack reactVisionAttack) {
        this.reactVisionAttack = reactVisionAttack;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public Entity getTrigger() {
        return trigger;
    }

    public void setTrigger(Entity trigger) {
        this.trigger = trigger;
    }

    /**
     * 获取消耗的元素量，指玩家身上被反应掉多少元素量
     */
    public double getReactedVisionNum() {
        return reactedVisionNum;
    }

    /**
     * 设置消耗的元素量
     * @param reactedVisionNum 反应掉的元素量，值为玩家身上被反应掉的元素量
     */
    public void setReactedVisionNum(double reactedVisionNum) {
        this.reactedVisionNum = reactedVisionNum;
    }
}
