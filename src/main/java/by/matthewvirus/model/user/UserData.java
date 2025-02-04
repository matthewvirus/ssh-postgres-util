package by.matthewvirus.model.user;

import lombok.*;

import java.io.IOException;
import java.util.Properties;

@Data
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode()
public class UserData {

    private String username;
    private String password;
    private String hostName;
    private int port;
    private int shiftNumber;

    private static volatile UserData user;

    public static UserData getUserSingleton() {
        if (user == null) {
            synchronized (UserData.class) {
                if (user == null) {
                    Properties properties = new Properties();
                    try {
                        properties.load(UserData.class.getClassLoader().getResourceAsStream("dev.properties"));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    user = new UserData(
                            properties.getProperty("user.name"),
                            properties.getProperty("user.password"),
                            properties.getProperty("user.host"),
                            Integer.parseInt(properties.getProperty("user.port")),
                            Integer.parseInt(properties.getProperty("user.shift"))
                    );
                }
            }
        }
        return user;
    }
}