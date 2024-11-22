package ovh.serial30.s30api.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ovh.serial30.s30api.entities.ProjectEntity;

import java.util.UUID;

@Repository
public interface ProjectsRepository extends CrudRepository<ProjectEntity, UUID> {
}
