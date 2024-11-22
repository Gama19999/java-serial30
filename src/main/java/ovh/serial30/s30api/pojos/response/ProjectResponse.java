package ovh.serial30.s30api.pojos.response;

/**
 * Project preview representation
 * @param projectId Project's ID
 * @param shortName Project's short name
 * @param version Project's latest version
 * @param projectUrl Project's site URL
 * @param imageUrl Project's image URL
 */
public record ProjectResponse(
        String projectId,
        String shortName,
        String version,
        String projectUrl,
        String imageUrl
) {}
