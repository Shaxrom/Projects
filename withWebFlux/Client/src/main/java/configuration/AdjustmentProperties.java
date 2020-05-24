package configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Data
@ConfigurationProperties("datasender")
public class AdjustmentProperties {
    private String url;
    private int    letterBoxSize                = 100;
    private int    minProcessorConcurrencyLevel = 5;
    private int    maxProcessorConcurrencyLevel = 8;
    private int    processingTime               = 500;
}
