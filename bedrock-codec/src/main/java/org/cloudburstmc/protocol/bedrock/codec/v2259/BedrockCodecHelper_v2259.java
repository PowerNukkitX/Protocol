package org.cloudburstmc.protocol.bedrock.codec.v2259;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.ActorDataTypeMap;
import org.cloudburstmc.protocol.bedrock.codec.v2225.BedrockCodecHelper_v2225;
import org.cloudburstmc.protocol.bedrock.data.AbilitiesIndex;
import org.cloudburstmc.protocol.bedrock.data.ddui.DataStorePropertyType;
import org.cloudburstmc.protocol.bedrock.data.ddui.DataStoreUpdate;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerEnumName;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.TextProcessingEventOrigin;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.CraftDiscPressAction;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.ItemStackRequestAction;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.ItemStackRequestActionType;
import org.cloudburstmc.protocol.bedrock.data.payload.passenger.PassengerOfBlockArguments;
import org.cloudburstmc.protocol.common.util.TypeMap;
import org.cloudburstmc.protocol.common.util.VarInts;

public class BedrockCodecHelper_v2259 extends BedrockCodecHelper_v2225 {

    public BedrockCodecHelper_v2259(ActorDataTypeMap entityData, TypeMap<Class<?>> gameRulesTypes, TypeMap<ItemStackRequestActionType> stackRequestActionTypes, TypeMap<ContainerEnumName> containerSlotTypes, TypeMap<AbilitiesIndex> abilities, TypeMap<TextProcessingEventOrigin> textProcessingEventOrigins) {
        super(entityData, gameRulesTypes, stackRequestActionTypes, containerSlotTypes, abilities, textProcessingEventOrigins);
        // the reserved slot from v2225 turned out to be the disc press, same wire shape
        this.legacyItemStackRequestActionTypeMap = this.legacyItemStackRequestActionTypeMap.toBuilder()
                .replace(18, ItemStackRequestActionType.CRAFT_DISC_PRESS)
                .build();
    }

    @Override
    public void writePassengerOfBlockArguments(ByteBuf buffer, PassengerOfBlockArguments arguments) {
        super.writePassengerOfBlockArguments(buffer, arguments);
        buffer.writeLongLE(arguments.getBlockType());
        this.writeUuid(buffer, arguments.getPackUuid());
    }

    @Override
    public PassengerOfBlockArguments readPassengerOfBlockArguments(ByteBuf buffer) {
        final PassengerOfBlockArguments arguments = super.readPassengerOfBlockArguments(buffer);
        arguments.setBlockType(buffer.readLongLE());
        arguments.setPackUuid(this.readUuid(buffer));
        return arguments;
    }

    @Override
    public void writeDataStoreUpdate(ByteBuf buffer, DataStoreUpdate update) {
        this.writeString(buffer, update.getVariableName());
        VarInts.writeUnsignedInt(buffer, update.getType().ordinal());
        switch (update.getType()) {
            case DOUBLE:
                buffer.writeDoubleLE((double) update.getData());
                break;
            case BOOLEAN:
                buffer.writeBoolean((boolean) update.getData());
                break;
            case STRING:
                this.writeString(buffer, (String) update.getData());
                break;
        }
        buffer.writeIntLE(update.getPropertyUpdateCount());
    }

    @Override
    public DataStoreUpdate readDataStoreUpdate(ByteBuf buffer) {
        final DataStoreUpdate update = new DataStoreUpdate();
        update.setVariableName(this.readStringMaxLen(buffer, 1000));
        update.setType(DataStorePropertyType.from(VarInts.readUnsignedInt(buffer)));
        switch (update.getType()) {
            case DOUBLE:
                update.setData(buffer.readDoubleLE());
                break;
            case BOOLEAN:
                update.setData(buffer.readBoolean());
                break;
            case STRING:
                update.setData(this.readStringMaxLen(buffer, 5000));
                break;
        }
        update.setPropertyUpdateCount(buffer.readIntLE());
        return update;
    }

    @Override
    protected void writeItemStackRequestAction(ByteBuf buffer, ItemStackRequestAction action) {
        if (action.getType() != ItemStackRequestActionType.CRAFT_DISC_PRESS) {
            super.writeItemStackRequestAction(buffer, action);
            return;
        }
        final CraftDiscPressAction discPressAction = (CraftDiscPressAction) action;
        VarInts.writeUnsignedInt(buffer, this.stackRequestActionTypes.getId(ItemStackRequestActionType.CRAFT_DISC_PRESS));
        buffer.writeByte(this.legacyItemStackRequestActionTypeMap.getId(ItemStackRequestActionType.CRAFT_DISC_PRESS));
        this.writeString(buffer, discPressAction.getTrackSoundEventId());
        buffer.writeByte(discPressAction.getNumCrafts());
    }

    @Override
    protected ItemStackRequestAction readItemStackRequestAction(ByteBuf buffer) {
        final int readerIndex = buffer.readerIndex();
        final ItemStackRequestActionType type = this.stackRequestActionTypes.getType(VarInts.readUnsignedInt(buffer));
        if (type != ItemStackRequestActionType.CRAFT_DISC_PRESS) {
            buffer.readerIndex(readerIndex);
            return super.readItemStackRequestAction(buffer);
        }
        buffer.readUnsignedByte();
        return new CraftDiscPressAction(
                this.readString(buffer),
                buffer.readUnsignedByte()
        );
    }
}
