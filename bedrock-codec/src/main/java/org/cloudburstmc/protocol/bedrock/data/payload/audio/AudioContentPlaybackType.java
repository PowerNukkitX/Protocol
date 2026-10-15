package org.cloudburstmc.protocol.bedrock.data.payload.audio;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @since v2225
 */
@Getter
@RequiredArgsConstructor
public enum AudioContentPlaybackType {

    MUSIC("Music"),
    SOUND("Sound");

    private final String id;

    private static final AudioContentPlaybackType[] VALUES = values();

    public static AudioContentPlaybackType from(String id) {
        for (AudioContentPlaybackType value : VALUES) {
            if (value.getId().equalsIgnoreCase(id)) {
                return value;
            }
        }
        throw new UnsupportedOperationException("Detected unknown AudioContentPlaybackType ID: " + id);
    }
}
