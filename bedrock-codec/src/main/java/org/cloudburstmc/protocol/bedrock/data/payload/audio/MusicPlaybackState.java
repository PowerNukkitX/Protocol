package org.cloudburstmc.protocol.bedrock.data.payload.audio;

/**
 * @since v2259
 */
public enum MusicPlaybackState {

    LOCAL,
    ONLINE,
    // server has no definition for the sound event
    NOT_FOUND,
    // server isn't ready to verify registered audio content yet
    PENDING;

    private static final MusicPlaybackState[] VALUES = values();

    public static MusicPlaybackState from(int ordinal) {
        if (ordinal >= 0 && ordinal < VALUES.length) {
            return VALUES[ordinal];
        }
        throw new UnsupportedOperationException("Detected unknown MusicPlaybackState ID: " + ordinal);
    }
}
