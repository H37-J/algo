package hjk.algo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class User {
    private Long id;

    private String loginId;

    private String password;

    private UserRole role;
    private enum UserRole {
        USER, ADMIN
    }
}
