package net.archasmiel.thaumcraft.core.node;

public enum NodeModifier {
    COMMON(0.75f),
    BRIGHT(1.5f),
    PALE(0.66f),
    FADING(0.25f);

    final public float alpha;
    NodeModifier(float alpha) {
        this.alpha = alpha;
    }

    public float getAlpha() {
        return alpha;
    }
}