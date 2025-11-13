package rizkigunawan.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProductBrandAggregate {
    
    private String id;

    private Long min;

    private Long max;

    private Double average;
    
}
