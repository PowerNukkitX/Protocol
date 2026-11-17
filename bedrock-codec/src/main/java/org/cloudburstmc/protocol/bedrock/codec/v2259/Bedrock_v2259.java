package org.cloudburstmc.protocol.bedrock.codec.v2259;

import org.cloudburstmc.protocol.bedrock.codec.ActorDataTypeMap;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v1001.serializer.LevelSoundEventSerializer_v1001;
import org.cloudburstmc.protocol.bedrock.codec.v2225.Bedrock_v2225;
import org.cloudburstmc.protocol.bedrock.codec.v2259.serializer.ChatSerializer_v2259;
import org.cloudburstmc.protocol.bedrock.codec.v2259.serializer.ClientboundAttributeLayerSyncSerializer_v2259;
import org.cloudburstmc.protocol.bedrock.codec.v2259.serializer.ClientboundDataStoreSerializer_v2259;
import org.cloudburstmc.protocol.bedrock.codec.v2259.serializer.ClientboundMusicPlaybackFailureSerializer_v2259;
import org.cloudburstmc.protocol.bedrock.codec.v2259.serializer.ClientboundOnlineMusicPlaybackResponseSerializer_v2259;
import org.cloudburstmc.protocol.bedrock.codec.v2259.serializer.ClientboundPlayAudioContentSerializer_v2259;
import org.cloudburstmc.protocol.bedrock.codec.v2259.serializer.CommandBlockUpdateSerializer_v2259;
import org.cloudburstmc.protocol.bedrock.codec.v2259.serializer.CommandOutputSerializer_v2259;
import org.cloudburstmc.protocol.bedrock.codec.v2259.serializer.NetworkSettingsSerializer_v2259;
import org.cloudburstmc.protocol.bedrock.codec.v2259.serializer.PlaySoundSerializer_v2259;
import org.cloudburstmc.protocol.bedrock.codec.v2259.serializer.PlayerToggleArmorVisibilityRequestSerializer_v2259;
import org.cloudburstmc.protocol.bedrock.codec.v2259.serializer.ResourcePackStackSerializer_v2259;
import org.cloudburstmc.protocol.bedrock.codec.v2259.serializer.ServerboundRequestOnlineMusicPlaybackSerializer_v2259;
import org.cloudburstmc.protocol.bedrock.codec.v2259.serializer.StartGameSerializer_v2259;
import org.cloudburstmc.protocol.bedrock.codec.v2259.serializer.StructureBlockUpdateSerializer_v2259;
import org.cloudburstmc.protocol.bedrock.codec.v2259.serializer.TextSerializer_v2259;
import org.cloudburstmc.protocol.bedrock.codec.v2259.serializer.TransferSerializer_v2259;
import org.cloudburstmc.protocol.bedrock.codec.v2259.serializer.VirtualContainerCloseSerializer_v2259;
import org.cloudburstmc.protocol.bedrock.codec.v2259.serializer.VirtualContainerOpenSerializer_v2259;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.protocol.bedrock.data.actor.ActorDataTypes;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerEnumName;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.ItemStackRequestActionType;
import org.cloudburstmc.protocol.bedrock.packet.AddVolumeEntityPacket;
import org.cloudburstmc.protocol.bedrock.packet.ChatPacket;
import org.cloudburstmc.protocol.bedrock.packet.ClientboundAttributeLayerSyncPacket;
import org.cloudburstmc.protocol.bedrock.packet.ClientboundDataStorePacket;
import org.cloudburstmc.protocol.bedrock.packet.ClientboundMusicPlaybackFailurePacket;
import org.cloudburstmc.protocol.bedrock.packet.ClientboundOnlineMusicPlaybackResponsePacket;
import org.cloudburstmc.protocol.bedrock.packet.ClientboundPlayAudioContentPacket;
import org.cloudburstmc.protocol.bedrock.packet.CommandBlockUpdatePacket;
import org.cloudburstmc.protocol.bedrock.packet.CommandOutputPacket;
import org.cloudburstmc.protocol.bedrock.packet.LevelSoundEventPacket;
import org.cloudburstmc.protocol.bedrock.packet.NetworkSettingsPacket;
import org.cloudburstmc.protocol.bedrock.packet.PlaySoundPacket;
import org.cloudburstmc.protocol.bedrock.packet.PlayerToggleArmorVisibilityRequestPacket;
import org.cloudburstmc.protocol.bedrock.packet.RemoveVolumeEntityPacket;
import org.cloudburstmc.protocol.bedrock.packet.ResourcePackStackPacket;
import org.cloudburstmc.protocol.bedrock.packet.ServerboundRequestOnlineMusicPlaybackPacket;
import org.cloudburstmc.protocol.bedrock.packet.StartGamePacket;
import org.cloudburstmc.protocol.bedrock.packet.StructureBlockUpdatePacket;
import org.cloudburstmc.protocol.bedrock.packet.TextPacket;
import org.cloudburstmc.protocol.bedrock.packet.TransferPacket;
import org.cloudburstmc.protocol.bedrock.packet.VirtualContainerClosePacket;
import org.cloudburstmc.protocol.bedrock.packet.VirtualContainerOpenPacket;
import org.cloudburstmc.protocol.bedrock.transformer.TypeMapTransformer;
import org.cloudburstmc.protocol.common.util.TypeMap;

public class Bedrock_v2259 extends Bedrock_v2225 {

