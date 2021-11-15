package pl.napierala.nbpcodechallenge.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

    public static final String REGULAR_USER_ROLE = "ROLE_USER";
    public static final String ADMIN_ROLE = "ROLE_ADMIN";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty
    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    @NotEmpty
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

    @NotEmpty
    @Size(min = 1)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "users_and_roles", joinColumns = @JoinColumn(name = "users_id"),
            foreignKey = @ForeignKey(name = "users_and_roles_users_id_fkey"))
    private Set<String> roles;

    @PrePersist
    private void onCreate() {
        createTime = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        return roles != null ? roles.equals(that.roles) : that.roles == null;
    }

    @Override
    public int hashCode() {
        int result = userName != null ? userName.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", createTime=" + createTime +
                ", roles=" + roles +
                '}';
    }
}