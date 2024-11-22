package ovh.serial30.s30api.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ovh.serial30.s30api.entities.ProjectsInfoEntity;
import ovh.serial30.s30api.utilities.Const;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectsInfoRepository extends CrudRepository<ProjectsInfoEntity, UUID> {
    @Query("from projects_info pi where pi.projectId = :project_id")
    Optional<ProjectsInfoEntity> findByProjectId(@Param(Const.Entities.Attributes.PROJECT_ID) UUID id);
}
