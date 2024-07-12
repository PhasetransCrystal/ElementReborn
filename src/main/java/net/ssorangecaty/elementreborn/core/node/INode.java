package net.ssorangecaty.elementreborn.core.node;

import net.ssorangecaty.elementreborn.core.element.IStorageElementsAble;
import net.ssorangecaty.elementreborn.core.element.MagicElement;

public interface INode extends IStorageElementsAble {
    NodeType getNodeType();

    void setNodeType(NodeType var1);

    void setNodeModifier(NodeModifier var1);

    NodeModifier getNodeModifier();

    float getNodeVis(MagicElement element);

    void setNodeVis(MagicElement element, float value);

}
