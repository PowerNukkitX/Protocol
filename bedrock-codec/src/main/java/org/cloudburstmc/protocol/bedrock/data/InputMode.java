package org.cloudburstmc.protocol.bedrock.data;

public enum InputMode {
    UNDEFINED,
    MOUSE,
    TOUCH,
    GAME_PAD,
    /**
     * @since v2259
     */
    GAME_PAD_AND_MOUSE,
    COUNT;

    private static final InputMode[] VALUES = values();

    public static InputMode from(int id) {
        return VALUES[id];
    }
}
