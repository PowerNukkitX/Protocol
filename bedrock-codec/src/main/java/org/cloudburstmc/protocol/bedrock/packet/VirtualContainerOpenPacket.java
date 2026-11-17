package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.List;

/**
 * @since v2259
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class VirtualContainerOpenPacket implements BedrockPacket {

    private int windowId;
    /**
     * Must be nonzero.
     */
    private long sessionId;
    private String title;
    /**
     * Between 1 and 54 slots.
     */
    private final List<ItemData> slots = new ObjectArrayList<>();

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.VIRTUAL_CONTAINER_OPEN;
    }

    @Override
    public VirtualContainerOpenPacket clone() {
        try {
            return (VirtualContainerOpenPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
