package org.cloudburstmc.protocol.bedrock.data.command;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

@Value
@AllArgsConstructor
public class CommandOutputMessage {
    private final boolean successful;
    @NonNull
    private final String messageId;
    @NonNull
    private final String[] parameters;
    /**
     * Optional on the wire, null when the message only goes through the translation key.
     *
     * @since v2259
     */
    private final String rawMessage;

    public CommandOutputMessage(boolean successful, @NonNull String messageId, @NonNull String[] parameters) {
        this(successful, messageId, parameters, null);
    }
}
