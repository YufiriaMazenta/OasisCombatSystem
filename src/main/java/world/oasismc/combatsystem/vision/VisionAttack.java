package world.oasismc.combatsystem.vision;

/**
 * 元素攻击，来源可以是实体，也可以是方块
 */
public class VisionAttack {

    private VisionType type;
    private Double visionNum;
    private Double damage;

    public VisionAttack(VisionType type, Double visionNum, Double damage) {
        this.type = type;
        this.visionNum = visionNum;
        this.damage = damage;
    }

    public Double getVisionNum() {
        return visionNum;
    }

    public void setVisionNum(Double visionNum) {
        this.visionNum = visionNum;
    }

    public VisionType getType() {
        return type;
    }

    public void setType(VisionType type) {
        this.type = type;
    }

    public Double getDamage() {
        return damage;
    }

    public void setDamage(Double damage) {
        this.damage = damage;
    }

}