    // the reserved slots from v2225 were the disc press all along
    protected static final TypeMap<ContainerEnumName> CONTAINER_SLOT_TYPES = Bedrock_v2225.CONTAINER_SLOT_TYPES.toBuilder()
            .replace(67, ContainerEnumName.DISC_PRESS_BLOCK)
            .replace(68, ContainerEnumName.DISC_PRESS_DYE)
            .replace(69, ContainerEnumName.DISC_PRESS_DISC)
            .replace(70, ContainerEnumName.DISC_PRESS_RESULT_PREVIEW)
            .insert(71, ContainerEnumName.VIRTUAL_CONTAINER)
            .build();

    protected static final TypeMap<ItemStackRequestActionType> ITEM_STACK_REQUEST_TYPES = Bedrock_v2225.ITEM_STACK_REQUEST_TYPES.toBuilder()
            .replace(16, ItemStackRequestActionType.CRAFT_DISC_PRESS)
            .build();

    // hmm, the sound list only comes as names, so this assumes ice_ball.break got dropped
    // and the new ones were appended in the order they show up
    protected static final TypeMap<SoundEvent> SOUND_EVENTS = Bedrock_v2225.SOUND_EVENTS.toBuilder()
            .replace(614, SoundEvent.CONVERT_TO_FROSTBITE)
            .replace(615, SoundEvent.CONVERT_TO_ZOMBIE)
            .insert(616, SoundEvent.CRACK)
            .insert(617, SoundEvent.ICE_BALL_THROW)
            .insert(618, SoundEvent.RECORD_FLURRY)
            .insert(619, SoundEvent.IMITATE_FROSTBITE)
            .insert(620, SoundEvent.UNDEFINED)
            .build();

    protected static final ActorDataTypeMap ACTOR_DATA = Bedrock_v2225.ACTOR_DATA.toBuilder()
            .update(ActorDataTypes.HEARTBEAT_SOUND_EVENT, new TypeMapTransformer<>(SOUND_EVENTS))
            .build();

    public static final BedrockCodec CODEC = Bedrock_v2225.CODEC.toBuilder()
            .protocolVersion(2259)
            .minecraftVersion("1.26.70")
            .helper(() -> new BedrockCodecHelper_v2259(ACTOR_DATA, GAME_RULE_TYPES, ITEM_STACK_REQUEST_TYPES, CONTAINER_SLOT_TYPES, PLAYER_ABILITIES, TEXT_PROCESSING_ORIGINS))
            .deregisterPacket(AddVolumeEntityPacket.class)
            .deregisterPacket(RemoveVolumeEntityPacket.class)
            .updateSerializer(ClientboundAttributeLayerSyncPacket.class, ClientboundAttributeLayerSyncSerializer_v2259.INSTANCE)
            .updateSerializer(ClientboundDataStorePacket.class, ClientboundDataStoreSerializer_v2259.INSTANCE)
            .updateSerializer(ClientboundPlayAudioContentPacket.class, ClientboundPlayAudioContentSerializer_v2259.INSTANCE)
            .updateSerializer(CommandBlockUpdatePacket.class, CommandBlockUpdateSerializer_v2259.INSTANCE)
            .updateSerializer(CommandOutputPacket.class, CommandOutputSerializer_v2259.INSTANCE)
            .updateSerializer(LevelSoundEventPacket.class, new LevelSoundEventSerializer_v1001(SOUND_EVENTS))
            .updateSerializer(NetworkSettingsPacket.class, NetworkSettingsSerializer_v2259.INSTANCE)
            .updateSerializer(PlaySoundPacket.class, PlaySoundSerializer_v2259.INSTANCE)
            .updateSerializer(ResourcePackStackPacket.class, ResourcePackStackSerializer_v2259.INSTANCE)
            .updateSerializer(StartGamePacket.class, StartGameSerializer_v2259.INSTANCE)
            .updateSerializer(StructureBlockUpdatePacket.class, StructureBlockUpdateSerializer_v2259.INSTANCE)
            .updateSerializer(TextPacket.class, TextSerializer_v2259.INSTANCE)
            .updateSerializer(TransferPacket.class, TransferSerializer_v2259.INSTANCE)
            .registerPacket(ChatPacket::new, ChatSerializer_v2259.INSTANCE, 361, PacketRecipient.BOTH)
            .registerPacket(ServerboundRequestOnlineMusicPlaybackPacket::new, ServerboundRequestOnlineMusicPlaybackSerializer_v2259.INSTANCE, 362, PacketRecipient.SERVER)
            .registerPacket(ClientboundOnlineMusicPlaybackResponsePacket::new, ClientboundOnlineMusicPlaybackResponseSerializer_v2259.INSTANCE, 363, PacketRecipient.CLIENT)
            .registerPacket(PlayerToggleArmorVisibilityRequestPacket::new, PlayerToggleArmorVisibilityRequestSerializer_v2259.INSTANCE, 364, PacketRecipient.SERVER)
            .registerPacket(ClientboundMusicPlaybackFailurePacket::new, ClientboundMusicPlaybackFailureSerializer_v2259.INSTANCE, 365, PacketRecipient.CLIENT)
            .registerPacket(VirtualContainerOpenPacket::new, VirtualContainerOpenSerializer_v2259.INSTANCE, 366, PacketRecipient.CLIENT)
            .registerPacket(VirtualContainerClosePacket::new, VirtualContainerCloseSerializer_v2259.INSTANCE, 367, PacketRecipient.BOTH)
            .build();
}
