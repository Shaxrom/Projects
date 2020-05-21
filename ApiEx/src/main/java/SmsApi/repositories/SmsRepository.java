package SmsApi.repositories;

import SmsApi.models.SmsModels;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsRepository extends JpaRepository <SmsModels, Long> {}
