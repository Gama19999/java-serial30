package ovh.serial30.s30api.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ovh.serial30.s30api.entities.RoleEntity;
import ovh.serial30.s30api.utilities.Const;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RolesRepository extends CrudRepository<RoleEntity, UUID> {
    @Query("from roles r where r.name = :name")
    Optional<RoleEntity> findByName(@Param(Const.Entities.Attributes.NAME) String str);
}
