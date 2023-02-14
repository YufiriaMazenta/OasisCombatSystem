package world.oasismc.combatsystem.event.vision;

import world.oasismc.combatsystem.event.BaseCustomEvent;
import world.oasismc.combatsystem.vision.VisionAttack;
import world.oasismc.combatsystem.vision.VisionType;

public class VisionReactEvent extends BaseCustomEvent {
    
    private VisionType reactedVision;
    private VisionAttack reactVisionAttack;

    /**
     * 基础元素反应事件类
     * @param reactedVision 被反应的元素，通常为反应实体身上的元素
     * @param reactVisionAttack 触发反应的元素攻击
     */
    protected VisionReactEvent(VisionType reactedVision, VisionAttack reactVisionAttack) {
        this.reactedVision = reactedVision;
        this.reactVisionAttack = reactVisionAttack;
    }

    protected VisionReactEvent() {}

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
    
}
