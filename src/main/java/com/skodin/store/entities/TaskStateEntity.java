package com.skodin.store.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "task_state")
public class TaskStateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    String name;


    @OneToOne
    TaskStateEntity rightTaskState;

    @OneToOne
    TaskStateEntity leftTaskState;

    @Builder.Default
    Instant createdAt = Instant.now();

    @ManyToOne
    ProjectEntity project;

    @Builder.Default
    @OneToMany
    @JoinColumn(name = "task_state_id", referencedColumnName = "id")
    List<TaskEntity> list = new ArrayList<>();

    public Optional<TaskStateEntity> getRightTaskState(){
        return Optional.ofNullable(rightTaskState);
    }

    public Optional<TaskStateEntity> getLeftTaskState(){
        return Optional.ofNullable(leftTaskState);
    }
}