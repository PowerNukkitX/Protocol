package org.cloudburstmc.protocol.bedrock.data.payload.connection;

import lombok.Data;

/**
 * Customises the loading screen shown while the client transfers to another server.
 * Every field is optional, null leaves the client default alone.
 *
 * @since v2259
 */
@Data
public class TransferPlayerUIOptions {

    private String titleText;
    private String bodyText;
    private Boolean titleVisible;
    private Boolean bodyVisible;
    private String cancelButtonText;
    private Boolean cancelButtonVisible;
    /**
     * How long the cancel button stays hidden before it shows up.
     */
    private Float cancelButtonDelaySeconds;
}
