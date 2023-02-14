package world.oasismc.combatsystem.vision.function;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import world.oasismc.combatsystem.vision.VisionAttack;
import world.oasismc.combatsystem.vision.VisionManager;
import world.oasismc.combatsystem.vision.VisionType;

/**
 * 燃烧反应
 */
public enum FireFunction implements VisionReactFunc {

    INSTANCE;

    @Override
    public Double execute(Entity entity, VisionAttack attack, Double damage) {
        VisionType reactedVisionType = attack.getType() == VisionType.DENDRO ? VisionType.PYRO : VisionType.DENDRO;
        double reactedNum = VisionManager.getVisionNum(entity, reactedVisionType);
        double reactNum = VisionManager.getVisionReactNumMap().get(attack.getType()).get(reactedVisionType);
        reactNum *= attack.getVisionNum();
        double actualReactNum = Math.min(reactedNum, reactNum);
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.setFireTicks((int) (actualReactNum * 100));
        }
        return damage;
    }

}
