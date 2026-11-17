package org.cloudburstmc.protocol.bedrock.data.payload.attribute.eas;

/**
 * @since v2259
 */
public enum StringAttributeOperation {

    OVERRIDE;

    private static final StringAttributeOperation[] VALUES = values();

    public static StringAttributeOperation from(int ordinal) {
        if (ordinal >= 0 && ordinal < VALUES.length) {
            return VALUES[ordinal];
        }
        throw new UnsupportedOperationException("Detected unknown StringAttributeOperation ID: " + ordinal);
    }
}
