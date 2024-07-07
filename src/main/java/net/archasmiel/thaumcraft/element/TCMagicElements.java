package net.archasmiel.thaumcraft.element;


import net.archasmiel.thaumcraft.Thaumcraft;
import net.archasmiel.thaumcraft.core.element.ElementsRegistry;
import net.archasmiel.thaumcraft.core.element.MagicElement;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TCMagicElements {
    public static final DeferredRegister<MagicElement> ELEMENTS = DeferredRegister.create(ElementsRegistry.REGISTRY_ELEMENTS, Thaumcraft.MODID);

    // 风
    public static final MagicElement AIR = register("aer", new MagicElement("aer", 16777086, 1));
    // 地
    public static final MagicElement EARTH = register("terra", new MagicElement("terra", 5685248, 1));
    // 火
    public static final MagicElement FIRE = register("ignis", new MagicElement("ignis", 16734721, 1));
    // 水
    public static final MagicElement WATER = register("aqua", new MagicElement("aqua", 3986684, 1));
    // 秩序
    public static final MagicElement ORDER = register("ordo", new MagicElement("ordo", 14013676, 1));
    // 混沌
    public static final MagicElement ENTROPY = register("perditio", new MagicElement("perditio", 4210752, 771));
    // 虚空
    public static final MagicElement VOID;
    // 光明
    public static final MagicElement LIGHT;
    // 气候
    public static final MagicElement WEATHER;
    // 移动
    public static final MagicElement MOTION;
    // 寒冷
    public static final MagicElement COLD;
    // 水晶
    public static final MagicElement CRYSTAL;
    // 生命
    public static final MagicElement LIFE;
    // 毒药
    public static final MagicElement POISON;
    // 能量
    public static final MagicElement ENERGY;
    // 交换
    public static final MagicElement EXCHANGE;
    // 金属
    public static final MagicElement METAL;
    // 死亡
    public static final MagicElement DEATH;
    // 飞行
    public static final MagicElement FLIGHT;
    // 黑暗
    public static final MagicElement DARKNESS;
    // 灵魂
    public static final MagicElement SOUL;
    // 治疗
    public static final MagicElement HEAL;
    // 旅行
    public static final MagicElement TRAVEL;
    // 异域
    public static final MagicElement ELDRITCH;
    // 魔力
    public static final MagicElement MAGIC;
    // 灵气
    public static final MagicElement AURA;
    // 污染
    public static final MagicElement TAINT;
    // 史莱姆
    public static final MagicElement SLIME;
    // 植物
    public static final MagicElement PLANT;
    // 树木
    public static final MagicElement TREE;
    // 野兽
    public static final MagicElement BEAST;
    // 肉体
    public static final MagicElement FLESH;
    // 不死
    public static final MagicElement UNDEAD;
    // 思维
    public static final MagicElement MIND;
    // 感官
    public static final MagicElement SENSES;
    // 人类
    public static final MagicElement MAN;
    // 作物
    public static final MagicElement CROP;
    // 矿藏
    public static final MagicElement MINE;
    // 工具
    public static final MagicElement TOOL;
    // 收获
    public static final MagicElement HARVEST;
    // 武器
    public static final MagicElement WEAPON;
    // 防护
    public static final MagicElement ARMOR;
    // 饥饿
    public static final MagicElement HUNGER;
    // 贪婪
    public static final MagicElement GREED;
    // 合成
    public static final MagicElement CRAFT;
    // 布匹
    public static final MagicElement CLOTH;
    // 机械
    public static final MagicElement MECHANISM;
    // 陷阱
    public static final MagicElement TRAP;
    public static final MagicElement[] DEFAULT_ELEMENTS = new MagicElement[]{AIR, EARTH, FIRE, WATER, ORDER, ENTROPY};

    static {
        VOID = register("vacuos", 8947848, 771, AIR, ENTROPY);
        LIGHT = register("lux", 16774755, AIR, FIRE);
        WEATHER = register("tempestas", 16777215, AIR, WATER);
        MOTION = register("motus", 13487348, AIR, ORDER);
        COLD = register("gelum", 14811135, FIRE, ENTROPY);
        CRYSTAL = register("vitreus", 8454143, EARTH, ORDER);
        LIFE = register("victus", 14548997, WATER, EARTH);
        POISON = register("venenum", 9039872, WATER, ENTROPY);
        ENERGY = register("potentia", 12648447, ORDER, FIRE);
        EXCHANGE = register("permutatio", 5735255, ENTROPY, ORDER);
        METAL = register("metallum", 11908557, EARTH, CRYSTAL);
        DEATH = register("mortuus", 8943496, LIFE, ENTROPY);
        FLIGHT = register("volatus", 15198167, AIR, MOTION);
        DARKNESS = register("tenebrae", 2236962, VOID, LIGHT);
        SOUL = register("spiritus", 15461371, LIFE, DEATH);
        HEAL = register("sano", 16723764, LIFE, ORDER);
        TRAVEL = register("iter", 14702683, MOTION, EARTH);
        ELDRITCH = register("alienis", 8409216, VOID, DARKNESS);
        MAGIC = register("praecantatio", 9896128, VOID, ENERGY);
        AURA = register("auram", 16761087, MAGIC, AIR);
        TAINT = register("vitium", 8388736, MAGIC, ENTROPY);
        SLIME = register("limus", 129024, LIFE, WATER);
        PLANT = register("herba", 109568, LIFE, EARTH);
        TREE = register("arbor", 8873265, AIR, PLANT);
        BEAST = register("bestia", 10445833, MOTION, LIFE);
        FLESH = register("corpus", 15615885, DEATH, BEAST);
        UNDEAD = register("exanimis", 3817472, MOTION, DEATH);
        MIND = register("cognitio", 16761523, FIRE, SOUL);
        SENSES = register("sensus", 1038847, AIR, SOUL);
        MAN = register("humanus", 16766912, BEAST, MIND);
        CROP = register("messis", 14791537, PLANT, MAN);
        MINE = register("perfodio", 14471896, MAN, EARTH);
        TOOL = register("instrumentum", 4210926, MAN, ORDER);
        HARVEST = register("meto", 15641986, CROP, TOOL);
        WEAPON = register("telum", 12603472, TOOL, FIRE);
        ARMOR = register("tutamen", 49344, TOOL, EARTH);
        HUNGER = register("fames", 10093317, LIFE, VOID);
        GREED = register("lucrum", 15121988, MAN, HUNGER);
        CRAFT = register("fabrico", 8428928, MAN, TOOL);
        CLOTH = register("pannus", 15395522, TOOL, BEAST);
        MECHANISM = register("machina", 8421536, MOTION, TOOL);
        TRAP = register("vinculum", 10125440, MOTION, ENTROPY);
    }

    public static MagicElement register(String name, int color, MagicElement... components) {
        MagicElement element = new MagicElement(name, color, components);
        ELEMENTS.register(name, () -> element);
        return element;
    }

    public static MagicElement register(String name, int color, int blend, MagicElement... components) {
        MagicElement element = new MagicElement(name, color, blend, components);
        ELEMENTS.register(name, () -> element);
        return element;
    }

    public static MagicElement register(String name, MagicElement element) {
        ELEMENTS.register(name, () -> element);
        return element;
    }

    public static void registerElements() {
    }
}
