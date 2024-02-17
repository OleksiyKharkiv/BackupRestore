package org.example.backuprestore.config;
package org.example.backuprestore.config;

import org.example.backuprestore.processor.UserWorkoutItemProcessor;
import org.example.backuprestore.reader.JsonBsonItemReader;
import org.example.backuprestore.writer.MongoItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BackupRestoreJobConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job backupRestoreJob() {
        return jobBuilderFactory.get("backupRestoreJob")
                .start(restoreStep())
                .build();
    }

    @Bean
    public Step restoreStep() {
        return stepBuilderFactory.get("restoreStep")
                .<UserWorkout, UserWorkout>chunk(100)
                .reader(jsonBsonItemReader())
                .processor(userWorkoutItemProcessor())
                .writer(mongoItemWriter())
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