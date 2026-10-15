package org.cloudburstmc.protocol.bedrock.data.payload.passenger;

/**
 * @since v2225
 */
public enum PassengerOfBlockEmoteType {

    STANDING,
    RIDING,
    LAYING;

    private static final PassengerOfBlockEmoteType[] VALUES = values();

    public static PassengerOfBlockEmoteType from(int ordinal) {
        if (ordinal >= 0 && ordinal < VALUES.length) {
            return VALUES[ordinal];
        }
        throw new UnsupportedOperationException("Detected unknown PassengerOfBlockArguments::EmoteType ID: " + ordinal);
    }
}
