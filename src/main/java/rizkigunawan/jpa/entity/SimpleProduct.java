package rizkigunawan.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SimpleProduct {

    private String id;

    private Long count;
    
    private Double average;

    private Long min;
    
    private Long max;

    private Long sum;

}
