package com.sourabh.influencers.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sourabh.influencers.model.Influencer;
import com.sourabh.influencers.repositories.InfluencerRepository;

@RestController
@RequestMapping("/influencer")
public class InfluencerController {

    @Autowired
    InfluencerRepository influencerRepository;

    @GetMapping
    public List<Influencer> getAllInfluencers() {
        List<Influencer> allInfluencers = new ArrayList<>();

        allInfluencers = influencerRepository.findAll();

        return allInfluencers;
    }

    @GetMapping("rank/{rankNumber}")
    public Influencer getInfluencerByRank(@PathVariable Long rankNumber) {

        return influencerRepository.findByRank(rankNumber);
    }
}
