package devanmejia.models.enums;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public enum OrderStatus {
    IRRELEVANT, RELEVANT, READY
}
