package com.sourabh.influencers.dto;

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
public class InfluencerInput {
    
    private String rank;
    private String channelInfo;
    private String influenceScore;
    private String followers;
    private String avgLikes;
    private String posts;
    private String sixtyDayEngRate;
    private String newPostAvgLikes;
    private String totalLikes;
    private String countryOrRegion;

}
