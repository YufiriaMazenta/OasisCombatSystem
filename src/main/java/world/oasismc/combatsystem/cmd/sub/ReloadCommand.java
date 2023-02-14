package world.oasismc.combatsystem.cmd.sub;

import org.bukkit.command.CommandSender;
import world.oasismc.combatsystem.OasisCombatSystem;
import world.oasismc.combatsystem.cmd.AbstractSubCommand;
import world.oasismc.combatsystem.util.LangUtil;

import java.util.List;

public class ReloadCommand extends AbstractSubCommand {

    public static final ReloadCommand INSTANCE = new ReloadCommand();

    private ReloadCommand() {
        super("reload", null);
    }

    @Override
    public boolean onCommand(CommandSender sender, List<String> args) {
        reloadPlugin();
        LangUtil.sendMsg(sender, "message.command.reload.success");
        return super.onCommand(sender, args);
    }

    public void reloadPlugin() {
        OasisCombatSystem.getInstance().reloadConfig();
        LangUtil.reloadLangFile();
    }

}
