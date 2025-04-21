package com.farmer.be.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(
                Object.class,
                Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries())
            )
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build()
        );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.farmer.be.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.farmer.be.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.farmer.be.domain.User.class.getName());
            createCache(cm, com.farmer.be.domain.Authority.class.getName());
            createCache(cm, com.farmer.be.domain.User.class.getName() + ".authorities");
            createCache(cm, com.farmer.be.domain.Client.class.getName());
            createCache(cm, com.farmer.be.domain.Client.class.getName() + ".hospitals");
            createCache(cm, com.farmer.be.domain.Client.class.getName() + ".patientRegistrationDetails");
            createCache(cm, com.farmer.be.domain.Hospital.class.getName());
            createCache(cm, com.farmer.be.domain.Hospital.class.getName() + ".patientRegistrationDetails");
            createCache(cm, com.farmer.be.domain.Hospital.class.getName() + ".departments");
            createCache(cm, com.farmer.be.domain.Hospital.class.getName() + ".beds");
            createCache(cm, com.farmer.be.domain.Hospital.class.getName() + ".medicineBatches");
            createCache(cm, com.farmer.be.domain.Hospital.class.getName() + ".admissions");
            createCache(cm, com.farmer.be.domain.Address.class.getName());
            createCache(cm, com.farmer.be.domain.Patient.class.getName());
            createCache(cm, com.farmer.be.domain.Patient.class.getName() + ".diagnosticTestReports");
            createCache(cm, com.farmer.be.domain.Patient.class.getName() + ".doctorVisits");
            createCache(cm, com.farmer.be.domain.Patient.class.getName() + ".admissions");
            createCache(cm, com.farmer.be.domain.PatientRegistrationDetails.class.getName());
            createCache(cm, com.farmer.be.domain.ReferralDoctor.class.getName());
            createCache(cm, com.farmer.be.domain.ReferralDoctor.class.getName() + ".patientRegistrationDetails");
            createCache(cm, com.farmer.be.domain.Department.class.getName());
            createCache(cm, com.farmer.be.domain.Department.class.getName() + ".employees");
            createCache(cm, com.farmer.be.domain.Department.class.getName() + ".diagnosticTests");
            createCache(cm, com.farmer.be.domain.Employee.class.getName());
            createCache(cm, com.farmer.be.domain.Employee.class.getName() + ".doctorVisitTypes");
            createCache(cm, com.farmer.be.domain.Employee.class.getName() + ".admissions");
            createCache(cm, com.farmer.be.domain.Employee.class.getName() + ".qualifications");
            createCache(cm, com.farmer.be.domain.Qualification.class.getName());
            createCache(cm, com.farmer.be.domain.Qualification.class.getName() + ".employees");
            createCache(cm, com.farmer.be.domain.DiagnosticTest.class.getName());
            createCache(cm, com.farmer.be.domain.DiagnosticTest.class.getName() + ".employees");
            createCache(cm, com.farmer.be.domain.DiagnosticTest.class.getName() + ".diagnosticTestReports");
            createCache(cm, com.farmer.be.domain.DiagnosticTest.class.getName() + ".slots");
            createCache(cm, com.farmer.be.domain.DiagnosticTestReport.class.getName());
            createCache(cm, com.farmer.be.domain.DoctorVisitType.class.getName());
            createCache(cm, com.farmer.be.domain.DoctorVisitType.class.getName() + ".slots");
            createCache(cm, com.farmer.be.domain.DoctorVisitType.class.getName() + ".doctorVisits");
            createCache(cm, com.farmer.be.domain.Slot.class.getName());
            createCache(cm, com.farmer.be.domain.DoctorVisit.class.getName());
            createCache(cm, com.farmer.be.domain.Prescription.class.getName());
            createCache(cm, com.farmer.be.domain.Prescription.class.getName() + ".medicineDoses");
            createCache(cm, com.farmer.be.domain.Prescription.class.getName() + ".diagnosticTests");
            createCache(cm, com.farmer.be.domain.MedicineDose.class.getName());
            createCache(cm, com.farmer.be.domain.Bed.class.getName());
            createCache(cm, com.farmer.be.domain.Bed.class.getName() + ".admissions");
            createCache(cm, com.farmer.be.domain.Medicine.class.getName());
            createCache(cm, com.farmer.be.domain.Medicine.class.getName() + ".medicineDoses");
            createCache(cm, com.farmer.be.domain.Medicine.class.getName() + ".medicineBatches");
            createCache(cm, com.farmer.be.domain.MedicineBatch.class.getName());
            createCache(cm, com.farmer.be.domain.Admission.class.getName());
            createCache(cm, com.farmer.be.domain.Admission.class.getName() + ".medicineBatches");
            createCache(cm, com.farmer.be.domain.Admission.class.getName() + ".diagnosticTestReports");
            createCache(cm, com.farmer.be.domain.Admission.class.getName() + ".doctorVisits");
            createCache(cm, com.farmer.be.domain.Admission.class.getName() + ".ledgerItems");
            createCache(cm, com.farmer.be.domain.Admission.class.getName() + ".beds");
            createCache(cm, com.farmer.be.domain.LedgerItem.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
