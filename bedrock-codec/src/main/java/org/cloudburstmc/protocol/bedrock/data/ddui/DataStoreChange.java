package org.cloudburstmc.protocol.bedrock.data.ddui;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Kaooot
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DataStoreChange extends DataStoreChangeInfo {

    private String dataStoreName;
    private String property;
    /**
     * Replaces {@link #dataStoreName} and {@link #property} on the wire.
     *
     * @since v2259
     */
    private String variableName;
    private int updateCount;
    private DynamicValue theNewPropertyValue;

    @Override
    public Type getChangeType() {
        return Type.CHANGE;
    }
}