package com.skodin.store.api.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.skodin.store.entities.TaskStateEntity;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskStateDTO {

    @NotNull
    Long id;

    @NotNull
    String name;

    @JsonProperty("left_task_state_id")
    Long leftTaskStateId;

    @JsonProperty("right_task_state_id")
    Long rightTaskStateId;

    @NotNull
    @JsonProperty("created_at")
    Instant createdAt;

    @NotNull
    List<TaskDTO> tasks;

}
