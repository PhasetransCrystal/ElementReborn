package net.archasmiel.thaumcraft.core.node;

public enum NodeModifier {
    COMMON(0.75f),
    BRIGHT(0.9f),
    PALE(0.5f),
    FADING(0.15f);

    final public float alpha;
    NodeModifier(float alpha) {
        this.alpha = alpha;
    }

    public float getAlpha() {
        return alpha;
    }
}