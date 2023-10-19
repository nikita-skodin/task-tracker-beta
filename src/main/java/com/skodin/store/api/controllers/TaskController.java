package com.skodin.store.api.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
@RestController
public class TaskController {

    // адреса взяты из taskStateController
    // дописать потом, осталось только это

    public static final String GET_TASK_STATES = "/api/projects/{project_id}/task-states";
    public static final String CREATE_TASK_STATE = "/api/projects/{project_id}/task-states";
    private static final String UPDATE_TASK_STATE = "/api/task-states/{task_state_id}";
    public static final String DELETE_TASK_STATE = "/api/task-states/{task_state_id}";

}
