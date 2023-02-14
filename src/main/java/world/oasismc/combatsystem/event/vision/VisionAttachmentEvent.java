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
    private static final List<VisionType> attachableVisionList = List.of(DENDRO, ELECTRO, HYDRO, CRYO, PYRO);



}
