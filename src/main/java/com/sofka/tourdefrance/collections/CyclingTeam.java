package com.sofka.tourdefrance.collections;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "teams")
public class CyclingTeam {
    @Id
    private String id;
    private String name;
    @Indexed(unique = true) // unique = true means the teamCode must be unique in the collection.
    private String teamCode;
    private String country;
}
