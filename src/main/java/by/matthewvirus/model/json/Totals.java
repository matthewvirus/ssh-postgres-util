package by.matthewvirus.model.json;

import lombok.*;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Totals {

    private String currCode;
    private String currName;
    private long numOfSales;
    private long sumOfSales;
    private long sumOfNoncash;
    private long sumOfCash;
    private long numOfRefunds;
    private long sumOfRefunds;
    private long numOfCashIn;
    private long sumOfCashIn;
    private long numOfCashOut;
    private long sumOfCashOut;
    private long numOfCancel;
    private long sumOfCancel;
    private long numOfAnnul;
    private long sumOfAnnul;
    private long numOfCorrections;
    private long sumOfCorrections;
    private long salesValue;

    @Override
    public String toString() {
        return "\n\t\t\t{" + "\n\t\t\t\t\"currCode\": \"" + currCode + "\"," +
                "\n\t\t\t\t\"currName\": \"" + currName + "\"," +
                "\n\t\t\t\t\"numOfSales\": " + numOfSales + "," +
                "\n\t\t\t\t\"sumOfSales\": " + sumOfSales + "," +
                "\n\t\t\t\t\"sumOfNoncash\": " + sumOfNoncash + "," +
                "\n\t\t\t\t\"sumOfCash\": " + sumOfCash + "," +
                "\n\t\t\t\t\"numOfRefunds\": " + numOfRefunds + "," +
                "\n\t\t\t\t\"sumOfRefunds\": " + sumOfRefunds + "," +
                "\n\t\t\t\t\"numOfCashIn\": " + numOfCashIn + "," +
                "\n\t\t\t\t\"sumOfCashIn\": " + sumOfCashIn + "," +
                "\n\t\t\t\t\"numOfCashOut\": " + numOfCashOut + "," +
                "\n\t\t\t\t\"sumOfCashOut\": " + sumOfCashOut + "," +
                "\n\t\t\t\t\"numOfCancel\": " + numOfCancel + "," +
                "\n\t\t\t\t\"sumOfCancel\": " + sumOfCancel + "," +
                "\n\t\t\t\t\"numOfAnnul\": " + numOfAnnul + "," +
                "\n\t\t\t\t\"sumOfAnnul\": " + sumOfAnnul + "," +
                "\n\t\t\t\t\"numOfCorrections\": " + numOfCorrections + "," +
                "\n\t\t\t\t\"sumOfCorrections\": " + sumOfCorrections + "," +
                "\n\t\t\t\t\"salesValue\": " + salesValue +
                "\n\t\t\t}";
    }
}