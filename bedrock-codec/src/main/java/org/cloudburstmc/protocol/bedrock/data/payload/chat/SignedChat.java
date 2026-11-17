package org.cloudburstmc.protocol.bedrock.data.payload.chat;

import lombok.Data;

/**
 * Chat message as signed by the Mojang chat service.
 *
 * @since v2259
 */
@Data
public class SignedChat {

    private String keyId;
    private String senderId;
    private String messageId;
    private String scanText;
    private String redactedText;
    private String authorName;
    private String signature;
}
