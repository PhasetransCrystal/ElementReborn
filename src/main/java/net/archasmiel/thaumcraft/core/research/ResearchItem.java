package net.archasmiel.thaumcraft.core.research;

import net.archasmiel.thaumcraft.core.element.MagicElement;
import net.archasmiel.thaumcraft.core.element.StorageElements;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class ResearchItem {
    public final String key;
    public final String category;
    public final StorageElements tags;
    public String[] parents = null;
    public String[] parentsHidden = null;
    public String[] siblings = null;
    public final int displayColumn;
    public final int displayRow;
    public final ItemStack icon_item;
    public final ResourceLocation icon_resource;
    private int complexity;
    private boolean isSpecial;
    private boolean isSecondary;
    private boolean isRound;
    private boolean isStub;
    private boolean isVirtual;
    private boolean isConcealed;
    private boolean isHidden;
    private boolean isLost;
    private boolean isAutoUnlock;
    private Iterable<ItemStack> itemTriggers;
    private String[] entityTriggers;
    private Iterable<MagicElement> MagicElementTriggers;
    private Iterable<ResearchPage> pages = null;


    private ResearchItem(Builder builder) {
        this.key = builder.key;
        this.category = builder.category;
        this.tags = builder.tags != null ? builder.tags : new StorageElements(new HashMap<>());
        this.icon_resource = builder.icon_resource;
        this.icon_item = builder.icon_item;
        this.displayColumn = builder.displayColumn;
        this.displayRow = builder.displayRow;
        this.complexity = Math.max(1, Math.min(3, builder.complexity));

        if(builder.icon_item == null && builder.icon_resource == null && builder.displayColumn == 0 && builder.displayRow == 0){
            this.setVirtual();
        }
    }

    public ResearchItem setSpecial() {
        this.isSpecial = true;
        return this;
    }

    public ResearchItem setStub() {
        this.isStub = true;
        return this;
    }

    public ResearchItem setLost() {
        this.isLost = true;
        return this;
    }

    public ResearchItem setConcealed() {
        this.isConcealed = true;
        return this;
    }

    public ResearchItem setHidden() {
        this.isHidden = true;
        return this;
    }

    public ResearchItem setVirtual() {
        this.isVirtual = true;
        return this;
    }

    public ResearchItem setParents(String... par) {
        this.parents = par;
        return this;
    }

    public ResearchItem setParentsHidden(String... par) {
        this.parentsHidden = par;
        return this;
    }

    public ResearchItem setSiblings(String... sib) {
        this.siblings = sib;
        return this;
    }

    public ResearchItem setPages(ResearchPage... par) {
        this.pages = List.of(par);
        return this;
    }

    public Iterable<ResearchPage> getPages() {
        return this.pages;
    }

    public ResearchItem setItemTriggers(ItemStack... par) {
        this.itemTriggers = List.of(par);
        return this;
    }

    public ResearchItem setEntityTriggers(String... par) {
        this.entityTriggers = par;
        return this;
    }

    public ResearchItem setMagicElementTriggers(MagicElement... par) {
        this.MagicElementTriggers = List.of(par);
        return this;
    }

    public Iterable<ItemStack> getItemTriggers() {
        return this.itemTriggers;
    }

    public String[] getEntityTriggers() {
        return this.entityTriggers;
    }

    public Iterable<MagicElement> getMagicElementTriggers() {
        return this.MagicElementTriggers;
    }

    public ResearchItem registerResearchItem() {
        ResearchCategories.addResearch(this);
        return this;
    }

    public Component getName() {
        return Component.translatable("tc.research_name." + this.key);
    }

    public Component getText() {
        return Component.translatable("tc.research_text." + this.key);
    }

    public boolean isSpecial() {
        return this.isSpecial;
    }

    public boolean isStub() {
        return this.isStub;
    }

    public boolean isLost() {
        return this.isLost;
    }

    public boolean isConcealed() {
        return this.isConcealed;
    }

    public boolean isHidden() {
        return this.isHidden;
    }

    public boolean isVirtual() {
        return this.isVirtual;
    }

    public boolean isAutoUnlock() {
        return this.isAutoUnlock;
    }

    public ResearchItem setAutoUnlock() {
        this.isAutoUnlock = true;
        return this;
    }

    public boolean isRound() {
        return this.isRound;
    }

    public ResearchItem setRound() {
        this.isRound = true;
        return this;
    }

    public boolean isSecondary() {
        return this.isSecondary;
    }

    public ResearchItem setSecondary() {
        this.isSecondary = true;
        return this;
    }

    public int getComplexity() {
        return this.complexity;
    }

    public ResearchItem setComplexity(int complexity) {
        this.complexity = complexity;
        return this;
    }

    public MagicElement getResearchPrimaryTag() {
        return ((Collection<MagicElement>) this.tags.getElements()).stream().max(Comparator.comparingDouble(this.tags::getElementValue))
                .orElse(null);
    }

    public static class Builder {
        private String key = null;
        private String category = null;
        private StorageElements tags = new StorageElements(new HashMap<>());
        private ResourceLocation icon_resource = null;
        private ItemStack icon_item = null;
        private int displayColumn = 0;
        private int displayRow = 0;
        private int complexity = 1;

        public Builder setKey(String key) {
            this.key = key;
            return this;
        }


        public Builder setCategory(String category) {
            this.category = category;
            return this;
        }

        public Builder setTags(StorageElements tags) {
            this.tags = tags;
            return this;
        }

        public Builder setIcon(ResourceLocation icon_resource) {
            this.icon_resource = icon_resource;
            return this;
        }

        public Builder setIcon(ItemStack icon_item) {
            this.icon_item = icon_item;
            return this;
        }

        public Builder setDisplayColumn(int displayColumn) {
            this.displayColumn = displayColumn;
            return this;
        }

        public Builder setDisplayRow(int displayRow) {
            this.displayRow = displayRow;
            return this;
        }

        public Builder setComplexity(int complexity) {
            this.complexity = complexity;
            return this;
        }

        public ResearchItem build() {
            if(this.tags == null || this.category == null){
                throw new IllegalArgumentException("Category and tags must be set.");
            }
            return new ResearchItem(this);
        }

    }
}
