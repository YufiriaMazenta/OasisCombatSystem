package world.oasismc.combatsystem.function.vision;

import org.bukkit.entity.Entity;
import world.oasismc.combatsystem.function.Function4;
import world.oasismc.combatsystem.vision.VisionAttack;
import world.oasismc.combatsystem.vision.VisionType;

public interface VisionReactFunction extends Function4<Entity, Entity, VisionType, VisionAttack, Double> {

    @Override
    Double execute(Entity entity, Entity trigger, VisionType visionType, VisionAttack visionAttack);

}
