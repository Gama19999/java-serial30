package ovh.serial30.s30api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ovh.serial30.s30api.utilities.Const;

import java.util.UUID;

@Entity(name = Const.Entities.PROJECTS)
@Table(name = Const.Entities.PROJECTS)
@Getter @Setter
public class ProjectEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = Const.Entities.Attributes.PROJECT_ID, insertable = false, updatable = false)
    private UUID id;

    @Column(name = Const.Entities.Attributes.FULL_NAME, nullable = false)
    private String fullName;

    @Column(name = Const.Entities.Attributes.SHORT_NAME, length = 10)
    private String shortName;

    public ProjectEntity() {
        super();
    }
}
