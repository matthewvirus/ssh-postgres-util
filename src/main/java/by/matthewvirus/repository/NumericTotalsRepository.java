package by.matthewvirus.repository;

import by.matthewvirus.model.DefaultValues;
import by.matthewvirus.model.json.Totals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NumericTotalsRepository {

    private final Connection conn;
    private final int lentaId;

    public static final String sumOfSalesSQL = "select sum(oper_sum) as sum_sales from wdfly.session_journal where cash_session_id=(select id from wdfly.cash_session where session_number=?) and oper_sum is not null and oper_type in ('30','20') and oper_status != 66";
    public static final String sumOfCashSQL = "select sum(cash_sum) from wdfly.session_journal inner join wdfly.ticket on session_journal.ticket_id=ticket.id where cash_session_id=(select id from wdfly.cash_session where session_number=?) and oper_type in('30','20') and cash_sum is not null and oper_status != 66";
    public static final String sumOfNoncashSQL = "select sum(cashless_sum) as cashless_sum from wdfly.session_journal inner join wdfly.ticket on session_journal.ticket_id=ticket.id where cash_session_id=(select id from wdfly.cash_session where session_number=?) and oper_type in('30','20') and cashless_sum is not null and oper_status != 66";
    public static final String numOfAnnulSQL = "select count(oper_sum) as annul_count from wdfly.session_journal where cash_session_id=(select id from wdfly.cash_session where session_number=?) and session_journal.oper_type='65'";
    public static final String sumOfAnnulSQL = "select sum(oper_sum) as annul_sum from wdfly.session_journal where cash_session_id=(select id from wdfly.cash_session where session_number=?) and session_journal.oper_type='65'";
    public static final String numOfRefundsSQL = "select count(oper_sum) as refunds_count from wdfly.session_journal where cash_session_id=(select id from wdfly.cash_session where session_number=?) and session_journal.oper_type='60'";
    public static final String sumOfRefundsSQL = "select sum(oper_sum) as refunds_sum from wdfly.session_journal where cash_session_id=(select id from wdfly.cash_session where session_number=?) and session_journal.oper_type='60'";
    public static final String salesValueSQL = "select sum(oper_sum) as sales_value from wdfly.session_journal where cash_session_id=(select id from wdfly.cash_session where session_number=?) and oper_type in('30','20') and oper_status=30";
    public static final String numOfSalesSQL = "select count(*) as num_sales from wdfly.session_journal where cash_session_id=(select id from wdfly.cash_session where session_number=?) and oper_type in('30','20') and oper_status != 66";
    public static final String numOfCashInSQL = "select count(*) as num_cash_in from wdfly.session_journal where cash_session_id=(select id from wdfly.cash_session where session_number=?) and oper_type='80' and oper_status=30";
    public static final String sumOfCashInSQL = "select sum(oper_sum) as sum_cash_in from wdfly.session_journal where cash_session_id=(select id from wdfly.cash_session where session_number=?) and oper_type='80' and oper_status=30";
    public static final String sumOfCashOutSQL = "select sum(oper_sum) as sum_cash_out from wdfly.session_journal where cash_session_id=(select id from wdfly.cash_session where session_number=?) and oper_type='90' and oper_status=30";
    public static final String numOfCashOutSQL = "select count(*) as num_cash_out from wdfly.session_journal where cash_session_id=(select id from wdfly.cash_session where session_number=?) and oper_type='90' and oper_status=30";

    public NumericTotalsRepository(Connection conn, int lentaId) {
        this.conn = conn;
        this.lentaId = lentaId;
    }

    private long queriesExecutor(String statementSql) {
        long data = 0;
        try (PreparedStatement st = conn.prepareStatement(statementSql)){
            st.setInt(1, lentaId);
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next()) {
                data = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return data;
    }

    private long cashCounters(String statementSql) {
        long data = 0;
        try (PreparedStatement st = conn.prepareStatement(statementSql)){
            st.setInt(1, lentaId);
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next()) {
                data = (long) (resultSet.getFloat(1) * 100f);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return data;
    }

    public Totals totalsBuilder() {
        return new Totals(
                DefaultValues.CURRENCY_CODE.getCode(),
                DefaultValues.CURRENCY_NAME.getCode(),
                queriesExecutor(numOfSalesSQL),
                cashCounters(sumOfSalesSQL),
                cashCounters(sumOfNoncashSQL),
                cashCounters(sumOfCashSQL),
                queriesExecutor(numOfRefundsSQL),
                cashCounters(sumOfRefundsSQL),
                queriesExecutor(numOfCashInSQL),
                cashCounters(sumOfCashInSQL),
                queriesExecutor(numOfCashOutSQL),
                cashCounters(sumOfCashOutSQL),
                0,
                0,
                queriesExecutor(numOfAnnulSQL),
                cashCounters(sumOfAnnulSQL),
                0,
                0,
                cashCounters(salesValueSQL)
        );
    }
}