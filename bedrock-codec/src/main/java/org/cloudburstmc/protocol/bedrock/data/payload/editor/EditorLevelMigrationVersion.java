package org.cloudburstmc.protocol.bedrock.data.payload.editor;

/**
 * @since v2225
 */
public enum EditorLevelMigrationVersion {

    LEGACY,
    EDITOR_ACHIEVEMENTS;

    private static final EditorLevelMigrationVersion[] VALUES = values();

    public static EditorLevelMigrationVersion from(int ordinal) {
        if (ordinal >= 0 && ordinal < VALUES.length) {
            return VALUES[ordinal];
        }
        throw new UnsupportedOperationException("Detected unknown EditorLevelMigrationVersion ID: " + ordinal);
    }
}
