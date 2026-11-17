package org.cloudburstmc.protocol.bedrock.codec.v2259.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.payload.chat.ChatFormatting;
import org.cloudburstmc.protocol.bedrock.data.payload.chat.SignedChat;
import org.cloudburstmc.protocol.bedrock.packet.ChatPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatSerializer_v2259 implements BedrockPacketSerializer<ChatPacket> {
    public static final ChatSerializer_v2259 INSTANCE = new ChatSerializer_v2259();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ChatPacket packet) {
        this.writeSignedChat(buffer, helper, packet.getSignedChat());
        helper.writeOptionalNull(buffer, packet.getRoomId(), helper::writeString);
        helper.writeOptionalNull(buffer, packet.getFormatting(), this::writeFormatting);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ChatPacket packet) {
        packet.setSignedChat(this.readSignedChat(buffer, helper));
        packet.setRoomId(helper.readOptional(buffer, null, helper::readString));
        packet.setFormatting(helper.readOptional(buffer, null, this::readFormatting));
    }

    protected void writeSignedChat(ByteBuf buffer, BedrockCodecHelper helper, SignedChat chat) {
        helper.writeString(buffer, chat.getKeyId());
        helper.writeString(buffer, chat.getSenderId());
        helper.writeString(buffer, chat.getMessageId());
        helper.writeString(buffer, chat.getScanText());
        helper.writeString(buffer, chat.getRedactedText());
        helper.writeString(buffer, chat.getAuthorName());
        helper.writeString(buffer, chat.getSignature());
    }

    protected SignedChat readSignedChat(ByteBuf buffer, BedrockCodecHelper helper) {
        final SignedChat chat = new SignedChat();
        chat.setKeyId(helper.readString(buffer));
        chat.setSenderId(helper.readString(buffer));
        chat.setMessageId(helper.readString(buffer));
        chat.setScanText(helper.readString(buffer));
        chat.setRedactedText(helper.readString(buffer));
        chat.setAuthorName(helper.readString(buffer));
        chat.setSignature(helper.readString(buffer));
        return chat;
    }

    protected void writeFormatting(ByteBuf buffer, BedrockCodecHelper helper, ChatFormatting formatting) {
        helper.writeOptionalNull(buffer, formatting.getNamePrefix(), helper::writeString);
        helper.writeOptionalNull(buffer, formatting.getNamePostfix(), helper::writeString);
        helper.writeOptionalNull(buffer, formatting.getMessagePrefix(), helper::writeString);
        helper.writeOptionalNull(buffer, formatting.getMessagePostfix(), helper::writeString);
    }

    protected ChatFormatting readFormatting(ByteBuf buffer, BedrockCodecHelper helper) {
        final ChatFormatting formatting = new ChatFormatting();
        formatting.setNamePrefix(helper.readOptional(buffer, null, helper::readString));
        formatting.setNamePostfix(helper.readOptional(buffer, null, helper::readString));
        formatting.setMessagePrefix(helper.readOptional(buffer, null, helper::readString));
        formatting.setMessagePostfix(helper.readOptional(buffer, null, helper::readString));
        return formatting;
    }
}
