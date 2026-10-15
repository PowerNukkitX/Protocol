package org.cloudburstmc.protocol.bedrock.codec.v2225.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v2168.serializer.ClientboundUpdateSoundDataSerializer_v2168;
import org.cloudburstmc.protocol.bedrock.data.payload.sound.SoundData;
import org.cloudburstmc.protocol.bedrock.data.payload.sound.SoundDataEvent;
import org.cloudburstmc.protocol.bedrock.packet.ClientboundUpdateSoundDataPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClientboundUpdateSoundDataSerializer_v2225 extends ClientboundUpdateSoundDataSerializer_v2168 {
    public static final ClientboundUpdateSoundDataSerializer_v2225 INSTANCE = new ClientboundUpdateSoundDataSerializer_v2225();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ClientboundUpdateSoundDataPacket packet) {
        helper.writeServerSoundHandle(buffer, packet.getServerSoundHandle());
        this.writeSoundData(buffer, helper, packet.getEvent());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ClientboundUpdateSoundDataPacket packet) {
        packet.setServerSoundHandle(helper.readServerSoundHandle(buffer));
        packet.setEvent(this.readSoundData(buffer, helper));
    }

    // a single variant now, and every event struct starts with its own type byte,
    // so the type shows up twice: once as the variant index, once inside the struct
    @Override
    protected void writeSoundData(ByteBuf buffer, BedrockCodecHelper helper, SoundData soundData) {
        VarInts.writeUnsignedInt(buffer, soundData.getType().ordinal());
        buffer.writeByte(soundData.getType().ordinal());
        this.writeSoundDataBody(buffer, soundData);
    }

    @Override
    protected SoundData readSoundData(ByteBuf buffer, BedrockCodecHelper helper) {
        final int variantIndex = VarInts.readUnsignedInt(buffer);
        final SoundDataEvent type = SoundDataEvent.from(buffer.readUnsignedByte());
        if (type.ordinal() != variantIndex) {
            throw new IllegalArgumentException("Sound data type " + type + " does not match variant index " + variantIndex);
        }
        return this.readSoundDataBody(buffer, type);
    }
}
