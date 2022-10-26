package com.sourabh.influencers.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.sourabh.influencers.model.Influencer;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

  private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public void afterJob(JobExecution jobExecution) {
    if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
      log.info("!!! JOB FINISHED! Time to verify the results");

      jdbcTemplate.query(
          "SELECT rank, channel_info, influence_score, followers, avg_likes, posts, sixty_day_eng_rate, new_post_avg_likes, total_likes, country_or_region FROM influencer",
          (rs, row) -> new Influencer(
              Long.parseLong(rs.getString(1)),
              rs.getString(2),
              Long.parseLong(rs.getString(3)),
              rs.getString(4),
              rs.getString(5),
              rs.getString(6),
              Double.parseDouble(rs.getString(7)),
              rs.getString(8),
              rs.getString(9),
              rs.getString(10)))
          .forEach(influencer -> log.info("Found <" + influencer + "> in the database."));
    }
  }
}
