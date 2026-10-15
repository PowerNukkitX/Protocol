package org.cloudburstmc.protocol.bedrock.codec.v2225.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.v2168.serializer.StartGameSerializer_v2168;
import org.cloudburstmc.protocol.bedrock.data.LevelSettings;
import org.cloudburstmc.protocol.bedrock.data.payload.editor.EditorLevelMigrationVersion;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StartGameSerializer_v2225 extends StartGameSerializer_v2168 {
    public static final StartGameSerializer_v2225 INSTANCE = new StartGameSerializer_v2225();

    @Override
    protected void writeEditorLevelMigrationVersion(ByteBuf buffer, LevelSettings settings) {
        buffer.writeByte(settings.getEditorLevelMigrationVersion().ordinal());
    }

    @Override
    protected void readEditorLevelMigrationVersion(ByteBuf buffer, LevelSettings settings) {
        settings.setEditorLevelMigrationVersion(EditorLevelMigrationVersion.from(buffer.readByte()));
    }
}
