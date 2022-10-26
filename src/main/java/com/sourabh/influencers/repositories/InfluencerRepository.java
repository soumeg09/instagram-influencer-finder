package com.sourabh.influencers.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sourabh.influencers.model.Influencer;

public interface InfluencerRepository extends CrudRepository<Influencer, String>{

    List<Influencer> findAll();


    Influencer findByRank(Long rank);
    
}
