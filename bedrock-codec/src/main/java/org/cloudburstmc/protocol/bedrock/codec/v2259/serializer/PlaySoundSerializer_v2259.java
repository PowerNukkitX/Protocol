package org.cloudburstmc.protocol.bedrock.codec.v2259.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v2192.serializer.PlaySoundSerializer_v2192;
import org.cloudburstmc.protocol.bedrock.packet.PlaySoundPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaySoundSerializer_v2259 extends PlaySoundSerializer_v2192 {
    public static final PlaySoundSerializer_v2259 INSTANCE = new PlaySoundSerializer_v2259();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, PlaySoundPacket packet) {
        super.serialize(buffer, helper, packet);
        helper.writeOptionalNull(buffer, packet.getStartDelaySeconds(), ByteBuf::writeFloatLE);
        helper.writeOptionalNull(buffer, packet.getAttachToActorRuntimeId(), VarInts::writeUnsignedLong);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, PlaySoundPacket packet) {
        super.deserialize(buffer, helper, packet);
        packet.setStartDelaySeconds(helper.readOptional(buffer, null, ByteBuf::readFloatLE));
        packet.setAttachToActorRuntimeId(helper.readOptional(buffer, null, VarInts::readUnsignedLong));
    }
}
