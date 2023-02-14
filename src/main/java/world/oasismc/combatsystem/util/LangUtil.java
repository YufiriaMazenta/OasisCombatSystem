package world.oasismc.combatsystem.util;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import world.oasismc.combatsystem.OasisCombatSystem;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

public class LangUtil {

    private static final Pattern colorPattern;
    private static final YamlFileWrapper langFile;
    private static final Map<String, String> defaultFormatMap;

    static {
        colorPattern = Pattern.compile("&#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})");
        langFile = new YamlFileWrapper("lang.yml");
        defaultFormatMap = new HashMap<>();
        defaultFormatMap.put("<prefix>", langFile.getConfig().getString("prefix", "&8[&3Oasis&bRecipe&8]"));
        defaultFormatMap.put("<version>", OasisCombatSystem.getInstance().getDescription().getVersion());
    }

    public static String color(String text) {
        StringBuilder strBuilder = new StringBuilder(text);
        Matcher matcher = colorPattern.matcher(strBuilder);
        while (matcher.find()) {
            String colorCode = matcher.group();
            String colorStr = ChatColor.of(colorCode.substring(1)).toString();
            strBuilder.replace(matcher.start(), matcher.start() + colorCode.length(), colorStr);
            matcher = colorPattern.matcher(strBuilder);
        }
        text = strBuilder.toString();
        return translateAlternateColorCodes('&', text);
    }

    public static String lang(String key) {
        return lang(key, key);
    }

    public static String lang(String key, String def) {
        return langFile.getConfig().getString(key, def);
    }

    public static void sendMsg(CommandSender sender, String msgKey) {
        sendMsg(sender, msgKey, new HashMap<>());
    }

    public static void sendMsg(CommandSender sender, String msgKey, Map<String, String> formatMap) {
        if (sender == null) {
            return;
        }
        formatMap.putAll(defaultFormatMap);
        String message = lang(msgKey);
        for (String formatStr : formatMap.keySet()) {
            message = message.replace(formatStr, formatMap.get(formatStr));
        }
//        if (sender instanceof Player)
//            if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null)
//                message = PlaceholderAPI.setPlaceholders((Player) sender, message);
        sender.sendMessage(color(message));
    }

    public static void info(String msgKey) {
        sendMsg(Bukkit.getConsoleSender(), msgKey);
    }

    public static void reloadLangFile() {
        langFile.reloadConfig();
    }

    public static void info(String msgKey, Map<String, String> map) {
        sendMsg(Bukkit.getConsoleSender(), msgKey, map);
    }

}
