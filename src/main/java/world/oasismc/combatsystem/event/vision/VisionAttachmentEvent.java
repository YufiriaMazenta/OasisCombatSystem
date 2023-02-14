package world.oasismc.combatsystem.event.vision;

import org.bukkit.entity.Entity;
import world.oasismc.combatsystem.event.BaseCustomEvent;
import world.oasismc.combatsystem.vision.VisionAttack;
import world.oasismc.combatsystem.vision.VisionType;

import java.util.List;

import static world.oasismc.combatsystem.vision.VisionType.*;

public class VisionAttachmentEvent extends BaseCustomEvent {

    private VisionAttack attachAttack;
    private Entity attachedEntity;
    private AttachCause cause;
    private static final List<VisionType> attachableVisionList = List.of(DENDRO, ELECTRO, HYDRO, CRYO, PYRO);

    protected VisionAttachmentEvent(VisionAttack attack, Entity attachedEntity, AttachCause cause) {
        this.attachAttack = attack;
        this.attachedEntity = attachedEntity;
        this.cause = cause;
    }

    public VisionAttack getAttachAttack() {
        return attachAttack;
    }

    public void setAttachAttack(VisionAttack attachAttack) {
        this.attachAttack = attachAttack;
    }

    public Entity getAttachedEntity() {
        return attachedEntity;
    }

    public void setAttachedEntity(Entity attachedEntity) {
        this.attachedEntity = attachedEntity;
    }

    public AttachCause getCause() {
        return cause;
    }

    public void setCause(AttachCause cause) {
        this.cause = cause;
    }

    public enum AttachCause {
        ENTITY_ATTACK,
        ENVIRONMENT,
        VISION_REACT;
    }

    /**
     * 构建一个新的元素附着事件对象
     * @param attack 造成元素附着的攻击
     * @param attachedEntity 被附着的实体
     * @param cause 造成附着的原因
     */
    public static VisionAttachmentEvent buildEvent(VisionAttack attack, Entity attachedEntity, AttachCause cause) {
        return new VisionAttachmentEvent(attack, attachedEntity, cause);
    }

    /**
     * 构建一个原因为实体攻击的元素附着事件对象
     * @param attack 造成元素附着的攻击
     * @param attachedEntity 被附着的实体
     */
    public static VisionAttachmentEvent buildEvent(VisionAttack attack, Entity attachedEntity) {
        return buildEvent(attack, attachedEntity, AttachCause.ENTITY_ATTACK);
    }

    @Override
    public VisionAttachmentEvent call() {
        return (VisionAttachmentEvent) super.call();
    }
}
