package world.oasismc.combatsystem.event.vision;

import org.bukkit.entity.Entity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.jetbrains.annotations.NotNull;
import world.oasismc.combatsystem.event.BaseCustomEvent;
import world.oasismc.combatsystem.vision.VisionAttack;

public class EntityTrigVisionReactEvent extends BaseCustomEvent {

    private static final HandlerList handlerList = new HandlerList();
    private final Entity damager;
    private final Entity entity;
    private double damage;
    private final VisionAttack visionAttack;

    public EntityTrigVisionReactEvent(@NotNull Entity entity, EntityDamageByEntityEvent event, VisionAttack visionAttack) {
        this.entity = entity;
        this.damager = event.getDamager();
        this.visionAttack = visionAttack;
        this.damage = event.getDamage();
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public Entity getDamager() {
        return damager;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public VisionAttack getVisionAttack() {
        return visionAttack;
    }

    public Entity getEntity() {
        return entity;
    }

    public static EntityTrigVisionReactEvent buildEvent(Entity entity, EntityDamageByEntityEvent damageByEntityEvent,  VisionAttack visionAttack) {
        return new EntityTrigVisionReactEvent(entity, damageByEntityEvent, visionAttack);
    }

    @Override
    public EntityTrigVisionReactEvent call() {
        return (EntityTrigVisionReactEvent) super.call();
    }
}
