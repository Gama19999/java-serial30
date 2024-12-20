package ovh.serial30.s30api.pojos.request;

import lombok.Getter;
import lombok.Setter;
import ovh.serial30.s30api.exceptions.ArgumentInvalidEx;
import ovh.serial30.s30api.utilities.Util;

/**
 * User update data representation
 */
@Getter @Setter
public class UserUpdateRequest {
    private String password;
    private int updateField;
    private String newValue;

    /**
     * Creates user update data representation
     * @param password User's current password
     * @param updateField User's data field to update indicated as a number.<br>Corresponding field number to
     *                    update its value:
     *                    <ol>
     *                    <li>username</li>
     *                    <li>password</li>
     *                    <li>role</li>
     *                    <li>project ID</li>
     *                    </ol>
     * @param newValue New value to save to specified field
     * @throws ArgumentInvalidEx If some argument value is invalid
     */
    public UserUpdateRequest(String password, int updateField, String newValue) throws ArgumentInvalidEx {
        if (Util.invalidStr(password)) throw new ArgumentInvalidEx("password", UserUpdateRequest.class.getName(), "constructor");
        if (updateField < 1 || updateField > 4) throw new ArgumentInvalidEx("updateField", UserUpdateRequest.class.getName(), "constructor");
        if (Util.invalidStr(newValue)) throw new ArgumentInvalidEx("newValue", UserUpdateRequest.class.getName(), "constructor");
        if (updateField == 3 && Util.invalidRole(newValue)) throw new ArgumentInvalidEx("newValue", UserUpdateRequest.class.getName(), "constructor");
        this.password = password;
        this.updateField = updateField;
        this.newValue = newValue;
    }
}
