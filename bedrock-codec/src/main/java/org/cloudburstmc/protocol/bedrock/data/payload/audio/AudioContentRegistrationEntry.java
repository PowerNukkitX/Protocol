package org.cloudburstmc.protocol.bedrock.data.payload.audio;

import lombok.Data;

/**
 * The three content fields are signed audio content, sent as compact JWTs.
 *
 * @since v2225
 */
@Data
public class AudioContentRegistrationEntry {

    private String audioContentID;
    private String sharedMetadata;
    private String serverContent;
    private String playbackContent;
}
