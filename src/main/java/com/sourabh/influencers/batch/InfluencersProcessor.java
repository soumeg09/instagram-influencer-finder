package com.sourabh.influencers.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.sourabh.influencers.dto.InfluencerInput;
import com.sourabh.influencers.model.Influencer;

public class InfluencersProcessor implements ItemProcessor<InfluencerInput, Influencer> {

    private static final Logger log = LoggerFactory.getLogger(InfluencersProcessor.class);

    @Override
    public Influencer process(final InfluencerInput influencerInput) throws Exception {
        log.info("Method process(influencerInput) starts.");
        final Influencer influencer = new Influencer();

        influencer.setAvgLikes(influencerInput.getAvgLikes());
        influencer.setChannelInfo(influencerInput.getChannelInfo());
        influencer.setCountryOrRegion(influencerInput.getCountryOrRegion());
        influencer.setFollowers(influencerInput.getFollowers());
        influencer.setInfluenceScore(Long.parseLong(influencerInput.getInfluenceScore()));
        influencer.setNewPostAvgLikes(influencerInput.getNewPostAvgLikes());
        influencer.setPosts(influencerInput.getPosts());
        influencer.setRank(Long.parseLong(influencerInput.getRank()));
        influencer.setSixtyDayEngRate(Double.parseDouble(influencerInput.getSixtyDayEngRate()));
        influencer.setTotalLikes(influencerInput.getTotalLikes());

        log.info("Method process(influencerInput) ends.");
        return influencer;
    }

}
