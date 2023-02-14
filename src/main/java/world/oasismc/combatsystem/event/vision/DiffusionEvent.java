package world.oasismc.combatsystem.event.vision;

import org.bukkit.entity.Entity;
import world.oasismc.combatsystem.OasisCombatSystem;
import world.oasismc.combatsystem.vision.VisionAttack;
import world.oasismc.combatsystem.vision.VisionType;

public class DiffusionEvent extends VisionReactEvent {

    private double radius, damage;
    /**
     * 扩散反应
     * @param entity            触发的实体，即发生反应的实体
     * @param trigger           触发者，大部分情况是攻击者，也可能和entity相同
     * @param reactedVision     被反应的元素，通常为反应实体身上的元素
     * @param reactVisionAttack 触发反应的元素攻击
     */
    protected DiffusionEvent(Entity entity, Entity trigger, VisionType reactedVision, VisionAttack reactVisionAttack) {
        super(entity, trigger, reactedVision, reactVisionAttack);
        double radiusScale = OasisCombatSystem.getInstance().getConfig().getDouble("diffusion_radius_scale", 1.0);
        double damageScale = OasisCombatSystem.getInstance().getConfig().getDouble("diffusion_damage_scale", 2.0);
        this.radius = getReactedVisionNum() * radiusScale;
        this.damage = getReactedVisionNum() * damageScale;
    }

    public static DiffusionEvent buildEvent(Entity entity, Entity trigger, VisionType reactedVision, VisionAttack reactVisionAttack) {
        return new DiffusionEvent(entity, trigger, reactedVision, reactVisionAttack);
    }

    @Override
    public DiffusionEvent call() {
        return (DiffusionEvent) super.call();
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

}
