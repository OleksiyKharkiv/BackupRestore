package com.example.backuprestore.config;

import com.example.backuprestore.model.UserWorkout;
import com.example.backuprestore.processor.UserWorkoutItemProcessor;
import com.example.backuprestore.reader.JsonBsonItemReader;
import com.example.backuprestore.writer.MongoItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BackupRestoreJobConfig {

    private final org.springframework.batch.core.configuration.annotation.StepBuilderFactory stepBuilderFactory;

    public BackupRestoreJobConfig(org.springframework.batch.core.configuration.annotation.StepBuilderFactory stepBuilderFactory) {
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Job backupRestoreJob(Step restoreStep) {
        return jobBuilderFactory.get("backupRestoreJob")
                .start(restoreStep)
                .build();
    }

    @Bean
    @JobScope
    public Step restoreStep(JsonBsonItemReader<UserWorkout> itemReader,
                            UserWorkoutItemProcessor itemProcessor,
                            MongoItemWriter<UserWorkout> itemWriter) {
        return stepBuilderFactory.get("restoreStep")
                .<UserWorkout, UserWorkout>chunk(100)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();
    }

    @Bean
    public JsonBsonItemReader<UserWorkout> jsonBsonItemReader() {
        return new JsonBsonItemReader<>();
    }

    @Bean
    public UserWorkoutItemProcessor userWorkoutItemProcessor() {
        return new UserWorkoutItemProcessor();
    }

    @Bean
    public MongoItemWriter<UserWorkout> mongoItemWriter() {
        return new MongoItemWriter<>();
    }
}
