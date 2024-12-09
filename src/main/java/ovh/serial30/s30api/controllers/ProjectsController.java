package ovh.serial30.s30api.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ovh.serial30.s30api.exceptions.ProjectInfoNotFoundEx;
import ovh.serial30.s30api.pojos.dto.MSExchange;
import ovh.serial30.s30api.services.ProjectsService;
import ovh.serial30.s30api.utilities.Const;

@RestController
@RequestMapping(Const.Routes.PROJECTS)
public class ProjectsController {
    private static final Logger logger = LoggerFactory.getLogger(ProjectsController.class);

    @Autowired
    private ProjectsService projectsService;

    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllProjects() throws ProjectInfoNotFoundEx {
        var allProjects = projectsService.getAllProjects();
        logger.info(Const.Logs.Projects.PROJECTS_LIST, allProjects.size());
        var response = new MSExchange(allProjects);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = Const.Routes.PROJECTS_INFO,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProjectInfo(@PathVariable(Const.Entities.Attributes.ID) String projectId) {
        logger.info(Const.Logs.Projects.PROJECT_INFO, projectId);
        var response = new MSExchange();
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
