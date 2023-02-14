package world.oasismc.combatsystem.cmd.sub;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import world.oasismc.combatsystem.cmd.AbstractSubCommand;
import world.oasismc.combatsystem.cmd.ISubCommand;
import world.oasismc.combatsystem.util.LangUtil;
import world.oasismc.combatsystem.util.MapUtil;
import world.oasismc.combatsystem.vision.VisionManager;
import world.oasismc.combatsystem.vision.VisionType;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SetAttackVisionCmd extends AbstractSubCommand {

    public static final SetAttackVisionCmd INSTANCE = new SetAttackVisionCmd();

    private SetAttackVisionCmd() {
        super("set-attack-vision", null);
    }

    @Override
    public boolean onCommand(CommandSender sender, List<String> args) {
        if (!(sender instanceof Entity entity)) {
            LangUtil.sendMsg(sender, "message.command.player_only");
            return true;
        }
        if (args.size() < 1) {
            LangUtil.sendMsg(sender, "message.command.not_enough_param");
            return true;
        }
        VisionType type = VisionType.valueOf(args.get(0).toUpperCase(Locale.ROOT));
        double num = 1.0;
        if (args.size() >= 2) {
            try {
                num = Double.parseDouble(args.get(1));
            } catch (Exception e) {
                LangUtil.sendMsg(sender, "message.command.set-attack-vision.not_double");
            }
        }
        VisionManager.setAttackVision(entity, type, num);
        Map<String, String> replaceMap = MapUtil.newHashMap("<vision>", type.getIcon() + type.name(), "<num>", String.valueOf(num));
        LangUtil.sendMsg(sender, "message.command.set-attack-vision.success", replaceMap);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, List<String> args) {
        return List.of("pyro", "dendro", "hydro", "cryo", "electro", "geo", "anemo");
    }
}
