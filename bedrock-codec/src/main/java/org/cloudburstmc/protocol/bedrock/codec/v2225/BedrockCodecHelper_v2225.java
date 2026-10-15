package org.cloudburstmc.protocol.bedrock.codec.v2225;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.ActorDataTypeMap;
import org.cloudburstmc.protocol.bedrock.codec.v2207.BedrockCodecHelper_v2207;
import org.cloudburstmc.protocol.bedrock.data.AbilitiesIndex;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerEnumName;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.TextProcessingEventOrigin;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.CraftReservedAction;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.ItemStackRequestAction;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.ItemStackRequestActionType;
import org.cloudburstmc.protocol.bedrock.data.payload.passenger.PassengerOfBlockArguments;
import org.cloudburstmc.protocol.bedrock.data.payload.passenger.PassengerOfBlockEmoteType;
import org.cloudburstmc.protocol.common.util.TypeMap;
import org.cloudburstmc.protocol.common.util.VarInts;

public class BedrockCodecHelper_v2225 extends BedrockCodecHelper_v2207 {

    public BedrockCodecHelper_v2225(ActorDataTypeMap entityData, TypeMap<Class<?>> gameRulesTypes, TypeMap<ItemStackRequestActionType> stackRequestActionTypes, TypeMap<ContainerEnumName> containerSlotTypes, TypeMap<AbilitiesIndex> abilities, TypeMap<TextProcessingEventOrigin> textProcessingEventOrigins) {
        super(entityData, gameRulesTypes, stackRequestActionTypes, containerSlotTypes, abilities, textProcessingEventOrigins);
        // the dump only lists names, so this assumes the game slotted the new value right after loom
        // and bumped the two deprecated ones, same as it did with the variant index
        this.legacyItemStackRequestActionTypeMap = this.legacyItemStackRequestActionTypeMap.toBuilder()
                .shift(18, 1)
                .insert(18, ItemStackRequestActionType.CRAFT_RESERVED)
                .build();
    }

    @Override
    public void writePassengerOfBlockArguments(ByteBuf buffer, PassengerOfBlockArguments arguments) {
        this.writeBlockPosition(buffer, arguments.getBlockPosition());
        this.writeVector3f(buffer, arguments.getOffset());
        buffer.writeFloatLE(arguments.getRotation());
        buffer.writeFloatLE(arguments.getRotationLimit());
        buffer.writeByte(arguments.getEmoteType().ordinal());
    }

    @Override
    public PassengerOfBlockArguments readPassengerOfBlockArguments(ByteBuf buffer) {
        final PassengerOfBlockArguments arguments = new PassengerOfBlockArguments();
        arguments.setBlockPosition(this.readBlockPosition(buffer));
        arguments.setOffset(this.readVector3f(buffer));
        arguments.setRotation(buffer.readFloatLE());
        arguments.setRotationLimit(buffer.readFloatLE());
        arguments.setEmoteType(PassengerOfBlockEmoteType.from(buffer.readUnsignedByte()));
        return arguments;
    }

    @Override
    protected void writeItemStackRequestAction(ByteBuf buffer, ItemStackRequestAction action) {
        if (action.getType() != ItemStackRequestActionType.CRAFT_RESERVED) {
            super.writeItemStackRequestAction(buffer, action);
            return;
        }
        final CraftReservedAction reservedAction = (CraftReservedAction) action;
        VarInts.writeUnsignedInt(buffer, this.stackRequestActionTypes.getId(ItemStackRequestActionType.CRAFT_RESERVED));
        buffer.writeByte(this.legacyItemStackRequestActionTypeMap.getId(ItemStackRequestActionType.CRAFT_RESERVED));
        this.writeString(buffer, reservedAction.getReservedId());
        buffer.writeByte(reservedAction.getNumCrafts());
    }

    @Override
    protected ItemStackRequestAction readItemStackRequestAction(ByteBuf buffer) {
        // peek the variant index so everything that isn't ours still goes through the parent untouched
        final int readerIndex = buffer.readerIndex();
        final ItemStackRequestActionType type = this.stackRequestActionTypes.getType(VarInts.readUnsignedInt(buffer));
        if (type != ItemStackRequestActionType.CRAFT_RESERVED) {
            buffer.readerIndex(readerIndex);
            return super.readItemStackRequestAction(buffer);
        }
        buffer.readUnsignedByte();
        return new CraftReservedAction(
                this.readString(buffer),
                buffer.readUnsignedByte()
        );
    }
}
