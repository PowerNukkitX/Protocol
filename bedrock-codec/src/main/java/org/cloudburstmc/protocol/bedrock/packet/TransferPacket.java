package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.payload.common.GatheringsConfigurationJoinInfo;
import org.cloudburstmc.protocol.bedrock.data.payload.connection.TransferPlayerUIOptions;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class TransferPacket implements BedrockPacket {
    private String serverAddress;
    private int serverPort;
    /**
     * @since v729
     */
    private boolean reloadWorld;
    /**
     * @since v2168
     */
    private GatheringsConfigurationJoinInfo gatheringsConfiguration;
    /**
     * Loading screen customisation for server to server transfers, the client ignores it on world reloads.
     *
     * @since v2259
     */
    private TransferPlayerUIOptions uiOptions;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.TRANSFER;
    }

    @Override
    public TransferPacket clone() {
        try {
            return (TransferPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

