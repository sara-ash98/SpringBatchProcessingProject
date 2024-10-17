package com.sara.Customer_batch_processing.Config;

import java.util.Arrays;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import com.sara.Customer_batch_processing.Repository.CustomerRepository;
import com.sara.Customer_batch_processing.entity.Customer;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class SpringBatchConfig {
	@Autowired
	private CustomerRepository repository;
	@Bean
    @StepScope
    public MultiFileItemReader<Customer> multiFileReader() {
        List<String> fileNames = Arrays.asList(
            "src/main/resources/customers-100.csv",
            "src/main/resources/customers-1000.csv",
            "src/main/resources/customers-10000.csv",
            "src/main/resources/customers-100000.csv"
        );

        return new MultiFileItemReader<>(fileNames, customLineMapper());
    }
	
	@Bean
	public LineMapper<Customer> customLineMapper(){

	    DefaultLineMapper<Customer> lineMapper = new DefaultLineMapper<>();

	    DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
	    tokenizer.setDelimiter(DelimitedLineTokenizer.DELIMITER_COMMA);
	    tokenizer.setStrict(false);

	  
	    tokenizer.setNames("Index", "Customer Id", "First Name", "Last Name", 
	                       "Company", "City", "Country", "Phone 1", "Phone 2", 
	                       "Email", "Subscription Date", "Website");
	    lineMapper.setLineTokenizer(tokenizer);
	    lineMapper.setFieldSetMapper(new CustomFieldSetMapper());

	    

	    return lineMapper;
	}

	
	  
	
	@Bean
	public ProcessorConfig processor() {
		return new ProcessorConfig();
	}
	@Bean
	public RepositoryItemWriter<Customer> writer(){
		RepositoryItemWriter<Customer> writer=new RepositoryItemWriter<>();
		writer.setRepository(repository);
		writer.setMethodName("save");
		return writer;
	}
	@Bean
	public Step step1(JobRepository jobRepository,PlatformTransactionManager transactionManager) {
		return new StepBuilder("csv-step",jobRepository).<Customer,Customer>chunk(10,transactionManager)
				.reader(multiFileReader())
				.processor(processor())
				.writer(writer())
				.taskExecutor(taskExecutor())
				.build();
	}
	@Bean
	public Job runJob(JobRepository jobRepository,PlatformTransactionManager transactionManager) {
		return new JobBuilder("importCustomers",jobRepository)
				.flow(step1(jobRepository,transactionManager)).end().build();
	}
	@Bean
	public TaskExecutor taskExecutor() {
		SimpleAsyncTaskExecutor asyncTaskExecutor=new SimpleAsyncTaskExecutor();
		asyncTaskExecutor.setConcurrencyLimit(10);
		return taskExecutor();
	}
	
}
