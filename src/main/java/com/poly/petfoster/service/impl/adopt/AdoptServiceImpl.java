package com.poly.petfoster.service.impl.adopt;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.poly.petfoster.config.JwtProvider;
import com.poly.petfoster.constant.AdoptStatus;
import com.poly.petfoster.constant.Constant;
import com.poly.petfoster.entity.Adopt;
import com.poly.petfoster.entity.Pet;
import com.poly.petfoster.entity.User;
import com.poly.petfoster.repository.AdoptRepository;
import com.poly.petfoster.repository.PetRepository;
import com.poly.petfoster.repository.UserRepository;
import com.poly.petfoster.request.adopts.AdoptsRequest;
import com.poly.petfoster.request.adopts.CancelAdoptRequest;
import com.poly.petfoster.request.adopts.UpdatePickUpDateRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.response.adopts.AdoptsResponse;
import com.poly.petfoster.response.common.PagiantionResponse;
import com.poly.petfoster.service.adopt.AdoptService;
import com.poly.petfoster.service.impl.pets.PetServiceImpl;
import com.poly.petfoster.service.impl.user.UserServiceImpl;
import com.poly.petfoster.ultils.FormatUtils;

@Service
public class AdoptServiceImpl implements AdoptService {

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PetRepository petRepository;
    
    @Autowired
    AdoptRepository adoptRepository;

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    PetServiceImpl petServiceImpl;

    @Autowired
    FormatUtils formatUtils;

