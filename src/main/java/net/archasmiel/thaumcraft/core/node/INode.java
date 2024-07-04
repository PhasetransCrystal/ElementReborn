package net.archasmiel.thaumcraft.core.node;

import net.archasmiel.thaumcraft.core.element.MagicElement;
import net.archasmiel.thaumcraft.core.element.StorageElements;

import java.util.UUID;

public interface INode {
    UUID getUUID();
    StorageElements getElementsBase();

    NodeType getNodeType();

    void setNodeType(NodeType var1);

    void setNodeModifier(NodeModifier var1);

    NodeModifier getNodeModifier();

    int getNodeVisBase(MagicElement var1);

    void setNodeVisBase(MagicElement var1, short var2);

}
