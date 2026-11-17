package org.cloudburstmc.protocol.bedrock.codec.v2259.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v975.serializer.ClientboundAttributeLayerSyncSerializer_v975;
import org.cloudburstmc.protocol.bedrock.data.payload.attribute.eas.AttributeDataType;
import org.cloudburstmc.protocol.bedrock.data.payload.attribute.eas.EASAttributeData;
import org.cloudburstmc.protocol.bedrock.data.payload.attribute.eas.StringAttributeData;
import org.cloudburstmc.protocol.bedrock.data.payload.attribute.eas.StringAttributeOperation;
import org.cloudburstmc.protocol.bedrock.packet.ClientboundAttributeLayerSyncPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClientboundAttributeLayerSyncSerializer_v2259 extends ClientboundAttributeLayerSyncSerializer_v975 {
    public static final ClientboundAttributeLayerSyncSerializer_v2259 INSTANCE = new ClientboundAttributeLayerSyncSerializer_v2259();

    @Override
    protected void writeAttributeData(ByteBuf buffer, BedrockCodecHelper helper, EASAttributeData data) {
        if (data.getType() != AttributeDataType.STRING) {
            super.writeAttributeData(buffer, helper, data);
            return;
        }
        final StringAttributeData stringData = (StringAttributeData) data;
        VarInts.writeUnsignedInt(buffer, AttributeDataType.STRING.ordinal());
        helper.writeString(buffer, stringData.getValue());
        helper.writeOptionalNull(buffer, stringData.getOperation() == null ? null : stringData.getOperation().name(), helper::writeString);
    }

    @Override
    protected EASAttributeData readAttributeData(ByteBuf buffer, BedrockCodecHelper helper) {
        // peek the type so the older variants still go through the parent untouched
        final int readerIndex = buffer.readerIndex();
        if (AttributeDataType.from(VarInts.readUnsignedInt(buffer)) != AttributeDataType.STRING) {
            buffer.readerIndex(readerIndex);
            return super.readAttributeData(buffer, helper);
        }
        final StringAttributeData data = new StringAttributeData();
        data.setValue(helper.readString(buffer));
        data.setOperation(helper.readOptional(buffer, null,
                (buf, h) -> StringAttributeOperation.valueOf(h.readString(buf))));
        return data;
    }
}
