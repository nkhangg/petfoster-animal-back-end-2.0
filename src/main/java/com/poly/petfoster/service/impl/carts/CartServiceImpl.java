package com.poly.petfoster.service.impl.carts;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.petfoster.config.JwtProvider;
import com.poly.petfoster.entity.CartItem;
import com.poly.petfoster.entity.Carts;
import com.poly.petfoster.entity.Product;
import com.poly.petfoster.entity.ProductRepo;
import com.poly.petfoster.entity.User;
import com.poly.petfoster.repository.CartItemRepository;
import com.poly.petfoster.repository.CartRepository;
import com.poly.petfoster.repository.ProductRepoRepository;
import com.poly.petfoster.repository.ProductRepository;
import com.poly.petfoster.repository.UserRepository;
import com.poly.petfoster.request.carts.CartRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.carts.CartService;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    ProductRepoRepository productRepoRepository;
    @Autowired
    ProductRepository productRepository;

    @Override
    public ApiResponse updateCarts(String jwt, String productID, Integer quantity) {
        // TODO Auto-generated method stub
        User user = userRepository.findByUsername(jwtProvider.getUsernameFromToken(jwt)).orElse(null);

        if (user == null) {
            return ApiResponse.builder()
                    .message("Invalid Token!!!")
                    .status(400)
                    .errors("Invalid token")
                    .build();
        }
        Carts createCart = new Carts();

        Carts carts = cartRepository.findCarts(user.getId()).orElse(null);

        if (carts == null) {
            createCart.setUser(user);
            carts = cartRepository.save(createCart);
        }

        ProductRepo productRepo = productRepoRepository.findByProductID(productID);

        if (productRepo == null) {
            return ApiResponse.builder().message("Product ID is not exist!").status(400).errors(true).data(null)
                    .build();
        }

        CartItem updateCartItem = new CartItem(null, carts, productRepo, quantity);

        cartItemRepository.save(updateCartItem);

        return ApiResponse.builder().status(200).errors(false).data(null).message("Update Successfully!")
                .build();
    }

    @Override
    public ApiResponse getCarts(String jwt) {
        // TODO Auto-generated method stub
        User user = userRepository.findByUsername(jwtProvider.getUsernameFromToken(jwt)).orElse(null);

        if (user == null) {
            return ApiResponse.builder()
                    .message("Invalid Token!!!")
                    .status(400)
                    .errors("Invalid token")
                    .build();
        }
        Carts createCart = new Carts();

        Carts carts = cartRepository.findCarts(user.getId()).orElse(null);

        if (carts == null) {
            createCart.setUser(user);
            carts = cartRepository.save(createCart);
        }

        List<CartItem> cartItems = cartItemRepository.cartItem(carts.getCardId());

        if (cartItems.isEmpty()) {
            return ApiResponse.builder().message("No data available").status(200).errors(false).data(cartItems)
                    .build();
        }

        List<CartRequest> cartRequests = new ArrayList<>();

        for (int i = 0; i < cartItems.size(); i++) {
            CartRequest cartRequest = new CartRequest();
            cartRequest.setId(cartItems.get(i).getProductRepo().getProduct().getId());
            cartRequest.setBrand(cartItems.get(i).getProductRepo().getProduct().getBrand().getBrand());
            cartRequest.setSize(cartItems.get(i).getProductRepo().getSize());
            cartRequest.setImage(cartItems.get(i).getProductRepo().getProduct().getImgs().get(0).getNameImg());
            cartRequest.setName(cartItems.get(i).getProductRepo().getProduct().getName());
            cartRequest.setPrice(cartItems.get(i).getProductRepo().getOutPrice());
            cartRequest.setQuantity(1);
            cartRequest.setRepo(cartItems.get(i).getProductRepo().getQuantity());
            cartRequests.add(cartRequest);
        }

        return ApiResponse.builder().status(200).errors(false).data(cartRequests)
                .build();
    }

}
