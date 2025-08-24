package com.backend.Lakshya.controller;

import com.backend.Lakshya.model.Master;
import com.backend.Lakshya.pojo.UpdateSalesPersonRequest;
import com.backend.Lakshya.repository.MasterRepository;
import com.backend.Lakshya.service.MasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/master")
public class MasterDataController {

    @Autowired
    MasterService masterService;

    @Autowired
    private MasterRepository masterRepository;

    @GetMapping
    public List<Master> getAll()
    {
        return masterRepository.findAll();
    }

    @PostMapping
    public Master addData(@RequestBody Master master)
    {
        return masterRepository.save(master);
    }

    @GetMapping("/{userId}")
    public List<Master> getShopDetails(@PathVariable Long userId){
        return masterService.getShopsByOwnerId(userId);
    }

    @PutMapping("/{shopName}/salesperson")
    public Master updateSalesPerson(
            @PathVariable String shopName,
            @RequestBody UpdateSalesPersonRequest request) {
        return masterService.updateSalesPerson(shopName, request.getNewSalesPerson());
    }

}
