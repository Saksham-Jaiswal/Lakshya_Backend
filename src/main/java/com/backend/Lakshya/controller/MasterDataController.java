package com.backend.Lakshya.controller;

import com.backend.Lakshya.model.Master;
import com.backend.Lakshya.repository.MasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/master")
public class MasterDataController {

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
        masterRepository.save(master);
    }
}
