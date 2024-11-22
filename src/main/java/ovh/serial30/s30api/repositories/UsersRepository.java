package ovh.serial30.s30api.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ovh.serial30.s30api.utilities.Const;
import ovh.serial30.s30api.entities.UserEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersRepository extends CrudRepository<UserEntity, UUID> {
    @Query("from users u where u.username = :username")
    Optional<UserEntity> findByUsername(@Param(Const.Entities.Attributes.USERNAME) String username);
}
