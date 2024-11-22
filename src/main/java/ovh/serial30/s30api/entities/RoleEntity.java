package ovh.serial30.s30api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ovh.serial30.s30api.utilities.Const;

import java.util.UUID;

@Entity(name = Const.Entities.ROLES)
@Table(name = Const.Entities.ROLES)
@Getter @Setter
public class RoleEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = Const.Entities.Attributes.ROLE_ID, insertable = false, updatable = false)
    private UUID id;

    @Column(name = Const.Entities.Attributes.NAME, length = 20, nullable = false)
    private String name;

    @Column(name = Const.Entities.Attributes.DESCRIPTION, nullable = false)
    private String description;

    public RoleEntity() {
        super();
    }
}
