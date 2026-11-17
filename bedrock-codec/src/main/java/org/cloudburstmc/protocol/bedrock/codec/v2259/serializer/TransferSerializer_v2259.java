package org.cloudburstmc.protocol.bedrock.codec.v2259.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v2168.serializer.TransferSerializer_v2168;
import org.cloudburstmc.protocol.bedrock.data.payload.connection.TransferPlayerUIOptions;
import org.cloudburstmc.protocol.bedrock.packet.TransferPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransferSerializer_v2259 extends TransferSerializer_v2168 {
    public static final TransferSerializer_v2259 INSTANCE = new TransferSerializer_v2259();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, TransferPacket packet) {
        super.serialize(buffer, helper, packet);
        helper.writeOptionalNull(buffer, packet.getUiOptions(), this::writeUIOptions);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, TransferPacket packet) {
        super.deserialize(buffer, helper, packet);
        packet.setUiOptions(helper.readOptional(buffer, null, this::readUIOptions));
    }

    protected void writeUIOptions(ByteBuf buffer, BedrockCodecHelper helper, TransferPlayerUIOptions options) {
        helper.writeOptionalNull(buffer, options.getTitleText(), helper::writeString);
        helper.writeOptionalNull(buffer, options.getBodyText(), helper::writeString);
        helper.writeOptionalNull(buffer, options.getTitleVisible(), ByteBuf::writeBoolean);
        helper.writeOptionalNull(buffer, options.getBodyVisible(), ByteBuf::writeBoolean);
        helper.writeOptionalNull(buffer, options.getCancelButtonText(), helper::writeString);
        helper.writeOptionalNull(buffer, options.getCancelButtonVisible(), ByteBuf::writeBoolean);
        helper.writeOptionalNull(buffer, options.getCancelButtonDelaySeconds(), ByteBuf::writeFloatLE);
    }

    protected TransferPlayerUIOptions readUIOptions(ByteBuf buffer, BedrockCodecHelper helper) {
        final TransferPlayerUIOptions options = new TransferPlayerUIOptions();
        options.setTitleText(helper.readOptional(buffer, null, helper::readString));
        options.setBodyText(helper.readOptional(buffer, null, helper::readString));
        options.setTitleVisible(helper.readOptional(buffer, null, ByteBuf::readBoolean));
        options.setBodyVisible(helper.readOptional(buffer, null, ByteBuf::readBoolean));
        options.setCancelButtonText(helper.readOptional(buffer, null, helper::readString));
        options.setCancelButtonVisible(helper.readOptional(buffer, null, ByteBuf::readBoolean));
        options.setCancelButtonDelaySeconds(helper.readOptional(buffer, null, ByteBuf::readFloatLE));
        return options;
    }
}
