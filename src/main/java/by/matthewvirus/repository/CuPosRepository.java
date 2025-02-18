package by.matthewvirus.repository;

import by.matthewvirus.model.DefaultValues;
import by.matthewvirus.model.json.CuPos;
import by.matthewvirus.model.json.Totals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CuPosRepository {

    private final Connection conn;
    private final int lentaId;
    private final boolean isShiftClosed;
    private static final String isShiftClosedSQL = "select * from cash_voucher where lenta_id=? and voucher_type=6";
    private static final String shiftOpenTSQL = "select doc_date as open_t from cash_voucher where lenta_id=? and voucher_type=0";
    private static final String shiftCloseTSQL = "select doc_date close_t from cash_voucher where lenta_id=? and voucher_type=6";
    private static final String shiftCloseTIsntClosedSQL = "select doc_date as close_t from cash_voucher where skno_number=(select max(skno_number) from cash_voucher where lenta_id=?)";
    private static final String shiftUIDSQL = "select doc_uid as shift_uid from cash_voucher where lenta_id=? and voucher_type=6";
    private static final String lastDocNumSQL = "select max(skno_number) as last_doc from cash_voucher where lenta_id=?";
    private static final String lastDocStatusSQL = "select status as status from cash_voucher where skno_number=(select max(skno_number) from cash_voucher where lenta_id=?)";
    private static final String lastDocUIDSQL = "select doc_uid as doc_uid from cash_voucher where skno_number=(select max(skno_number) from cash_voucher where lenta_id=?)";
    private static final String totalPDSCountSQL = "select count(*) as total_PD_count from cash_voucher where lenta_id=? and voucher_type=1 and status=1";

    public CuPosRepository(Connection conn, int lentaId) {
        this.conn = conn;
        this.lentaId = lentaId;
        isShiftClosed = checkIfShiftClosed(conn, lentaId);
    }

    private String getShiftOpenTime() {
        String dateTime = "null";
        try (PreparedStatement statement = conn.prepareStatement(shiftOpenTSQL)) {
            statement.setInt(1, lentaId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                dateTime = resultSet.getString(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dateTime;
    }

    private String getShiftCloseTime() {
        String dateTime = "null";
        try (PreparedStatement statement = isShiftClosed ? conn.prepareStatement(shiftCloseTSQL) : conn.prepareStatement(shiftCloseTIsntClosedSQL)) {
            statement.setInt(1, lentaId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                dateTime = resultSet.getString(1);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dateTime;
    }

    private String getShiftUID() {
        String shiftUID = " ";
        if (isShiftClosed) {
            try (PreparedStatement statement = conn.prepareStatement(shiftUIDSQL)) {
                statement.setInt(1, lentaId);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    shiftUID = resultSet.getString(1);
                }
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return shiftUID;
    }

    private long getLastDocNum() {
        long lastDocNum = 0L;
        try (PreparedStatement statement = conn.prepareStatement(lastDocNumSQL)) {
            statement.setInt(1, lentaId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                lastDocNum = resultSet.getLong(1);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lastDocNum;
    }

    private int getLastDocStatus() {
        int lastDocStatus = 0;
        try (PreparedStatement statement = conn.prepareStatement(lastDocStatusSQL)) {
            statement.setInt(1, lentaId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                lastDocStatus = resultSet.getInt(1);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return (lastDocStatus == 1) ? 3 : 1;
    }

    private String getLastDocUID() {
        String lastDocUID = " ";
        try (PreparedStatement statement = conn.prepareStatement(lastDocUIDSQL)) {
            statement.setInt(1, lentaId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                lastDocUID = resultSet.getString(1);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lastDocUID;
    }

    private long getTotalPDsCount() {
        long totalPDsCount = 0L;
        try (PreparedStatement statement = conn.prepareStatement(totalPDSCountSQL)) {
            statement.setInt(1, lentaId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                totalPDsCount = resultSet.getLong(1);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return totalPDsCount;
    }

    private boolean checkIfShiftClosed(Connection conn, int lentaId) {
        boolean answer;
        try (PreparedStatement statement = conn.prepareStatement(isShiftClosedSQL)) {
            statement.setInt(1, lentaId);
            ResultSet resultSet = statement.executeQuery();
            answer = resultSet.next();
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return answer;
    }

    public CuPos cuposBuilder() {
        return new CuPos(
                DefaultValues.OWNER_NAME.getCode(),
                " ",
                isShiftClosed ? lentaId : 0,
                getShiftOpenTime(),
                getShiftCloseTime(),
                getShiftUID(),
                isShiftClosed ? 3 : 1,
                getLastDocNum(),
                getLastDocStatus(),
                getLastDocUID(),
                getTotalPDsCount(),
                "null",
                "null",
                new Totals()
        );
    }
}