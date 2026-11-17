package org.cloudburstmc.protocol.bedrock.data.payload.inventory;

/**
 * @since v2259
 */
public enum VirtualContainerCloseKind {

    REQUEST,
    CLOSED,
    CONFIRMED;

    private static final VirtualContainerCloseKind[] VALUES = values();

    public static VirtualContainerCloseKind from(int ordinal) {
        if (ordinal >= 0 && ordinal < VALUES.length) {
            return VALUES[ordinal];
        }
        throw new UnsupportedOperationException("Detected unknown VirtualContainerCloseKind ID: " + ordinal);
    }
}
