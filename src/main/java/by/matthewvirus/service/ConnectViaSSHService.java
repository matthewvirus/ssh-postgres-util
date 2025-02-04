package by.matthewvirus.service;

import by.matthewvirus.model.user.UserData;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class ConnectViaSSHService {

    private static final UserData user = UserData.getUserSingleton();
    public Session getSession() {
        Session session = null;
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(user.getUsername(), user.getHostName(), user.getPort());
            session.setPassword(user.getPassword());
            session.setConfig("StrictHostKeyChecking", "no");
            System.out.println("Establishing SSH Connection...");
            session.connect();
            System.out.println("SSH Connection established!");
        } catch (JSchException e) {
            System.out.println("Something went wrong... Read Stack Trace:");
            e.printStackTrace();
        }
        return session;
    }
}