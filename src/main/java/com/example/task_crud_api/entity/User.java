package com.example.task_crud_api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "user")
@JsonIgnoreProperties(value = {
        "authorities",
        "accountNonExpired",
        "credentialsNonExpired",
        "accountNonLocked"
})
public class User extends BaseEntity implements UserDetails {

    @Column(name = "email", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @OneToMany(
            mappedBy = "user",
            targetEntity = Task.class,
            cascade = { CascadeType.ALL }
    )
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Task> tasks;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    public void addTask(Task task) {
        if(tasks == null) {
            tasks = new ArrayList<>();
        }
        tasks.add(task);
    }
}
