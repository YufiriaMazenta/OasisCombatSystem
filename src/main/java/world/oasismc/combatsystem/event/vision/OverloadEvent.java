package world.oasismc.combatsystem.event.vision;

import org.bukkit.entity.Entity;
import world.oasismc.combatsystem.OasisCombatSystem;
import world.oasismc.combatsystem.vision.VisionAttack;
import world.oasismc.combatsystem.vision.VisionType;

public class OverloadEvent extends VisionReactEvent {

    private Float explosionPower;

    protected OverloadEvent(Entity entity, Entity trigger, VisionType reactedVision, VisionAttack reactVisionAttack) {
        super(entity, trigger, reactedVision, reactVisionAttack);
        double explosionPowerScale = OasisCombatSystem.getInstance().getConfig().getDouble("overload_explosion_power_scale", 0.5);
        this.explosionPower = (float) (getReactedVisionNum() * explosionPowerScale);
    }

    public static OverloadEvent buildEvent(Entity entity , Entity trigger, VisionType reactedVision, VisionAttack reactVisionAttack) {
        return new OverloadEvent(entity, trigger, reactedVision, reactVisionAttack);
    }

    public Float getExplosionPower() {
        return explosionPower;
    }

    public void setExplosionPower(Float explosionPower) {
        this.explosionPower = explosionPower;
    }

}
