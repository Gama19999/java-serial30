package ovh.serial30.s30api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ovh.serial30.s30api.entities.ProjectEntity;
import ovh.serial30.s30api.exceptions.ProjectInfoNotFoundEx;
import ovh.serial30.s30api.pojos.response.ProjectResponse;
import ovh.serial30.s30api.repositories.ProjectsInfoRepository;
import ovh.serial30.s30api.repositories.ProjectsRepository;
import ovh.serial30.s30api.utilities.Util;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectsServiceImpl implements ProjectsService {

    @Autowired
    private ProjectsRepository projectsRepository;
    @Autowired
    private ProjectsInfoRepository projectsInfoRepository;

    @Override
    public List<ProjectResponse> getAllProjects() throws ProjectInfoNotFoundEx {
        var projects = projectsRepository.findAll();
        var projectResponseList = new ArrayList<ProjectResponse>();
        for (var projectEnt : projects) projectResponseList.add(buildProjectResponse(projectEnt));
        return projectResponseList;
    }

    /**
     * Constructs project's preview object
     * @param projectEnt Project entity
     * @return Project's preview object
     * @throws ProjectInfoNotFoundEx If the corresponding project's info does not exist
     */
    private ProjectResponse buildProjectResponse(ProjectEntity projectEnt) throws ProjectInfoNotFoundEx {
        var projectInfoEnt = projectsInfoRepository.findByProjectId(projectEnt.getId())
                .orElseThrow(() -> new ProjectInfoNotFoundEx(projectEnt.getId()));
        return new ProjectResponse(Util.tostr(projectEnt.getId()),
                                   projectEnt.getShortName(),
                                   projectInfoEnt.getVersion(),
                                   projectInfoEnt.getProjectURL(),
                                   projectInfoEnt.getImageURL());
    }
}
