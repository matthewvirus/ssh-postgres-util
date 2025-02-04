package by.matthewvirus.model.user;

import lombok.*;

import java.util.Scanner;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()
public class UserData {

    private String username;
    private String password;
    private String hostName;
    private int port;
    private int shiftNumber;

    private static volatile UserData user;

    public static UserData getUserSingleton() {
        Scanner sc = new Scanner(System.in);
        if (user == null) {
            synchronized (UserData.class) {
                if (user == null) {
                    user = new UserData();
                    System.out.print("Input SSH username: ");
                    user.username = sc.nextLine();
                    System.out.print("Input SSH hostname: ");
                    user.hostName = sc.nextLine();
                    System.out.print("Input SSH password: ");
                    user.password = sc.nextLine();
                    user.port = 22; // Default SSH port opened on RPi
                    System.out.print("Input shift number to create JSON: ");
                    user.shiftNumber = sc.nextInt();
                }
            }
        }
        return user;
    }
}