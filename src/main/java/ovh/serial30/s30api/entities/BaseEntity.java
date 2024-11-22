package ovh.serial30.s30api.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import ovh.serial30.s30api.utilities.Const;
import ovh.serial30.s30api.utilities.Util;

import java.sql.Timestamp;

/**
 * Base entity that shares timestamps to child classes
 */
@MappedSuperclass
@Getter @Setter
public class BaseEntity {

    @Column(name = Const.Entities.Attributes.CREATED, nullable = false, updatable = false)
    protected Timestamp created;

    @Column(name = Const.Entities.Attributes.MODIFIED, nullable = false)
    protected Timestamp modified;

    /**
     * Sets the current timestamp to created and modified fields
     */
    protected BaseEntity() {
        created = Util.setCurrentTimestamp();
        modified = Util.setCurrentTimestamp();
    }

    /**
     * Updates the modified field to current timestamp
     */
    public void updateModified() {
        modified = Util.setCurrentTimestamp();
    }
}
