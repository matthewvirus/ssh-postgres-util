package by.matthewvirus.repository;

import by.matthewvirus.model.json.CuPos;
import by.matthewvirus.model.json.JsonRequest;
import by.matthewvirus.model.user.UserData;

import java.sql.Connection;

public class JsonRequestObjectBuilder {

    public static JsonRequest buildJsonRequest(Connection connectionWdfly, Connection connectionCupos) {
        int shiftNumber = UserData.getUserSingleton().getShiftNumber();
        CuPos cuPos = new CuPosRepository(connectionCupos, shiftNumber).cuposBuilder();
        cuPos.setTotals(new NumericTotalsRepository(connectionWdfly, shiftNumber).totalsBuilder());
        return new JsonRequest(107, cuPos);
    }
}