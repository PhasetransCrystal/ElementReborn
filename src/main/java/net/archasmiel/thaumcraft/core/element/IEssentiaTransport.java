package net.archasmiel.thaumcraft.core.element;


import net.minecraft.core.Direction;

public interface IEssentiaTransport {
    boolean isConnectable(Direction direction);

    boolean canInputFrom(Direction direction);

    boolean canOutputTo(Direction direction);

    void setSuction(MagicElement magicElement, int value);

    MagicElement getSuctionType(Direction direction);

    int getSuctionAmount(Direction direction);

    int takeEssentia(MagicElement magicElement, int value, Direction direction);

    int addEssentia(MagicElement magicElement, int value, Direction direction);

    MagicElement getEssentiaType(Direction direction);

    int getEssentiaAmount(Direction direction);

    int getMinimumSuction();

    boolean renderExtendedTube();
}

