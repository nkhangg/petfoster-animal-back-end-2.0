package com.poly.petfoster.service.adopt;

import java.util.Date;
import java.util.Optional;


import com.poly.petfoster.request.adopts.AdoptsRequest;
import com.poly.petfoster.request.adopts.UpdatePickUpDateRequest;
import com.poly.petfoster.response.ApiResponse;

public interface AdoptService {
    
    ApiResponse getAdopts(String jwt);

    ApiResponse adopt(String jwt, AdoptsRequest adoptsRequest);

    ApiResponse filterAdopts(
        Optional<String> name, 
        Optional<String> petName, 
        Optional<String> status, 
        Optional<Date> registerStart, 
        Optional<Date> registerEnd,
        Optional<Date> adoptStart,
        Optional<Date> adoptEnd,
        Optional<String> sort,
        Optional<Integer> page
    );

    ApiResponse acceptAdoption(Integer id, UpdatePickUpDateRequest updatePickUpDateRequest);

}
