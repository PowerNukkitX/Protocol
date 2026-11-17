package org.cloudburstmc.protocol.bedrock.codec.v2259.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v898.serializer.CommandOutputSerializer_v898;
import org.cloudburstmc.protocol.bedrock.data.command.CommandOutputMessage;
import org.cloudburstmc.protocol.bedrock.packet.CommandOutputPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommandOutputSerializer_v2259 extends CommandOutputSerializer_v898 {
    public static final CommandOutputSerializer_v2259 INSTANCE = new CommandOutputSerializer_v2259();

    @Override
    public void writeMessage(ByteBuf buffer, BedrockCodecHelper helper, CommandOutputMessage outputMessage) {
        super.writeMessage(buffer, helper, outputMessage);
        helper.writeOptionalNull(buffer, outputMessage.getRawMessage(), helper::writeString);
    }

    @Override
    public CommandOutputMessage readMessage(ByteBuf buffer, BedrockCodecHelper helper) {
        final CommandOutputMessage message = super.readMessage(buffer, helper);
        final String rawMessage = helper.readOptional(buffer, null, helper::readString);
        return new CommandOutputMessage(message.isSuccessful(), message.getMessageId(), message.getParameters(), rawMessage);
    }
}
