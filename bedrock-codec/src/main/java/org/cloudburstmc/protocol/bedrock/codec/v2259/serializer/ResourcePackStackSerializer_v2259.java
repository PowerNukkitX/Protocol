package org.cloudburstmc.protocol.bedrock.codec.v2259.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v898.serializer.ResourcePackStackSerializer_v898;
import org.cloudburstmc.protocol.bedrock.data.payload.pack.EditorPackType;
import org.cloudburstmc.protocol.bedrock.packet.ResourcePackStackPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResourcePackStackSerializer_v2259 extends ResourcePackStackSerializer_v898 {
    public static final ResourcePackStackSerializer_v2259 INSTANCE = new ResourcePackStackSerializer_v2259();

    private static final EditorPackType[] EDITOR_PACK_TYPES = EditorPackType.values();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ResourcePackStackPacket packet) {
        buffer.writeBoolean(packet.isTexturePackRequired());
        helper.writeArray(buffer, packet.getTexturePackList(), this::writePackInstanceId);
        helper.writeString(buffer, packet.getBaseGameVersion());
        this.writeExperiments(buffer, helper, packet.getExperiments());
        int mask = 0;
        for (EditorPackType type : packet.getEditorPackTypes()) {
            mask |= type.getBit();
        }
        buffer.writeByte(mask);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ResourcePackStackPacket packet) {
        packet.setTexturePackRequired(buffer.readBoolean());
        helper.readArray(buffer, packet.getTexturePackList(), this::readPackInstanceId, MAX_LENGTH);
        packet.setBaseGameVersion(helper.readString(buffer));
        packet.setExperiments(this.readExperiments(buffer, helper));
        final int mask = buffer.readUnsignedByte();
        // yeah, the client throws the whole packet away on unknown bits, so we do the same
        if ((mask & ~EditorPackType.allBits()) != 0) {
            throw new IllegalStateException("Invalid editor pack types: " + mask);
        }
        for (EditorPackType type : EDITOR_PACK_TYPES) {
            if ((mask & type.getBit()) != 0) {
                packet.getEditorPackTypes().add(type);
            }
        }
        packet.setIncludeEditorPacks(mask != 0);
    }
}
