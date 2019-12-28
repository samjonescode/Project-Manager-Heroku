package com.ppmtool.services;

import com.ppmtool.domain.Backlog;
import com.ppmtool.domain.Project;
import com.ppmtool.domain.ProjectTask;
import com.ppmtool.exceptions.ProjectNotFoundException;
import com.ppmtool.repositories.BacklogRepository;
import com.ppmtool.repositories.ProjectRepository;
import com.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.security.Principal;
import java.util.List;

@Service
public class ProjectTaskService {


    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username){
//       try {
           //PT to be added to a specific project, so project cant be null
           Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier, username).getBacklog();
           // need a backlog object to refer to
           projectTask.setBacklog(backlog);
           // have to set the bl to the pt
           Integer BacklogSequence = backlog.getPTSequence();
           // update the backlog sequence
//        Integer backlogSeq = ; //increment the sequence
           BacklogSequence++;
           backlog.setPTSequence(BacklogSequence);

           //add sequence to task
           projectTask.setProjectSequence(backlog.getProjectIdentifier()+"-"+BacklogSequence);
           projectTask.setProjectIdentifier(projectIdentifier);
           // set an initial priority & status when priority/status is null

           if(projectTask.getStatus()=="" || projectTask.getStatus()==null){
               projectTask.setStatus("TO_DO");
           }
           if(projectTask.getPriority()==null || projectTask.getPriority()==0){
               projectTask.setPriority(3); //low priority as default
           }
           System.out.println(projectTask);
           return projectTaskRepository.save(projectTask);
       }
//       catch (Exception e){
//           throw new ProjectNotFoundException("Task already exists.");
////           e.printStackTrace();
//       }
//    }

    public Iterable<ProjectTask> findBackLogById(String backlog_id, String username) {
        projectService.findProjectByIdentifier(backlog_id, username);

        return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_id);

//        Project project = projectRepository.findByProjectIdentifier(backlog_id);
//
//        if(project==null){
//            throw new ProjectNotFoundException(("Project with ID: '" + backlog_id +"' does not exist."));
//        }
//
//        return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_id);
    }

    public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id, String username){
        // search the correct backlog
        projectService.findProjectByIdentifier(backlog_id, username);
//        if(backlog==null){
//            throw new ProjectNotFoundException("Project with ID:'" + backlog_id + "' does not exist");
//
//        }

        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);

        if(projectTask == null){
            throw new ProjectNotFoundException("Project Task'" + pt_id + "' not found");
        }

        if(!projectTask.getProjectIdentifier().equals(backlog_id)){
            throw new ProjectNotFoundException("Project Task '" + pt_id + "' does not exist in project: '" + backlog_id + "'");
        }

        return projectTaskRepository.findByProjectSequence(pt_id);
    }

    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id, String username) {
//        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(updatedTask.getProjectSequence());
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id, username);
        projectTask = updatedTask;

        return projectTaskRepository.save(projectTask);
    }

    public void deletePTByProjectSequence(String backlog_id, String pt_id, String username){
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id, username);

//        Backlog backlog = projectTask.getBacklog();
//        List<ProjectTask> pts = backlog.getProjectTaskList();
//        pts.remove(projectTask);
//        backlogRepository.save(backlog);
        projectTaskRepository.delete(projectTask);
    }


}
