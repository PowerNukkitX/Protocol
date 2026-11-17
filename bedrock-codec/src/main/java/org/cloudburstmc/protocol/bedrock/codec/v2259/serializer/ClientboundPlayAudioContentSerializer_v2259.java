package org.cloudburstmc.protocol.bedrock.codec.v2259.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v2225.serializer.ClientboundPlayAudioContentSerializer_v2225;
import org.cloudburstmc.protocol.bedrock.data.payload.audio.AudioContentPlaybackType;
import org.cloudburstmc.protocol.bedrock.packet.ClientboundPlayAudioContentPacket;
import org.cloudburstmc.protocol.bedrock.packet.PlaySoundPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClientboundPlayAudioContentSerializer_v2259 extends ClientboundPlayAudioContentSerializer_v2225 {
    public static final ClientboundPlayAudioContentSerializer_v2259 INSTANCE = new ClientboundPlayAudioContentSerializer_v2259();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ClientboundPlayAudioContentPacket packet) {
        helper.writeString(buffer, packet.getSharedMetadata());
        helper.writeString(buffer, packet.getPlaybackContent());
        helper.writeString(buffer, packet.getPlaybackType().getId());
        // the embedded play sound follows whatever the standalone packet looks like this version
        PlaySoundSerializer_v2259.INSTANCE.serialize(buffer, helper, packet.getPlaySound());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ClientboundPlayAudioContentPacket packet) {
        packet.setSharedMetadata(helper.readString(buffer));
        packet.setPlaybackContent(helper.readString(buffer));
        packet.setPlaybackType(AudioContentPlaybackType.from(helper.readString(buffer)));
        final PlaySoundPacket playSound = new PlaySoundPacket();
        PlaySoundSerializer_v2259.INSTANCE.deserialize(buffer, helper, playSound);
        packet.setPlaySound(playSound);
    }
}
