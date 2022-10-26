package com.sourabh.influencers.batch;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.sourabh.influencers.dto.InfluencerInput;
import com.sourabh.influencers.model.Influencer;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    private String[] FIELD_NAMES = new String[] { "rank", "channelInfo", "influenceScore", "followers", "avgLikes",
            "posts", "sixtyDayEngRate", "newPostAvgLikes", "totalLikes", "countryOrRegion" };

    @Bean
    public FlatFileItemReader<InfluencerInput> reader() {
        return new FlatFileItemReaderBuilder<InfluencerInput>()
                .name("influencerItemReader")
                .resource(new ClassPathResource("Top_Influencers.csv"))
                .delimited()
                .names(FIELD_NAMES)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<InfluencerInput>() {
                    {
                        setTargetType(InfluencerInput.class);
                    }
                })
                .build();
    }

    @Bean
    public InfluencersProcessor processor() {
        return new InfluencersProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Influencer> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Influencer>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO influencer (rank, channel_info, influence_score, followers, avg_likes, posts, sixty_day_eng_rate, new_post_avg_likes, total_likes, country_or_region) VALUES (:rank, :channelInfo, :influenceScore, :followers, :avgLikes, :posts, :sixtyDayEngRate, :newPostAvgLikes, :totalLikes, :countryOrRegion)")
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public Job importInfluencerJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importInfluencerJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<Influencer> writer) {
        return stepBuilderFactory.get("step1")
                .<InfluencerInput, Influencer>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }
}