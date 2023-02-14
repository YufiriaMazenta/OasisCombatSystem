package world.oasismc.combatsystem.event.vision;

import org.bukkit.entity.Entity;
import world.oasismc.combatsystem.OasisCombatSystem;
import world.oasismc.combatsystem.vision.VisionAttack;
import world.oasismc.combatsystem.vision.VisionType;

public class EvaporationEvent extends VisionReactEvent {

    private double damage;

    protected EvaporationEvent(Entity entity, Entity trigger, VisionType reactedVision, VisionAttack reactAttack) {
        super(entity, trigger, reactedVision, reactAttack);
        double damageScale;
        if (reactAttack.getType() == VisionType.PYRO)
            damageScale = OasisCombatSystem.getInstance().getConfig().getDouble("evaporation_damage_scale.pyro_trigger", 1.5);
        else
            damageScale = OasisCombatSystem.getInstance().getConfig().getDouble("evaporation_damage_scale.hydro_trigger", 2.0);
        this.damage = reactAttack.getDamage() * damageScale;
    }

    public static EvaporationEvent buildEvent(Entity entity, Entity trigger, VisionType reactedVision, VisionAttack reactAttack) {
        return new EvaporationEvent(entity, trigger, reactedVision, reactAttack);
    }

    @Override
    public EvaporationEvent call() {
        return (EvaporationEvent) super.call();
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

}
