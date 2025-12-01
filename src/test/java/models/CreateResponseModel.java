package models;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CreateResponseModel {
    String name, job, id, createdAt;
}
