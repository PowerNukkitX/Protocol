package org.cloudburstmc.protocol.bedrock.data.payload.audio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @since v2259
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MusicPlaybackFailureEntry {

    private MusicPlaybackState state;
    private String description;
}
