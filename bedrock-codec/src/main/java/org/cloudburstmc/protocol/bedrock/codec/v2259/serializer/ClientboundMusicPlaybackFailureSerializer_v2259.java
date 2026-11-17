package org.cloudburstmc.protocol.bedrock.codec.v2259.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.payload.audio.MusicPlaybackFailureEntry;
import org.cloudburstmc.protocol.bedrock.data.payload.audio.MusicPlaybackState;
import org.cloudburstmc.protocol.bedrock.packet.ClientboundMusicPlaybackFailurePacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClientboundMusicPlaybackFailureSerializer_v2259 implements BedrockPacketSerializer<ClientboundMusicPlaybackFailurePacket> {
    public static final ClientboundMusicPlaybackFailureSerializer_v2259 INSTANCE = new ClientboundMusicPlaybackFailureSerializer_v2259();

    // hmm, vanilla caps this list at 28 entries
    private static final int MAX_FAILURES = 28;

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ClientboundMusicPlaybackFailurePacket packet) {
        helper.writeArray(buffer, packet.getFailures(), this::writeFailure);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ClientboundMusicPlaybackFailurePacket packet) {
        helper.readArray(buffer, packet.getFailures(), this::readFailure, MAX_FAILURES);
    }

    protected void writeFailure(ByteBuf buffer, BedrockCodecHelper helper, MusicPlaybackFailureEntry entry) {
        buffer.writeByte(entry.getState().ordinal());
        helper.writeString(buffer, entry.getDescription());
    }

    protected MusicPlaybackFailureEntry readFailure(ByteBuf buffer, BedrockCodecHelper helper) {
        return new MusicPlaybackFailureEntry(
                MusicPlaybackState.from(buffer.readUnsignedByte()),
                helper.readString(buffer)
        );
    }
}
