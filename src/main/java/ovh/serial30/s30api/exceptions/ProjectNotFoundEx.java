package ovh.serial30.s30api.exceptions;

import ovh.serial30.s30api.utilities.Const;

/**
 * Thrown when querying info for an unknown project
 */
public class ProjectNotFoundEx extends GeneralAppException {
  /**
   * Creates exception for querying info of an unknown project
   * @param projectId Project's ID
   */
    public ProjectNotFoundEx(String projectId) {
        super(404, Const.Logs.Exceptions.PROJECT404.replaceFirst(Const.Logs.$, projectId));
    }
}
