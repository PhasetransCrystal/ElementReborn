package net.archasmiel.thaumcraft.core.wands;

import net.archasmiel.thaumcraft.core.element.StorageElements;
import net.archasmiel.thaumcraft.element.TCMagicElements;
import net.archasmiel.thaumcraft.util.IResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;


public class FocusUpgradeType {
    public static FocusUpgradeType[] types = new FocusUpgradeType[20];
    public short id;
    public ResourceLocation icon;
    public String name;
    public String text;
    public StorageElements TCMagicElementss;
    public static FocusUpgradeType potency;
    public static FocusUpgradeType frugal;
    public static FocusUpgradeType treasure;
    public static FocusUpgradeType enlarge;
    public static FocusUpgradeType alchemistsfire;
    public static FocusUpgradeType alchemistsfrost;
    public static FocusUpgradeType architect;
    public static FocusUpgradeType extend;
    public static FocusUpgradeType silktouch;

    public FocusUpgradeType(int id, ResourceLocation icon, String name, String text, StorageElements TCMagicElementss) {
        this.id = (short)id;
        this.icon = icon;
        this.name = name;
        this.text = text;
        this.TCMagicElementss = TCMagicElementss;
        if (id < types.length && types[id] != null) {
            LogManager.getLogger("THAUMCRAFT").fatal("Focus Upgrade id " + id + " already occupied. Ignoring.");
        } else {
            if (id >= types.length) {
                FocusUpgradeType[] temp = new FocusUpgradeType[id + 1];
                System.arraycopy(types, 0, temp, 0, types.length);
                types = temp;
            }

            types[id] = this;
        }
    }

    public Component getLocalizedName() {
        return Component.translatable(this.name);
    }

    public Component getLocalizedText() {
        return Component.translatable(this.text);
    }

    public boolean equals(Object obj) {
        if (obj instanceof FocusUpgradeType) {
            return this.id == ((FocusUpgradeType)obj).id;
        } else {
            return false;
        }
    }

    static {
        potency = new FocusUpgradeType(0, IResourceLocation.create("thaumcraft", "textures/foci/potency.png"), "focus.upgrade.potency.name", "focus.upgrade.potency.text", StorageElements.createByElement(TCMagicElements.WEAPON, 1));
        frugal = new FocusUpgradeType(1, IResourceLocation.create("thaumcraft", "textures/foci/frugal.png"), "focus.upgrade.frugal.name", "focus.upgrade.frugal.text", StorageElements.createByElement(TCMagicElements.HUNGER, 1));
        treasure = new FocusUpgradeType(2, IResourceLocation.create("thaumcraft", "textures/foci/treasure.png"), "focus.upgrade.treasure.name", "focus.upgrade.treasure.text", StorageElements.createByElement(TCMagicElements.GREED, 1));
        enlarge = new FocusUpgradeType(3, IResourceLocation.create("thaumcraft", "textures/foci/enlarge.png"), "focus.upgrade.enlarge.name", "focus.upgrade.enlarge.text", StorageElements.createByElement(TCMagicElements.TRAVEL, 1));
        alchemistsfire = new FocusUpgradeType(4, IResourceLocation.create("thaumcraft", "textures/foci/alchemistsfire.png"), "focus.upgrade.alchemistsfire.name", "focus.upgrade.alchemistsfire.text", StorageElements.createByElement(TCMagicElements.ENERGY, 1)/*.addElement(TCMagicElements.SLIME, 1)*/);
        alchemistsfrost = new FocusUpgradeType(5, IResourceLocation.create("thaumcraft", "textures/foci/alchemistsfrost.png"), "focus.upgrade.alchemistsfrost.name", "focus.upgrade.alchemistsfrost.text", StorageElements.createByElement(TCMagicElements.COLD, 1)/*.addElement(TCMagicElements.TRAP, 1)*/);
        architect = new FocusUpgradeType(6, IResourceLocation.create("thaumcraft", "textures/foci/architect.png"), "focus.upgrade.architect.name", "focus.upgrade.architect.text", StorageElements.createByElement(TCMagicElements.CRAFT, 1));
        extend = new FocusUpgradeType(7, IResourceLocation.create("thaumcraft", "textures/foci/extend.png"), "focus.upgrade.extend.name", "focus.upgrade.extend.text", StorageElements.createByElement(TCMagicElements.EXCHANGE, 1));
        silktouch = new FocusUpgradeType(8, IResourceLocation.create("thaumcraft", "textures/foci/silktouch.png"), "focus.upgrade.silktouch.name", "focus.upgrade.silktouch.text", StorageElements.createByElement(TCMagicElements.GREED, 1));
    }
}