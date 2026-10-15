package org.cloudburstmc.protocol.bedrock.data.payload.inventory;

/**
 * @since v2225
 */
public enum CursorItemDragState {

    START,
    STOP;

    private static final CursorItemDragState[] VALUES = values();

    public static CursorItemDragState from(int ordinal) {
        if (ordinal >= 0 && ordinal < VALUES.length) {
            return VALUES[ordinal];
        }
        throw new UnsupportedOperationException("Detected unknown CursorItemDragState ID: " + ordinal);
    }
}
