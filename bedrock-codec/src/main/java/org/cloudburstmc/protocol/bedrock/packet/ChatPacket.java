package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.payload.chat.ChatFormatting;
import org.cloudburstmc.protocol.bedrock.data.payload.chat.SignedChat;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Chat message signed by the Mojang chat service, goes both ways.
 *
 * @since v2259
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ChatPacket implements BedrockPacket {

    private SignedChat signedChat = new SignedChat();
    private String roomId;
    private ChatFormatting formatting;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CHAT;
    }

    @Override
    public ChatPacket clone() {
        try {
            return (ChatPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
