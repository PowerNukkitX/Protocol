package org.cloudburstmc.protocol.bedrock.codec.v2259.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.VirtualContainerOpenPacket;
import org.cloudburstmc.protocol.common.util.Preconditions;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VirtualContainerOpenSerializer_v2259 implements BedrockPacketSerializer<VirtualContainerOpenPacket> {
    public static final VirtualContainerOpenSerializer_v2259 INSTANCE = new VirtualContainerOpenSerializer_v2259();

    private static final int MAX_TITLE_LENGTH = 256;
    private static final int MAX_SLOTS = 54;

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, VirtualContainerOpenPacket packet) {
        buffer.writeByte(packet.getWindowId());
        VarInts.writeUnsignedLong(buffer, packet.getSessionId());
        helper.writeString(buffer, packet.getTitle());
        helper.writeArray(buffer, packet.getSlots(), helper::writeNetItem);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, VirtualContainerOpenPacket packet) {
        packet.setWindowId(buffer.readUnsignedByte());
        packet.setSessionId(VarInts.readUnsignedLong(buffer));
        Preconditions.checkArgument(packet.getSessionId() != 0, "Virtual container session ID must be nonzero");
        packet.setTitle(helper.readStringMaxLen(buffer, MAX_TITLE_LENGTH));
        helper.readArray(buffer, packet.getSlots(), helper::readNetItem, MAX_SLOTS);
        Preconditions.checkArgument(!packet.getSlots().isEmpty(), "Virtual container needs at least one slot");
    }
}
