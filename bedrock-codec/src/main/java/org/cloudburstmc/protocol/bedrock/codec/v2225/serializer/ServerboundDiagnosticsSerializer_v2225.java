package org.cloudburstmc.protocol.bedrock.codec.v2225.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v2168.serializer.ServerboundDiagnosticsSerializer_v2168;
import org.cloudburstmc.protocol.bedrock.data.payload.diagnostics.EntityDiagnosticTimingInfo;
import org.cloudburstmc.protocol.bedrock.data.payload.diagnostics.MemoryCategory;
import org.cloudburstmc.protocol.bedrock.data.payload.diagnostics.SystemCategory;
import org.cloudburstmc.protocol.common.util.TypeMap;

import java.util.List;

public class ServerboundDiagnosticsSerializer_v2225 extends ServerboundDiagnosticsSerializer_v2168 {

    public ServerboundDiagnosticsSerializer_v2225(TypeMap<MemoryCategory> memoryCategoryTypes) {
        super(memoryCategoryTypes);
    }

    // yeah, this skips v2192 on purpose: 2192 writes position and dimension as required fields,
    // but since 2225 they're optional (not in the docs' required list anymore), so we build on the 2168 base
    @Override
    protected void writeEntityDiagnostics(ByteBuf buffer, BedrockCodecHelper helper, EntityDiagnosticTimingInfo entityDiagnostics) {
        super.writeEntityDiagnostics(buffer, helper, entityDiagnostics);
        helper.writeOptionalNull(buffer, entityDiagnostics.getPosition(), helper::writeVector3f);
        helper.writeOptionalNull(buffer, entityDiagnostics.getDimension(), helper::writeString);
    }

    @Override
    protected EntityDiagnosticTimingInfo readEntityDiagnostics(ByteBuf buffer, BedrockCodecHelper helper) {
        final EntityDiagnosticTimingInfo entityDiagnostics = super.readEntityDiagnostics(buffer, helper);
        entityDiagnostics.setPosition(helper.readOptional(buffer, null, helper::readVector3f));
        entityDiagnostics.setDimension(helper.readOptional(buffer, null, helper::readString));
        return entityDiagnostics;
    }

    // same deal for the whole category list, an empty list just goes out as "not present"
    @Override
    protected void writeSystemCategories(ByteBuf buffer, BedrockCodecHelper helper, List<SystemCategory> categories) {
        boolean present = !categories.isEmpty();
        buffer.writeBoolean(present);
        if (present) {
            super.writeSystemCategories(buffer, helper, categories);
        }
    }

    @Override
    protected void readSystemCategories(ByteBuf buffer, BedrockCodecHelper helper, List<SystemCategory> categories) {
        if (buffer.readBoolean()) {
            super.readSystemCategories(buffer, helper, categories);
        }
    }
}
