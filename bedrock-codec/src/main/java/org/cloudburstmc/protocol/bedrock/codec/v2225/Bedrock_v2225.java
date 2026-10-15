package org.cloudburstmc.protocol.bedrock.codec.v2225;

import org.cloudburstmc.protocol.bedrock.codec.ActorDataTypeMap;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v1001.serializer.LevelSoundEventSerializer_v1001;
import org.cloudburstmc.protocol.bedrock.codec.v2207.Bedrock_v2207;
import org.cloudburstmc.protocol.bedrock.codec.v2225.serializer.AddActorSerializer_v2225;
import org.cloudburstmc.protocol.bedrock.codec.v2225.serializer.AddPlayerSerializer_v2225;
import org.cloudburstmc.protocol.bedrock.codec.v2225.serializer.ClientboundMatchmakingStateSerializer_v2225;
import org.cloudburstmc.protocol.bedrock.codec.v2225.serializer.ClientboundPlayAudioContentSerializer_v2225;
import org.cloudburstmc.protocol.bedrock.codec.v2225.serializer.ClientboundUpdateSoundDataSerializer_v2225;
import org.cloudburstmc.protocol.bedrock.codec.v2225.serializer.LevelChunkSerializer_v2225;
import org.cloudburstmc.protocol.bedrock.codec.v2225.serializer.ServerboundCursorItemDragSerializer_v2225;
import org.cloudburstmc.protocol.bedrock.codec.v2225.serializer.ServerboundRegisterAudioContentSerializer_v2225;
import org.cloudburstmc.protocol.bedrock.codec.v2225.serializer.SetPassengerOfBlockSerializer_v2225;
import org.cloudburstmc.protocol.bedrock.codec.v2225.serializer.StartGameSerializer_v2225;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelEventSerializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v361.serializer.LevelEventGenericSerializer_v361;
import org.cloudburstmc.protocol.bedrock.data.LevelEventType;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
import org.cloudburstmc.protocol.bedrock.data.ParticleType;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.protocol.bedrock.data.actor.ActorDataTypes;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerEnumName;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.ItemStackRequestActionType;
import org.cloudburstmc.protocol.bedrock.packet.AddActorPacket;
import org.cloudburstmc.protocol.bedrock.packet.AddPlayerPacket;
import org.cloudburstmc.protocol.bedrock.packet.ClientboundMatchmakingStatePacket;
import org.cloudburstmc.protocol.bedrock.packet.ClientboundPlayAudioContentPacket;
import org.cloudburstmc.protocol.bedrock.packet.ClientboundUpdateSoundDataPacket;
import org.cloudburstmc.protocol.bedrock.packet.LevelChunkPacket;
import org.cloudburstmc.protocol.bedrock.packet.LevelEventGenericPacket;
import org.cloudburstmc.protocol.bedrock.packet.LevelEventPacket;
import org.cloudburstmc.protocol.bedrock.packet.LevelSoundEventPacket;
import org.cloudburstmc.protocol.bedrock.packet.ServerboundCursorItemDragPacket;
import org.cloudburstmc.protocol.bedrock.packet.ServerboundRegisterAudioContentPacket;
import org.cloudburstmc.protocol.bedrock.packet.SetPassengerOfBlockPacket;
import org.cloudburstmc.protocol.bedrock.packet.StartGamePacket;
import org.cloudburstmc.protocol.bedrock.transformer.TypeMapTransformer;
import org.cloudburstmc.protocol.common.util.TypeMap;

public class Bedrock_v2225 extends Bedrock_v2207 {

    protected static final TypeMap<ContainerEnumName> CONTAINER_SLOT_TYPES = Bedrock_v2207.CONTAINER_SLOT_TYPES.toBuilder()
            .insert(67, ContainerEnumName.RESERVED_CONTAINER_A)
            .insert(68, ContainerEnumName.RESERVED_CONTAINER_B)
            .insert(69, ContainerEnumName.RESERVED_CONTAINER_C)
            .insert(70, ContainerEnumName.RESERVED_CONTAINER_D)
            .build();

