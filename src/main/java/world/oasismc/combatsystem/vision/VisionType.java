package world.oasismc.combatsystem.vision;

import world.oasismc.combatsystem.util.LangUtil;

public enum VisionType {

    DENDRO(LangUtil.color(LangUtil.lang("vision_icon.dendro", "&2❦"))),//草元素
    ELECTRO(LangUtil.color(LangUtil.lang("vision_icon.electro", "&5⚡"))),//雷元素
    HYDRO(LangUtil.color(LangUtil.lang("vision_icon.hydro", "&3㊌"))),//水元素
    CRYO(LangUtil.color(LangUtil.lang("vision_icon.cryo", "&b❄"))),//冰元素
    ANEMO(LangUtil.color(LangUtil.lang("vision_icon.anemo", "&a∰"))),//风元素
    PYRO(LangUtil.color(LangUtil.lang("vision_icon.pyro", "&c\uD83D\uDD25"))),//火元素
    GEO(LangUtil.color(LangUtil.lang("vision_icon.geo", "&6❖"))),//岩元素
    FIRE(""),//燃元素
    ICE(""),//冻元素
    SURGE("");//激元素

    private final String icon;

    VisionType(String icon) {
        this.icon = icon;
    }

    public String getIcon() { return icon; }

}
