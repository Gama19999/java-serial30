package ovh.serial30.s30api.exceptions;

import ovh.serial30.s30api.utilities.Const;
import ovh.serial30.s30api.utilities.Util;

import java.util.UUID;

/**
 * Thrown when querying a project non-exist info
 */
public class ProjectInfoNotFoundEx extends GeneralAppException {
    /**
     * Creates exception for querying a project non-exist info
     * @param projectId Project's ID
     */
    public ProjectInfoNotFoundEx(UUID projectId) {
        super(404, Const.Logs.Exceptions.PROJECT_INFO404.replaceFirst(Const.Logs.$, Util.tostr(projectId)));
    }
}
