package org.cloudburstmc.protocol.bedrock.data.payload.chat;

import lombok.Data;

/**
 * Every field is optional on the wire, null leaves the client default alone.
 *
 * @since v2259
 */
@Data
public class ChatFormatting {

    private String namePrefix;
    private String namePostfix;
    private String messagePrefix;
    private String messagePostfix;
}