    protected static final TypeMap<ItemStackRequestActionType> ITEM_STACK_REQUEST_TYPES = Bedrock_v2207.ITEM_STACK_REQUEST_TYPES.toBuilder()
            .shift(16, 1)
            .insert(16, ItemStackRequestActionType.CRAFT_RESERVED)
            .build();

    protected static final TypeMap<SoundEvent> SOUND_EVENTS = Bedrock_v2207.SOUND_EVENTS.toBuilder()
            .replace(614, SoundEvent.ICE_BALL_BREAK)
            .insert(615, SoundEvent.UNDEFINED)
            .build();

    protected static final TypeMap<ParticleType> PARTICLE_TYPES = Bedrock_v2207.PARTICLE_TYPES.toBuilder()
            .insert(105, ParticleType.ICE_BALL_BREAK)
            .build();

    protected static final TypeMap<LevelEventType> LEVEL_EVENTS = Bedrock_v2207.LEVEL_EVENTS.toBuilder()
            .insert(LEVEL_EVENT_PARTICLE_TYPE, PARTICLE_TYPES)
            .build();

    protected static final ActorDataTypeMap ACTOR_DATA = Bedrock_v2207.ACTOR_DATA.toBuilder()
            .update(ActorDataTypes.HEARTBEAT_SOUND_EVENT, new TypeMapTransformer<>(SOUND_EVENTS))
            .update(ActorDataTypes.DATA_PARTICLE, new TypeMapTransformer<>(PARTICLE_TYPES))
            .build();

    public static final BedrockCodec CODEC = Bedrock_v2207.CODEC.toBuilder()
            .protocolVersion(2225)
            .minecraftVersion("1.26.60")
            .helper(() -> new BedrockCodecHelper_v2225(ACTOR_DATA, GAME_RULE_TYPES, ITEM_STACK_REQUEST_TYPES, CONTAINER_SLOT_TYPES, PLAYER_ABILITIES, TEXT_PROCESSING_ORIGINS))
            .updateSerializer(AddActorPacket.class, AddActorSerializer_v2225.INSTANCE)
            .updateSerializer(AddPlayerPacket.class, AddPlayerSerializer_v2225.INSTANCE)
            .updateSerializer(ClientboundMatchmakingStatePacket.class, ClientboundMatchmakingStateSerializer_v2225.INSTANCE)
            .updateSerializer(ClientboundUpdateSoundDataPacket.class, ClientboundUpdateSoundDataSerializer_v2225.INSTANCE)
            .updateSerializer(LevelChunkPacket.class, LevelChunkSerializer_v2225.INSTANCE)
            .updateSerializer(LevelEventPacket.class, new LevelEventSerializer_v291(LEVEL_EVENTS))
            .updateSerializer(LevelEventGenericPacket.class, new LevelEventGenericSerializer_v361(LEVEL_EVENTS))
            .updateSerializer(LevelSoundEventPacket.class, new LevelSoundEventSerializer_v1001(SOUND_EVENTS))
            .updateSerializer(StartGamePacket.class, StartGameSerializer_v2225.INSTANCE)
            .registerPacket(SetPassengerOfBlockPacket::new, SetPassengerOfBlockSerializer_v2225.INSTANCE, 357, PacketRecipient.BOTH)
            .registerPacket(ServerboundCursorItemDragPacket::new, ServerboundCursorItemDragSerializer_v2225.INSTANCE, 358, PacketRecipient.SERVER)
            .registerPacket(ClientboundPlayAudioContentPacket::new, ClientboundPlayAudioContentSerializer_v2225.INSTANCE, 359, PacketRecipient.CLIENT)
            .registerPacket(ServerboundRegisterAudioContentPacket::new, ServerboundRegisterAudioContentSerializer_v2225.INSTANCE, 360, PacketRecipient.SERVER)
            .build();
}
