package ApiEx.withWebFlux.repositories;

import ApiEx.withWebFlux.models.SmsModels;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsRepository extends JpaRepository <SmsModels, Long> {}
