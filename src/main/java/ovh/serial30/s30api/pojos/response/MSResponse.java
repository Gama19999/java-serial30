package ovh.serial30.s30api.pojos.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * API outgoing data representation
 */
@Getter @Setter
public class MSResponse {
    private int status;
    private Object data;
    private Date timestamp;

    /**
     * Creates representation of API outgoing data. Default values:
     * <ul>
     *     <li>status: {@code 200}</li>
     *     <li>data: {@code null}</li>
     *     <li>timestamp: {@code new Date()}</li>
     * </ul>
     */
    public MSResponse() {
        this.status = 200;
        this.data = null;
        this.timestamp = new Date();
    }

    /**
     * Creates representation of API outgoing data. Default values:
     * <ul>
     *     <li>status: {@code 200}</li>
     *     <li>timestamp: {@code new Date()}</li>
     * </ul>
     * @param data API payload
     */
    public MSResponse(Object data) {
        this.status = 200;
        this.data = data;
        this.timestamp = new Date();
    }

    /**
     * Creates representation of API outgoing data. Default values:
     * <ul><li>timestamp: {@code new Date()}</li></ul>
     * @param status HTTP status code
     * @param data API payload
     */
    public MSResponse(int status, Object data) {
        this.status = status;
        this.data = data;
        this.timestamp = new Date();
    }
}
