package com.poly.petfoster.service.impl.user;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.poly.petfoster.config.JwtProvider;
import com.poly.petfoster.constant.RespMessage;
import com.poly.petfoster.entity.Addresses;
import com.poly.petfoster.entity.Authorities;
import com.poly.petfoster.entity.Product;
import com.poly.petfoster.entity.Role;
import com.poly.petfoster.entity.User;
import com.poly.petfoster.random.RandomPassword;
import com.poly.petfoster.repository.AddressRepository;
import com.poly.petfoster.repository.AuthoritiesRepository;
import com.poly.petfoster.repository.RoleRepository;
import com.poly.petfoster.repository.UserRepository;
import com.poly.petfoster.request.ResetPasswordRequest;
import com.poly.petfoster.request.users.CreateaUserManageRequest;
import com.poly.petfoster.request.users.UpdateUserRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.response.common.PagiantionResponse;
import com.poly.petfoster.response.users.UserManageResponse;
import com.poly.petfoster.response.users.UserProfileMessageResponse;
import com.poly.petfoster.response.users.UserProfileResponse;
import com.poly.petfoster.service.user.UserService;
import com.poly.petfoster.ultils.ImageUtils;
import com.poly.petfoster.ultils.MailUtils;
import com.poly.petfoster.ultils.PortUltil;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RandomPassword randomPassword;

    @Autowired
    MailUtils mailUtils;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    PortUltil portUltil;

    @Autowired
    AuthoritiesRepository authoritiesRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AddressRepository addressRepository;

    @Override
    public UserDetails findByUsername(String username) throws UsernameNotFoundException {

        User existsUser = userRepository.findByUsername(username).get();

        List<GrantedAuthority> authorities = new ArrayList<>();
        // authorities.add(new SimpleGrantedAuthority(existsUser.getRole()));
        existsUser.getAuthorities()
                .forEach(authority -> authorities.add(new SimpleGrantedAuthority(authority.getRole().getRole())));

        return new org.springframework.security.core.userdetails.User(
                existsUser.getUsername(),
                existsUser.getPassword(),
                existsUser.getIsEmailVerified(),
                false, false, false, authorities);

    }

    @Override
    public ApiResponse updatePassword(ResetPasswordRequest resetPasswordRequest) {

        User existsUser = userRepository.findByEmail(resetPasswordRequest.getEmail()).orElse(null);
        if (existsUser == null) {
            return ApiResponse.builder().data(null).message("User is not exist").status(404)
                    .errors(false).build();
        }
        String newPassword = randomPassword.randomPassword();
        System.out.println(newPassword);
        existsUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(existsUser);

        // Send password to mail
        mailUtils.sendEmail(resetPasswordRequest.getEmail(), "Reset password", "Your new password is " + newPassword);
        return ApiResponse.builder().data(existsUser).message("Successfully!").status(200)
                .errors(false).build();
    }

    @Override
    public ApiResponse getAllUser(String jwt, Optional<String> keyword, Optional<String> sort, Optional<String> role,
            Optional<Integer> pages) {

        List<User> users = userRepository.findAll(keyword.orElse(null), role.orElse(null), sort.orElse(null));

        if (users.isEmpty()) {
            return ApiResponse.builder().message("No data!")
                    .status(400)
                    .errors(false)
                    .data(PagiantionResponse.builder().data(new ArrayList<>())
                            .build())
                    .build();
        }

        Pageable pageable = PageRequest.of(pages.orElse(0), 10);
        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), users.size());

        if (startIndex >= endIndex) {
            return ApiResponse.builder()
                    .message(RespMessage.NOT_FOUND.getValue())
                    .data(null)
                    .errors(true)
                    .status(HttpStatus.NOT_FOUND.value())
                    .build();
        }

        List<User> visibleUsers = users.subList(startIndex, endIndex);

        List<UserProfileResponse> visibleResponseUsers = new ArrayList<>();

        visibleUsers.forEach((user) -> {
            // find role by user
            List<Authorities> roles = authoritiesRepository.findByUser(user);
            Role rolee = null;
            if (roles.size() > 0) {
                rolee = roles.get(0).getRole();
            }
            UserProfileResponse userProfile = UserProfileResponse.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .fullname(user.getFullname())
                    .birthday(user.getBirthday())
                    .gender(user.getGender() == null ? false : true)
                    .phone(user.getPhone())
                    .email(user.getEmail())
                    .avatar(user.getAvatar() == null ? null : portUltil.getUrlImage(user.getAvatar()))
                    .role(rolee == null ? null : rolee.getRole())
                    .createAt(user.getCreateAt())
                    .build();

            visibleResponseUsers.add(userProfile);

        });

        Page<UserProfileResponse> pagination = new PageImpl<UserProfileResponse>(visibleResponseUsers, pageable,
                users.size());

        return ApiResponse.builder().message("Successfully!")
                .status(200)
                .errors(false)
                .data(PagiantionResponse.builder().data(pagination.getContent()).pages(pagination.getTotalPages())
                        .build())
                .build();
    }

    @Override
    public ApiResponse updateUser(@Valid UpdateUserRequest updateUserRequest) {
        Map<String, String> errorsMap = new HashMap<>();

        User user = userRepository.findById(updateUserRequest.getId()).orElse(null);

        if (user == null) {
            return ApiResponse.builder()
                    .message("User not found !")
                    .status(HttpStatus.NOT_FOUND.value())
                    .errors(true)
                    .data(null)
                    .build();
        }

        // start validate

        if (updateUserRequest.getFullname().isEmpty()) {
            errorsMap.put("fullname", "Fullname can't be blank");
        }

        if (updateUserRequest.getAvatar() != null) {
            if (updateUserRequest.getAvatar().getSize() > 500000) {
                errorsMap.put("avartar", "Image size is too large");
            } else {
                try {
                    File file = ImageUtils.createFileImage();

                    updateUserRequest.getAvatar().transferTo(new File(file.getAbsolutePath()));
                    user.setAvatar(file.getName());
                } catch (Exception e) {
                    System.out.println("Erorr in update avatar in Profile service impl");
                    e.printStackTrace();
                    return ApiResponse.builder()
                            .message(RespMessage.INTERNAL_SERVER_ERROR.getValue())
                            .errors(true)
                            .status(500)
                            .data(null)
                            .build();
                }
            }
        }

        // check errors

        if (!errorsMap.isEmpty()) {
            return ApiResponse.builder()
                    .message("Update fail !")
                    .status(501)
                    .errors(errorsMap)
                    .data(null)
                    .build();
        }

        // end validate

        user.setFullname(updateUserRequest.getFullname());
        user.setGender(updateUserRequest.getGender());
        user.setBirthday(updateUserRequest.getBirthday().orElse(null));
        user.setPhone(updateUserRequest.getPhone());
        // user.setAddress(updateUserRequest.getAddress());

        User newUser = userRepository.save(user);

        newUser.setAvatar(portUltil.getUrlImage(user.getAvatar()));

        return ApiResponse.builder()
                .message("Update success!")
                .errors(false)
                .status(HttpStatus.OK.value())
                .data(newUser)
                .build();

    }

    @Override
    public ApiResponse deleteUser(String id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return ApiResponse.builder()
                    .message("User not found !")
                    .status(HttpStatus.NOT_FOUND.value())
                    .errors(true)
                    .data(null)
                    .build();
        }

        user.setIsActive(false);
        return ApiResponse.builder()
                .message("Delete success!")
                .errors(false)
                .status(HttpStatus.OK.value())
                .data(userRepository.save(user))
                .build();
    }

    @Override
    public ApiResponse getUser(String id) {

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return ApiResponse.builder()
                    .message("User not found !")
                    .errors(true)
                    .status(HttpStatus.NOT_FOUND.value())
                    .data(null)
                    .build();
        }

        Role role = authoritiesRepository.findByUser(user).get(0).getRole();
        UserManageResponse userManageResponse = UserManageResponse.builder()
                .id(user.getId())
                .gender(user.getGender())
                .username(user.getUsername())
                .role(role.getRole())
                .email(user.getEmail())
                .birthday(user.getBirthday())
                .createAt(user.getCreateAt())
                .phone(user.getPhone())
                .active(user.getIsActive())
                .password("This is secret. You can't watch !")
                .avatar(portUltil.getUrlImage(user.getAvatar()))
                .fullname(user.getFullname()).build();

        return ApiResponse.builder()
                .message("Get user success!")
                .errors(false)
                .status(HttpStatus.OK.value())
                .data(userManageResponse)
                .build();
    }

    @Override
    public ApiResponse createUser(CreateaUserManageRequest createaUserManageRequest) {
        Map<String, String> errorsMap = new HashMap<>();
        // start validate

        if (createaUserManageRequest.getEmail().isEmpty()) {
            errorsMap.put("email", "Can't update email !");
        }

        if (userRepository.existsByUsername(createaUserManageRequest.getUsername())) {
            errorsMap.put("username", "Username " + RespMessage.EXISTS);
        }

        if (userRepository.existsByEmail(createaUserManageRequest.getEmail())) {
            errorsMap.put("email", "Email " + RespMessage.EXISTS);
        }

        if (createaUserManageRequest.getFullname().isEmpty()) {
            errorsMap.put("fullname", "Fullname can't be blank");
        }

        if (createaUserManageRequest.getPhone().isEmpty()) {
            errorsMap.put("phone", "Phone can't be blank");
        }

        if (createaUserManageRequest.getBirthday().orElse(null) == null) {
            errorsMap.put("birthday", "Birthday can't be blank");
        }

        // end validate

        // check errors
        if (!errorsMap.isEmpty()) {
            return ApiResponse.builder()
                    .message("Update faild !")
                    .errors(errorsMap)
                    .status(501)
                    .data(null)
                    .build();
        }

        User newUser = User.builder()
                .birthday(createaUserManageRequest.getBirthday().orElse(null))
                .email(createaUserManageRequest.getEmail())
                .fullname(createaUserManageRequest.getFullname())
                .gender(createaUserManageRequest.getGender())
                .isActive(true)
                .password(passwordEncoder.encode(createaUserManageRequest.getPassword()))
                .phone(createaUserManageRequest.getPhone())
                .username(createaUserManageRequest.getUsername())
                .isEmailVerified(true)
                .build();

        // set role and authorization
        List<Authorities> authoritiesList = new ArrayList<>();
        Authorities authorities = Authorities.builder().user(newUser).role(roleRepository.getRoleUser()).build();
        authoritiesList.add(authorities);

        newUser.setAuthorities(authoritiesList);

        if (createaUserManageRequest.getAvatar() != null) {
            if (createaUserManageRequest.getAvatar().getSize() > 500000) {
                errorsMap.put("avartar", "Image size is too large");
            } else {
                try {
                    File file = ImageUtils.createFileImage();

                    createaUserManageRequest.getAvatar().transferTo(new File(file.getAbsolutePath()));
                    newUser.setAvatar(file.getName());
                } catch (Exception e) {
                    System.out.println("Erorr in update avatar in Profile service impl");
                    e.printStackTrace();
                    return ApiResponse.builder()
                            .message(RespMessage.INTERNAL_SERVER_ERROR.getValue())
                            .errors(true)
                            .status(500)
                            .data(null)
                            .build();
                }
            }
        }

        return ApiResponse.builder()
                .message("Create succssefuly !")
                .errors(false)
                .status(HttpStatus.OK.value())
                .data(userRepository.save(newUser))
                .build();
    }

    @Override
    public ApiResponse getUserWithUsername(String username) {
        User user = userRepository.findByUsername(username).orElse(null);

        if (user == null) {
            return ApiResponse.builder()
                    .message("User not found !")
                    .errors(true)
                    .status(HttpStatus.NOT_FOUND.value())
                    .data(null)
                    .build();
        }

        Addresses addresses = addressRepository.findByIsDefaultWithUser(username);

        if (addresses == null) {
            if (user.getAddresses().size() > 0) {
                addresses = user.getAddresses().get(0);
            }
        }

        UserProfileMessageResponse userManageResponse = UserProfileMessageResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .avatar(portUltil.getUrlImage(user.getAvatar()))
                .address(buildAddress(addresses))
                .fullname(user.getFullname()).build();

        return ApiResponse.builder()
                .message("Get user success!")
                .errors(false)
                .status(HttpStatus.OK.value())
                .data(userManageResponse)
                .build();
    }

    private String buildAddress(Addresses addresses) {
        if (addresses == null)
            return null;

        return addresses.getAddress() + ", " + addresses.getWard() + ", " + addresses.getDistrict() + ", "
                + addresses.getProvince();
    }

}
