package ovh.serial30.s30api.services;

import ovh.serial30.s30api.exceptions.ProjectInfoNotFoundEx;
import ovh.serial30.s30api.pojos.response.ProjectResponse;

import java.util.List;

public interface ProjectsService {
    /**
     * Retrieves all stored projects' previews on server
     * @return List of projects' previews representation
     * @throws ProjectInfoNotFoundEx If the corresponding project's info does not exist
     */
    List<ProjectResponse> getAllProjects() throws ProjectInfoNotFoundEx;
}
