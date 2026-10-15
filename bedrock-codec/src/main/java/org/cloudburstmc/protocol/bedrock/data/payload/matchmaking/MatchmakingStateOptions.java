package org.cloudburstmc.protocol.bedrock.data.payload.matchmaking;

import lombok.Data;

/**
 * Both fields are optional on the wire, leave them null to omit.
 *
 * @since v2225
 */
@Data
public class MatchmakingStateOptions {

    private String triggeringPlayerName;
    private Boolean triggeredByLocalPlayer;
}
