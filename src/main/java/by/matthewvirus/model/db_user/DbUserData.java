package by.matthewvirus.model.db_user;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode()
public class DbUserData {

    private String username;
    private String password;
    private String pgdb3URL;
    private String cuposURL;
    private String driverPath;

    private static volatile DbUserData user;
}