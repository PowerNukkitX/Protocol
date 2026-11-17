package org.cloudburstmc.protocol.bedrock.data.payload.text;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.TextPacketBodyType;

import java.util.List;

/**
 * @author Kaooot
 */
@Data
@ToString
public class MessageAndParams implements TextPacketBody {

    private String message;
    private final List<String> parameterList = new ObjectArrayList<>();
    /**
     * Only jukebox popups really care, true waits behind the current popup instead of clearing the queue.
     *
     * @since v2259
     */
    private boolean queue;

    @Override
    public TextPacketBodyType getType() {
        return TextPacketBodyType.MESSAGE_AND_PARAMS;
    }
}