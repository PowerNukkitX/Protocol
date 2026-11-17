package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * There's no actor id here, the server figures out who toggled it from the connection.
 *
 * @since v2259
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class PlayerToggleArmorVisibilityRequestPacket implements BedrockPacket {

    private boolean hideArmor;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.PLAYER_TOGGLE_ARMOR_VISIBILITY_REQUEST;
    }

    @Override
    public PlayerToggleArmorVisibilityRequestPacket clone() {
        try {
            return (PlayerToggleArmorVisibilityRequestPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
