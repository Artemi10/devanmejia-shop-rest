package devanmejia.controllers;

import devanmejia.models.entities.ShopUser;
import devanmejia.services.shopUser.ShopUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/api/shop")
@RestController
public class ShopUserController {
    @Autowired
    private ShopUserService shopUserService;

    @PostMapping("/user/create")
    public ResponseEntity<Object> createShopUser(@RequestBody ShopUser authShopUser){
        try{
            shopUserService.createOrderUser(authShopUser);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
