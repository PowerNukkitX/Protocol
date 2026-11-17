package org.cloudburstmc.protocol.bedrock.data.payload.passenger;

import lombok.Data;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.math.vector.Vector3i;

import java.util.UUID;

/**
 * @since v2225
 */
@Data
public class PassengerOfBlockArguments {

    private Vector3i blockPosition;
    private Vector3f offset;
    private float rotation;
    private float rotationLimit;
    private PassengerOfBlockEmoteType emoteType;
    /**
     * Hashed serialized id of the block the passenger sits on.
     *
     * @since v2259
     */
    private long blockType;
    /**
     * @since v2259
     */
    private UUID packUuid;
}
