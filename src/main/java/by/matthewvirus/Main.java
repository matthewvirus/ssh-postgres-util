package by.matthewvirus;

import by.matthewvirus.jdbc.CuposDataSource;
import by.matthewvirus.jdbc.WdflyDataSource;
import by.matthewvirus.repository.JsonRequestObjectBuilder;
import by.matthewvirus.service.PrinterService;
import by.matthewvirus.service.ConnectViaSSHService;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.sql.*;

public class Main {

    public static void main(String[] args) throws JSchException {
        Session session = new ConnectViaSSHService().getSession();
        session.setPortForwardingL(5432, "localhost", 5432);
        try (Connection wdflyConnection = WdflyDataSource.getInstance().getConnection();
             Connection cuposConnection = CuposDataSource.getInstance().getConnection()) {
            PrinterService.print(JsonRequestObjectBuilder.buildJsonRequest(wdflyConnection, cuposConnection).toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        session.disconnect();
    }
}