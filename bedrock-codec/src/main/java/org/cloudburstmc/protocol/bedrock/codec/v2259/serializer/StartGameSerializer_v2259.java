package org.cloudburstmc.protocol.bedrock.codec.v2259.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v2225.serializer.StartGameSerializer_v2225;
import org.cloudburstmc.protocol.bedrock.data.payload.common.ServerConfigurationJoinInfo;
import org.cloudburstmc.protocol.bedrock.data.payload.configuration.ManagedChatConfiguration;
import org.cloudburstmc.protocol.bedrock.packet.StartGamePacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StartGameSerializer_v2259 extends StartGameSerializer_v2225 {
    public static final StartGameSerializer_v2259 INSTANCE = new StartGameSerializer_v2259();

    @Override
    protected void writeServerConfigurationJoinInfo(ByteBuf buffer, BedrockCodecHelper helper, ServerConfigurationJoinInfo joinInfo) {
        super.writeServerConfigurationJoinInfo(buffer, helper, joinInfo);
        helper.writeOptionalNull(buffer, joinInfo.getManagedChatConfiguration(), this::writeManagedChatConfiguration);
    }

    @Override
    protected ServerConfigurationJoinInfo readServerConfigurationJoinInfo(ByteBuf buffer, BedrockCodecHelper helper) {
        final ServerConfigurationJoinInfo joinInfo = super.readServerConfigurationJoinInfo(buffer, helper);
        joinInfo.setManagedChatConfiguration(helper.readOptional(buffer, null, this::readManagedChatConfiguration));
        return joinInfo;
    }

    protected void writeManagedChatConfiguration(ByteBuf buffer, BedrockCodecHelper helper, ManagedChatConfiguration configuration) {
        helper.writeOptionalNull(buffer, configuration.getEnabled(), ByteBuf::writeBoolean);
    }

    protected ManagedChatConfiguration readManagedChatConfiguration(ByteBuf buffer, BedrockCodecHelper helper) {
        final ManagedChatConfiguration configuration = new ManagedChatConfiguration();
        configuration.setEnabled(helper.readOptional(buffer, null, ByteBuf::readBoolean));
        return configuration;
    }
}
