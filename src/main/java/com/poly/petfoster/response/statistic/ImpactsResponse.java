package com.poly.petfoster.response.statistic;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImpactsResponse {
    
    private List<ImpactItemResponse> impactOfYear;

}
