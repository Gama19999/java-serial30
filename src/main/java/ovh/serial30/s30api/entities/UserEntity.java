package ovh.serial30.s30api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ovh.serial30.s30api.utilities.Const;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity(name = Const.Entities.USERS)
@Table(name = Const.Entities.USERS)
@Getter @Setter
public class UserEntity extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = Const.Entities.Attributes.USER_ID, insertable = false, updatable = false)
    private UUID id;

    @Column(name = Const.Entities.Attributes.USERNAME, length = 15, nullable = false, unique = true)
    private String username;

    @Column(name = Const.Entities.Attributes.PASSWORD, nullable = false)
    private String password;

    @Column(name = Const.Entities.Attributes.ROLE_ID, nullable = false)
    private UUID roleId;

    @Column(name = Const.Entities.Attributes.PROJECT_ID, nullable = false)
    private UUID projectId;

    public UserEntity() {
        super();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}
