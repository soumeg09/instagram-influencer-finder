package com.sourabh.influencers.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Influencer {
    
    @Id
    private Long rank;
    private String channelInfo;
    private Long influenceScore;
    private String followers;
    private String avgLikes;
    private String posts;
    private Double sixtyDayEngRate;
    private String newPostAvgLikes;
    private String totalLikes;
    private String countryOrRegion;

}
