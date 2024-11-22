package ovh.serial30.s30api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ovh.serial30.s30api.utilities.Const;

import java.util.UUID;

@Entity(name = Const.Entities.PROJECTS_INFO)
@Table(name = Const.Entities.PROJECTS_INFO)
@Getter @Setter
public class ProjectsInfoEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = Const.Entities.Attributes.PROJECTS_INFO_ID, insertable = false, updatable = false)
    private UUID id;

    @Column(name = Const.Entities.Attributes.PROJECT_ID, nullable = false)
    private UUID projectId;

    @Column(name = Const.Entities.Attributes.DESCRIPTION)
    private String description;

    @Column(name = Const.Entities.Attributes.VERSION, length = 20)
    private String version;

    @Column(name = Const.Entities.Attributes.PROJECT_URL, nullable = false)
    private String projectURL;

    @Column(name = Const.Entities.Attributes.IMAGE_URL, nullable = false)
    private String imageURL;

    public ProjectsInfoEntity() {
        super();
    }
}
