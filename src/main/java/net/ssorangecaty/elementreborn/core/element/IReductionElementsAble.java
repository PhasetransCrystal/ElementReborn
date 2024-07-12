package net.ssorangecaty.elementreborn.core.element;

import net.minecraft.network.chat.Component;

public interface IReductionElementsAble {
    float getValueAfterReduction(MagicElement element, int value);
    float getReductionTime();
    Component getReductionMessage();


}
