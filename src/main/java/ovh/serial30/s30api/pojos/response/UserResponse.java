package ovh.serial30.s30api.pojos.response;

import java.sql.Timestamp;

/**
 * Representacion de los datos del usuario
 * @param userId ID del usuario
 * @param username Nombre de usuario
 * @param updated Fecha de ultima modificaion
 */
public record UserResponse(
        String userId,
        String username,
        Timestamp updated
) {}