    @Override
    public ApiResponse getAdopts(String jwt, Optional<Integer> page) {
        
        //get username 
        String username = jwtProvider.getUsernameFromToken(jwt);
        if(username.isEmpty() || username == null) {
            return ApiResponse.builder().status(HttpStatus.BAD_REQUEST.value()).message("Failure!!!").errors(true).data(new ArrayList<>()).build();
        }

        //get user
        User user = userRepository.findByUsername(username).orElse(null);
        if(user == null) {
            return ApiResponse.builder().status(HttpStatus.BAD_REQUEST.value()).message("Failure!!!").errors(true).data(new ArrayList<>()).build();
        }

        //get adopts
        List<Adopt> adopts = user.getAdopts();
        if(adopts.isEmpty()) {
            return ApiResponse.builder().status(HttpStatus.BAD_REQUEST.value()).message("No data available!!!").errors(false).data(adopts).build();
        }

        Integer pageSize = 10;
        Integer pages = page.orElse(0);
        Integer totalPages = (adopts.size() + pageSize - 1) / pageSize;

        if (pages >= totalPages) {
            return ApiResponse.builder()
                    .status(HttpStatus.NO_CONTENT.value())
                    .message("No data available!!!")
                    .errors(false)
                    .data(PagiantionResponse.builder().data(new ArrayList<>()).pages(0).build())
                    .build();
        }

        Pageable pageable = PageRequest.of(pages, pageSize);
        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), adopts.size());
        if (startIndex >= endIndex) {
            return ApiResponse.builder()
                    .status(200)
                    .message("Successfully!!!")
                    .errors(false)
                    .data(PagiantionResponse.builder().data(adopts).pages(0).build())
                    .build();
        }

        List<Adopt> visibleAdopts = adopts.subList(startIndex, endIndex);
        
        Page<Adopt> pagination = new PageImpl<Adopt>(visibleAdopts, pageable, adopts.size());
        
        //get adopts response & sort
        List<AdoptsResponse> adoptsResponse = visibleAdopts.stream().map(adopt -> this.buildAdoptsResponse(adopt))
                                                .collect(Collectors.toList());
        adoptsResponse.sort(Comparator.comparing((AdoptsResponse adoptResponse) -> adoptResponse.getRegisterAt() != null ? adoptResponse.getRegisterAt() : Constant.MIN_DATE).reversed());                                        
                                                

        return ApiResponse.builder()
                .status(200)
                .message("Successfully!!!")
                .errors(false)
                .data(PagiantionResponse.builder().data(adoptsResponse).pages(pagination.getTotalPages()).build())
                .build();
    
    }

    @Override
    public ApiResponse adopt(String jwt, AdoptsRequest adoptsRequest) {

        //get user
        String username = jwtProvider.getUsernameFromToken(jwt);
        if(username == null || username.isEmpty()) {
            return ApiResponse.builder().status(HttpStatus.BAD_REQUEST.value()).message("User not found!!!").errors(true).build();
        }

        User curUser = userRepository.findByUsername(username).orElse(null);
        if(curUser == null) {
            return ApiResponse.builder().status(HttpStatus.BAD_REQUEST.value()).message("User not found!!!").errors(true).build();
        }

        User user = userRepository.findById(adoptsRequest.getUserId()).orElse(null);
        if(user == null) {
            return ApiResponse.builder().status(HttpStatus.BAD_REQUEST.value()).message("User not found!!!").errors(true).build();
        }

        if(user != curUser) {
            return ApiResponse.builder().status(HttpStatus.BAD_REQUEST.value()).message("You don't have permission to adopt for this user!!!").errors(true).build();
        }

        //get pet
        Pet pet = petRepository.findById(adoptsRequest.getPetId()).orElse(null);
        if(pet == null) {
            return ApiResponse.builder().status(HttpStatus.BAD_REQUEST.value()).message("Pet not found!!!").errors(true).build();
        }

        //pet was adopted
        if(adoptRepository.exsitsAdopted(adoptsRequest.getPetId()) != null) {
            return ApiResponse.builder().status(HttpStatus.BAD_REQUEST.value()).message("This pet was adopted or registered").errors(true).build();
        }

        //the adoption was waiting by the user
        if(adoptRepository.existsByPetAndUser(adoptsRequest.getPetId(), adoptsRequest.getUserId()) != null) {
            return ApiResponse.builder().status(HttpStatus.BAD_REQUEST.value()).message("The adoption was accepted!!!").errors(true).build();
        }
        
        //save adopt
        Adopt adopt = Adopt.builder()
            .registerAt(new Date())
            .status("Waiting")
            .pet(pet)
            .user(user)
            .phone(adoptsRequest.getPhone())
            .address(adoptsRequest.getAddress())
            .build();
        adoptRepository.save(adopt);

        //adopt response
        AdoptsResponse adoptsResponse = this.buildAdoptsResponse(adopt);

        return ApiResponse.builder()
            .status(200)
            .message("Successfully!!!")
            .errors(false)
            .data(adoptsResponse)
            .build();
    }
    
    public AdoptsResponse buildAdoptsResponse(Adopt adopt) {
        return AdoptsResponse.builder()
        .id(adopt.getAdoptId())
        .state(adopt.getStatus())
        .user(userServiceImpl.buildUserProfileResponse(adopt.getUser()))
        .pet(petServiceImpl.buildPetResponse(adopt.getPet(), adopt.getUser()))
        .adoptAt(adopt.getAdoptAt())
        .registerAt(adopt.getRegisterAt())
        .cancelReason(adopt.getCancelReason() != null ? adopt.getCancelReason() : "")
        .phone(adopt.getPhone())
        .address(adopt.getAddress())
        .build();
    }

    @Override
    public ApiResponse filterAdopts(
        Optional<String> name, 
        Optional<String> petName, 
        Optional<String> status, 
        Optional<Date> registerStart, 
        Optional<Date> registerEnd,
        Optional<Date> adoptStart,
        Optional<Date> adoptEnd,
        Optional<String> sort,
        Optional<Integer> page) {

        Date registerStartValue = formatUtils.changeDateRange(registerStart, registerEnd).get("minDateValue");
        Date registerEndValue = formatUtils.changeDateRange(registerStart, registerEnd).get("maxDateValue");
        Date adoptStartValue = formatUtils.changeDateRange(adoptStart, adoptEnd).get("minDateValue");
        Date adoptEndValue = formatUtils.changeDateRange(adoptStart, adoptEnd).get("maxDateValue");

        if (registerStartValue != null && registerEndValue != null) {
            if (registerStartValue.after(registerEndValue)) {
                return ApiResponse.builder()
                        .message("The max date must after the min date!!!")
                        .status(HttpStatus.CONFLICT.value())
                        .errors("The max date must after the min date!!!")
                        .build();
            }
        }

        if (adoptStartValue != null && adoptEndValue != null) {
            if (adoptStartValue.after(adoptEndValue)) {
                return ApiResponse.builder()
                        .message("The max date must after the min date!!!")
                        .status(HttpStatus.CONFLICT.value())
                        .errors("The max date must after the min date!!!")
                        .build();
            }
        }

        List<Adopt> filterAdopts = adoptRepository.filterAdopts(
            name.orElse(null), petName.orElse(null), status.orElse(null), 
            registerStartValue, registerEndValue, adoptStartValue, adoptEndValue, sort.orElse(null)
            );

        Integer pageSize = 10;
        Integer pages = page.orElse(0);
        Integer totalPages = (filterAdopts.size() + pageSize - 1) / pageSize;

        if (pages >= totalPages) {
            return ApiResponse.builder()
                    .status(HttpStatus.NO_CONTENT.value())
                    .message("No data available!!!")
                    .errors(false)
                    .data(PagiantionResponse.builder().data(new ArrayList<>()).pages(0).build())
                    .build();
        }

        Pageable pageable = PageRequest.of(pages, pageSize);
        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), filterAdopts.size());
        if (startIndex >= endIndex) {
            return ApiResponse.builder()
                    .status(200)
                    .message("Successfully!!!")
                    .errors(false)
                    .data(PagiantionResponse.builder().data(filterAdopts).pages(0).build())
                    .build();
        }

        List<Adopt> visibleAdopts = filterAdopts.subList(startIndex, endIndex);
        Page<Adopt> pagination = new PageImpl<Adopt>(visibleAdopts, pageable, filterAdopts.size());
        List<AdoptsResponse> adopts = new ArrayList<>();
        visibleAdopts.forEach(adopt -> adopts.add(this.buildAdoptsResponse(adopt)));

        return ApiResponse.builder()
                .status(200)
                .message("Successfully!!!")
                .errors(false)
                .data(PagiantionResponse.builder().data(adopts).pages(pagination.getTotalPages()).build())
                .build();
       
    }

    @Override
    public ApiResponse acceptAdoption(Integer id, UpdatePickUpDateRequest updatePickUpDateRequest) {
        
        Adopt adopt = adoptRepository.findById(id).orElse(null);
        if(adopt == null) {
            return ApiResponse.builder().status(HttpStatus.NOT_FOUND.value()).message("Adopt not found!!!").errors(true).build();
        }

        if(adopt.getStatus().equalsIgnoreCase(AdoptStatus.ADOPTED.getValue()) || adopt.getStatus().equalsIgnoreCase(AdoptStatus.REGISTERED.getValue())) {
            return ApiResponse.builder().status(HttpStatus.BAD_REQUEST.value()).message("This adoption was accepted!!!").errors(true).build();
        }

        if(updatePickUpDateRequest.getPickUpDate().before(new Date())) {
            return ApiResponse.builder().status(HttpStatus.BAD_REQUEST.value()).message("The date was passed! Please choose another date!!!").errors(true).build();
        }

        adopt.setStatus(AdoptStatus.REGISTERED.getValue());
        adopt.setPickUpAt(updatePickUpDateRequest.getPickUpDate());
        adoptRepository.save(adopt);

        return ApiResponse.builder()
                .status(200)
                .message("Successfully!!!")
                .errors(false)
                .data(this.buildAdoptsResponse(adopt))
                .build();
    }

    @Override
    public ApiResponse cancelAdopt(Integer id, CancelAdoptRequest cancelAdoptRequest) {

        //get adopt
        Adopt adopt = adoptRepository.findById(id).orElse(null);
        if(adopt == null) {
            return ApiResponse.builder().status(HttpStatus.BAD_REQUEST.value()).message("Adopt not found!!!").errors(true).build();
        }

        //check adoption status was adopted or not
        if(adopt.getStatus().equalsIgnoreCase(AdoptStatus.ADOPTED.getValue())) {
            return ApiResponse.builder().status(HttpStatus.BAD_REQUEST.value()).message("The pet was adopted. Cannot cancel!!!").errors(true).build();
        }

        //check adoption status was cancel or not
        if(adopt.getStatus().equalsIgnoreCase(AdoptStatus.CANCELLED_BY_ADMIN.getValue()) || adopt.getStatus().equalsIgnoreCase(AdoptStatus.CANCELLED_BY_CUSTOMER.getValue())) {
            return ApiResponse.builder().status(HttpStatus.BAD_REQUEST.value()).message("The adoption was cancel. Cannot cancel again!!!").errors(true).build();
        }

        //reject the adoption
        adopt.setStatus(AdoptStatus.CANCELLED_BY_ADMIN.getValue());
        adopt.setCancelReason(cancelAdoptRequest.getCancelReason());
        adoptRepository.save(adopt);

        return ApiResponse.builder()
                .status(200)
                .message("Successfully!!!")
                .errors(false)
                .data(this.buildAdoptsResponse(adopt))
                .build();
    }

    @Override
    public ApiResponse cancelAdoptByUser(Integer id, String jwt, CancelAdoptRequest cancelAdoptRequest) {

        //get adopt
        Adopt adopt = adoptRepository.findById(id).orElse(null);
        if(adopt == null) {
            return ApiResponse.builder().status(HttpStatus.BAD_REQUEST.value()).message("Adopt not found!!!").errors(true).build();
        }

        //check user adopt
        String username = jwtProvider.getUsernameFromToken(jwt);
        if(username == null || username.isEmpty()) {
            return ApiResponse.builder().status(HttpStatus.BAD_REQUEST.value()).message("Username is not exists!!!").errors(true).build();
        }

        User user = userRepository.findByUsername(username).orElse(null);
        if(user == null) {
            return ApiResponse.builder().status(HttpStatus.BAD_REQUEST.value()).message("User not found!!!").errors(true).build();
        }

        if(adoptRepository.existsByUser(user.getId(), id) == null) {
            return ApiResponse.builder().status(HttpStatus.BAD_REQUEST.value()).message("You don't have permission to cancel!!!").errors(true).build();
        }
        

        //check adoption status was adopted or not
        if(adopt.getStatus().equalsIgnoreCase(AdoptStatus.ADOPTED.getValue())) {
            return ApiResponse.builder().status(HttpStatus.BAD_REQUEST.value()).message("The pet was adopted. Cannot cancel!!!").errors(true).build();
        }

        //check adoption status was cancel or not
        if(adopt.getStatus().equalsIgnoreCase(AdoptStatus.CANCELLED_BY_ADMIN.getValue()) || adopt.getStatus().equalsIgnoreCase(AdoptStatus.CANCELLED_BY_CUSTOMER.getValue())) {
            return ApiResponse.builder().status(HttpStatus.BAD_REQUEST.value()).message("The adoption was cancel. Cannot cancel again!!!").errors(true).build();
        }

        //reject the adoption
        adopt.setStatus(AdoptStatus.CANCELLED_BY_CUSTOMER.getValue());
        adopt.setCancelReason(cancelAdoptRequest.getCancelReason());
        adoptRepository.save(adopt);

        return ApiResponse.builder()
                .status(200)
                .message("Successfully!!!")
                .errors(false)
                .data(this.buildAdoptsResponse(adopt))
                .build();

    }

}
