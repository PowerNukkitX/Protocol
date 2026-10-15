package org.cloudburstmc.protocol.bedrock.data.payload.matchmaking;

/**
 * @author Kaooot
 * @since v2207
 */
public enum MatchmakingState {

    IDLE,
    MATCHMAKING,
    MATCH_FOUND,
    /**
     * @since v2225
     */
    CANCELED,
    /**
     * @since v2225
     */
    PLAYER_LEFT_PARTY,
    /**
     * @since v2225
     */
    PLAYER_LEFT_SERVER,
    /**
     * @since v2225
     */
    SERVER_SHUTDOWN,
    /**
     * @since v2225
     */
    TIMED_OUT,
    /**
     * @since v2225
     */
    REQUEUE_AS_PARTY;

    private static final MatchmakingState[] VALUES = values();

    public static MatchmakingState from(int ordinal) {
        if (ordinal >= 0 && ordinal < VALUES.length) {
            return VALUES[ordinal];
        }
        throw new IllegalArgumentException("Unknown MatchmakingState: " + ordinal);
    }
}
