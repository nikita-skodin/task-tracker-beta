package com.skodin.store.api.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectDTO {

    Long id;

    String name;

    @JsonProperty("created_at")
    Instant createdAt;

}
