package by.matthewvirus.model.json;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CuPos {

    private String ownerName;
    private String installationAddr;
    private int shiftNum;
    private String shiftOpenT;
    private String shiftCloseT;
    private String shiftUID;
    private int shiftStatus;
    private long lastDocNum;
    private int lastDocStatus;
    private String lastDocUID;
    private long totalPDsCount;
    private String firstPDNum;
    private String lastPDNum;
    private Totals totals;

    @Override
    public String toString() {
        return "{" + "\n\t\t\"ownerName\": \"" + ownerName + "\"," +
                "\n\t\t\"installationAddr\": \"" + installationAddr + "\"," +
                "\n\t\t\"shiftNum\": " + shiftNum + "," +
                "\n\t\t\"shiftOpenT\": \"" + shiftOpenT + "\"," +
                "\n\t\t\"shiftCloseT\": \"" + shiftCloseT + "\"," +
                "\n\t\t\"shiftUID\": \"" + shiftUID + "\"," +
                "\n\t\t\"shiftStatus\": " + shiftStatus + "," +
                "\n\t\t\"lastDocNum\": " + lastDocNum + "," +
                "\n\t\t\"lastDocStatus\": " + lastDocStatus + "," +
                "\n\t\t\"lastDocUID\": \"" + lastDocUID + "\"," +
                "\n\t\t\"totalPDsCount\": " + totalPDsCount + "," +
                "\n\t\t\"firstPDNum\": \"" + firstPDNum + "\"," +
                "\n\t\t\"lastPDNum\": \"" + lastPDNum + "\"," +
                "\n\t\t\"totals\": [" + totals.toString() +
                "\n\t\t]\n\t}";
    }
}