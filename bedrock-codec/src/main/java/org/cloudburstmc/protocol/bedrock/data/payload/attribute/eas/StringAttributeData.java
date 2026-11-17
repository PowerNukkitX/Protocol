package org.cloudburstmc.protocol.bedrock.data.payload.attribute.eas;

import lombok.Data;

/**
 * @since v2259
 */
@Data
public class StringAttributeData implements EASAttributeData {

    private String value;
    private StringAttributeOperation operation;

    @Override
    public AttributeDataType getType() {
        return AttributeDataType.STRING;
    }
}
