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
    public final int displayColumn;
    public final int displayRow;
    public final int complexity;
    public final ItemStack icon_item;
    public final ResourceLocation icon_resource;
    private boolean isVirtual;


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

    public ResearchItem setVirtual() {
        this.isVirtual = true;
        return this;
    }


    public ResearchItem registerResearchItem() {
        // TODO: register research item
        return this;
    }

    public Component getName() {
        return Component.translatable("tc.research_name." + this.key);
    }

    public Component getText() {
        return Component.translatable("tc.research_text." + this.key);
    }


    public boolean isVirtual() {
        return this.isVirtual;
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
