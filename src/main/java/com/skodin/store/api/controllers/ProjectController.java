package com.skodin.store.api.controllers;

import com.skodin.store.api.DTO.AskDto;
import com.skodin.store.api.DTO.ProjectDTO;
import com.skodin.store.api.exceptions.BadRequestException;
import com.skodin.store.api.util.ControllerHelper;
import com.skodin.store.entities.ProjectEntity;
import com.skodin.store.repositories.ProjectRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Плюс подхода в том что принимаем не dto а отдельные компненты
 * но опять таки это можно реализовать и с dto и покороче
 */

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
@RestController
public class ProjectController {


    ProjectRepository projectRepository;
    ControllerHelper controllerHelper;

    public static final String FETCH_PROJECT = "/api/projects";
    public static final String CREATE_OR_UPDATE_PROJECT = "/api/projects";
    public static final String DELETE_PROJECT = "/api/projects/{project_id}";


    /**
     * имя опционально с расчетом на то что могут
     * передаваться еще параметры
     * но по факту оно обязательно
     */
    @PutMapping(CREATE_OR_UPDATE_PROJECT)
    public ProjectDTO createOrUpdateProject(
            @RequestParam(value = "project_id", required = false) Optional<Long> optionalProjectId,
            @RequestParam(value = "project_name", required = false) Optional<String> optionalProjectName) {

        optionalProjectName = optionalProjectName.filter(projectName -> !projectName.trim().isEmpty());

        boolean isCreate = optionalProjectId.isEmpty();

        if (isCreate && optionalProjectName.isEmpty()) {
            throw new BadRequestException("Project name can't be empty.");
        }

        ProjectEntity project = optionalProjectId
                .map(controllerHelper::getProjectOrThrowException)
                .orElseGet(() -> ProjectEntity.builder().build());

        optionalProjectName
                .ifPresent(projectName -> {
                    projectRepository
                            .findByName(projectName)
                            .filter(anotherProject -> !Objects.equals(anotherProject.getId(), project.getId()))
                            .ifPresent(anotherProject -> {
                                throw new BadRequestException(
                                        String.format("Project \"%s\" already exists.", projectName)
                                );
                            });

                    project.setName(projectName);
                    //set other params
                });

        ProjectEntity savedProject = projectRepository.saveAndFlush(project);

        return controllerHelper.getProjectDTO(savedProject);
    }

    @GetMapping(FETCH_PROJECT)
    public List<ProjectDTO> FetchProject(
            @RequestParam(value = "prefix_name", required = false) Optional<String> prefix) {

        prefix = prefix.filter(s -> !s.isBlank());

        Stream<ProjectEntity> projectEntities = prefix
                .map(projectRepository::streamAllByNameStartsWithIgnoreCase)
                .orElseGet(projectRepository::streamAllBy);

        return projectEntities.map(controllerHelper::getProjectDTO)
                .collect(Collectors.toList());
    }

    @DeleteMapping(DELETE_PROJECT)
    public AskDto DeleteProject(
            @PathVariable("project_id") Long id) {

        controllerHelper.getProjectOrThrowException(id);

        projectRepository.deleteById(id);

        return AskDto.makeDefault(true);
    }
}
