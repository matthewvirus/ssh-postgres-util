package by.matthewvirus.model.json;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JsonRequest {

    private int id;
    private CuPos data;

    @Override
    public String toString() {
        return "{" + "\n\t\"id\": " + id + ',' +
                "\n\t\"data\": " + data.toString() +
                "\n}";
    }
}