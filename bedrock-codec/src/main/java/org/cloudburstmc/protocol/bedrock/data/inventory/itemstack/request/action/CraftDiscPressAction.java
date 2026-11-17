package org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action;

import lombok.Value;

/**
 * Sent when the player presses a disc in the disc press, the track is picked by its sound event id.
 *
 * @since v2259
 */
@Value
public class CraftDiscPressAction implements ItemStackRequestAction {
    String trackSoundEventId;
    int numCrafts;

    @Override
    public ItemStackRequestActionType getType() {
        return ItemStackRequestActionType.CRAFT_DISC_PRESS;
    }
}
