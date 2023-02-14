package world.oasismc.combatsystem.vision;

public class VisionAttack {

    private VisionType type;
    private Double visionNum;

    public VisionAttack(VisionType type, Double visionNum) {
        this.type = type;
        this.visionNum = visionNum;
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

}
