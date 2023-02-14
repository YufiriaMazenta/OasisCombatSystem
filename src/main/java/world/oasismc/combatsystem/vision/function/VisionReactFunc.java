package world.oasismc.combatsystem.vision.function;

import org.bukkit.entity.Entity;
import world.oasismc.combatsystem.function.Function3;
import world.oasismc.combatsystem.vision.VisionAttack;

/**
 * 基础元素反应方法接口
 */
public interface VisionReactFunc extends Function3<Entity, VisionAttack, Double, Double> {}
