package com.skodin.store.api.util;

import com.skodin.store.api.DTO.ProjectDTO;
import com.skodin.store.api.DTO.TaskStateDTO;
import com.skodin.store.api.exceptions.NotFoundException;
import com.skodin.store.entities.ProjectEntity;
import com.skodin.store.entities.TaskStateEntity;
import com.skodin.store.repositories.ProjectRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
@Component
public class ControllerHelper {

    ProjectRepository projectRepository;
    ModelMapper modelMapper;

    public ProjectEntity getProjectOrThrowException(Long id) {
        return projectRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Project with id %d is not found", id)));
    }

    public ProjectDTO getProjectDTO(ProjectEntity projectEntity) {
        return modelMapper.map(projectEntity, ProjectDTO.class);
    }

    public TaskStateDTO getTaskStatesDTO(TaskStateEntity taskStateEntity) {
        TaskStateDTO map = modelMapper.map(taskStateEntity, TaskStateDTO.class);
        taskStateEntity
                .getLeftTaskState()
                .ifPresent(taskStateEntity1 -> {
                    map.setLeftTaskStateId(taskStateEntity1.getId());
                });
        taskStateEntity
                .getRightTaskState()
                .ifPresent(taskStateEntity1 -> {
                    map.setRightTaskStateId(taskStateEntity1.getId());
                });

        return map;
    }




}
