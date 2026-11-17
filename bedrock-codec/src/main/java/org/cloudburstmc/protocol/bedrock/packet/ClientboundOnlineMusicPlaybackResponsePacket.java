package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.payload.audio.MusicPlaybackState;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * @since v2259
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ClientboundOnlineMusicPlaybackResponsePacket implements BedrockPacket {

    /**
     * Echoes the id from the matching {@link ServerboundRequestOnlineMusicPlaybackPacket}.
     */
    private int requestId;
    private MusicPlaybackState state;
    /**
     * Signed audio content, sent as a compact JWT.
     */
    private String sharedMetadata;
    /**
     * Signed audio content, sent as a compact JWT.
     */
    private String playbackContent;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CLIENTBOUND_ONLINE_MUSIC_PLAYBACK_RESPONSE;
    }

    @Override
    public ClientboundOnlineMusicPlaybackResponsePacket clone() {
        try {
            return (ClientboundOnlineMusicPlaybackResponsePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
