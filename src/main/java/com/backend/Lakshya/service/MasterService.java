package com.backend.Lakshya.service;

import com.backend.Lakshya.model.Master;
import com.backend.Lakshya.model.Users;
import com.backend.Lakshya.repository.MasterRepository;
import com.backend.Lakshya.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MasterService {

    @Autowired
    private MasterRepository masterRepository;
    @Autowired
    private UsersRepository usersRepository;

    public List<Master> getShopsByOwnerId(Long userId) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));


        return masterRepository.findByOwner(user.getName());
    }

    public Master updateSalesPerson(String shopName, String newSalesPerson)
    {
        Master shop = masterRepository.findById(shopName)
                .orElseThrow(() -> new RuntimeException("Shop not found: " + shopName));

        shop.setSalesPersonName(newSalesPerson);
        return masterRepository.save(shop);
    }
}
