package org.cloudburstmc.protocol.bedrock.data.payload.configuration;

import lombok.Data;

/**
 * @since v2259
 */
@Data
public class ManagedChatConfiguration {

    /**
     * Optional on the wire, null leaves it up to the client.
     */
    private Boolean enabled;
}
