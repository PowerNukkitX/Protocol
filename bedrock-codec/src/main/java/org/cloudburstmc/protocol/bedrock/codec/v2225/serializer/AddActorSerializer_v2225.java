package org.cloudburstmc.protocol.bedrock.codec.v2225.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v557.serializer.AddActorSerializer_v557;
import org.cloudburstmc.protocol.bedrock.packet.AddActorPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddActorSerializer_v2225 extends AddActorSerializer_v557 {
    public static final AddActorSerializer_v2225 INSTANCE = new AddActorSerializer_v2225();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, AddActorPacket packet) {
        super.serialize(buffer, helper, packet);
        helper.writeOptionalNull(buffer, packet.getPassengerOfBlockData(), helper::writePassengerOfBlockArguments);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, AddActorPacket packet) {
        super.deserialize(buffer, helper, packet);
        packet.setPassengerOfBlockData(helper.readOptional(buffer, null, helper::readPassengerOfBlockArguments));
    }
}
