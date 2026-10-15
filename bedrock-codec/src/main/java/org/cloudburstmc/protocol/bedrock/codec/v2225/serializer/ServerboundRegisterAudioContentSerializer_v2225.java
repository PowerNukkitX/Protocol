package org.cloudburstmc.protocol.bedrock.codec.v2225.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.payload.audio.AudioContentRegistrationEntry;
import org.cloudburstmc.protocol.bedrock.packet.ServerboundRegisterAudioContentPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServerboundRegisterAudioContentSerializer_v2225 implements BedrockPacketSerializer<ServerboundRegisterAudioContentPacket> {
    public static final ServerboundRegisterAudioContentSerializer_v2225 INSTANCE = new ServerboundRegisterAudioContentSerializer_v2225();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ServerboundRegisterAudioContentPacket packet) {
        helper.writeArray(buffer, packet.getRegistrations(), this::writeRegistration);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ServerboundRegisterAudioContentPacket packet) {
        helper.readArray(buffer, packet.getRegistrations(), this::readRegistration);
    }

    protected void writeRegistration(ByteBuf buffer, BedrockCodecHelper helper, AudioContentRegistrationEntry entry) {
        helper.writeString(buffer, entry.getAudioContentID());
        helper.writeString(buffer, entry.getSharedMetadata());
        helper.writeString(buffer, entry.getServerContent());
        helper.writeString(buffer, entry.getPlaybackContent());
    }

    protected AudioContentRegistrationEntry readRegistration(ByteBuf buffer, BedrockCodecHelper helper) {
        final AudioContentRegistrationEntry entry = new AudioContentRegistrationEntry();
        entry.setAudioContentID(helper.readString(buffer));
        entry.setSharedMetadata(helper.readString(buffer));
        entry.setServerContent(helper.readString(buffer));
        entry.setPlaybackContent(helper.readString(buffer));
        return entry;
    }
}
