package org.cloudburstmc.protocol.bedrock.codec.v2259.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v898.serializer.ClientboundDataStoreSerializer_v898;
import org.cloudburstmc.protocol.bedrock.data.ddui.DataStoreChange;
import org.cloudburstmc.protocol.bedrock.packet.ClientboundDataStorePacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClientboundDataStoreSerializer_v2259 extends ClientboundDataStoreSerializer_v898 {
    public static final ClientboundDataStoreSerializer_v2259 INSTANCE = new ClientboundDataStoreSerializer_v2259();

    @Override
    protected void writeDataStoreChange(ByteBuf buffer, BedrockCodecHelper helper, DataStoreChange change) {
        helper.writeString(buffer, change.getVariableName());
        buffer.writeIntLE(change.getUpdateCount());
        this.writeDynamicValue(buffer, helper, change.getTheNewPropertyValue());
    }

    @Override
    protected DataStoreChange readDataStoreChange(ByteBuf buffer, BedrockCodecHelper helper) {
        final DataStoreChange change = new DataStoreChange();
        change.setVariableName(helper.readStringMaxLen(buffer, 1000));
        change.setUpdateCount(buffer.readIntLE());
        change.setTheNewPropertyValue(this.readDynamicValue(buffer, helper));
        return change;
    }
}
