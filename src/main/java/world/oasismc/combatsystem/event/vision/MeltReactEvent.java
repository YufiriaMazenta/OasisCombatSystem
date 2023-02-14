package world.oasismc.combatsystem.event.vision;

import org.bukkit.entity.Entity;
import world.oasismc.combatsystem.OasisCombatSystem;
import world.oasismc.combatsystem.vision.VisionAttack;
import world.oasismc.combatsystem.vision.VisionType;

public class MeltReactEvent extends VisionReactEvent {

    private double damage;

    protected MeltReactEvent(Entity entity, Entity trigger, VisionType reactedVision, VisionAttack reactAttack) {
        super(entity, trigger, reactedVision, reactAttack);
        double damageScale;
        if (reactAttack.getType() == VisionType.PYRO)
            damageScale = OasisCombatSystem.getInstance().getConfig().getDouble("melt_damage_scale.pyro_trigger", 2.0);
        else
            damageScale = OasisCombatSystem.getInstance().getConfig().getDouble("melt_damage_scale.cryo_trigger", 1.5);
        damage = reactAttack.getDamage() * damageScale;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public static MeltReactEvent buildEvent(Entity entity, Entity trigger, VisionType reactedVision, VisionAttack reactAttack) {
        return new MeltReactEvent(entity, trigger, reactedVision, reactAttack);
    }

    @Override
    public MeltReactEvent call() {
        return (MeltReactEvent) super.call();
    }
}
