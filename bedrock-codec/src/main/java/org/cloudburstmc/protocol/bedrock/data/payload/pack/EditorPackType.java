package org.cloudburstmc.protocol.bedrock.data.payload.pack;

/**
 * Built in Editor packs the client stacks on top of the world, sent as a bit mask where the bit is the ordinal.
 *
 * @since v2259
 */
public enum EditorPackType {

    CORE,
    ANNOTATION;

    private static final EditorPackType[] VALUES = values();

    public int getBit() {
        return 1 << this.ordinal();
    }

    /**
     * Mask with every known bit set. The client rejects the packet if anything outside of it is set.
     */
    public static int allBits() {
        int mask = 0;
        for (EditorPackType type : VALUES) {
            mask |= type.getBit();
        }
        return mask;
    }
}
