package org.cloudburstmc.protocol.bedrock.codec.v2225.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v2207.serializer.ClientboundMatchmakingStateSerializer_v2207;
import org.cloudburstmc.protocol.bedrock.data.payload.matchmaking.MatchmakingStateOptions;
import org.cloudburstmc.protocol.bedrock.packet.ClientboundMatchmakingStatePacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClientboundMatchmakingStateSerializer_v2225 extends ClientboundMatchmakingStateSerializer_v2207 {
    public static final ClientboundMatchmakingStateSerializer_v2225 INSTANCE = new ClientboundMatchmakingStateSerializer_v2225();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ClientboundMatchmakingStatePacket packet) {
        super.serialize(buffer, helper, packet);
        helper.writeOptionalNull(buffer, packet.getOptions(), this::writeOptions);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ClientboundMatchmakingStatePacket packet) {
        super.deserialize(buffer, helper, packet);
        packet.setOptions(helper.readOptional(buffer, null, this::readOptions));
    }

    protected void writeOptions(ByteBuf buffer, BedrockCodecHelper helper, MatchmakingStateOptions options) {
        helper.writeOptionalNull(buffer, options.getTriggeringPlayerName(), helper::writeString);
        helper.writeOptionalNull(buffer, options.getTriggeredByLocalPlayer(), ByteBuf::writeBoolean);
    }

    protected MatchmakingStateOptions readOptions(ByteBuf buffer, BedrockCodecHelper helper) {
        final MatchmakingStateOptions options = new MatchmakingStateOptions();
        options.setTriggeringPlayerName(helper.readOptional(buffer, null, helper::readString));
        options.setTriggeredByLocalPlayer(helper.readOptional(buffer, null, ByteBuf::readBoolean));
        return options;
    }
}
