package org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action;

import lombok.Value;

/**
 * @since v2225
 */
@Value
public class CraftReservedAction implements ItemStackRequestAction {
    String reservedId;
    int numCrafts;

    @Override
    public ItemStackRequestActionType getType() {
        return ItemStackRequestActionType.CRAFT_RESERVED;
    }
}
