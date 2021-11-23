package com.cloud.domainloader.service;

import com.cloud.domainloader.model.WebDomain;
import com.cloud.domainloader.model.WebDomainList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class WebDomainLoaderService {
    private KafkaTemplate<String, WebDomain> kafkaTemplate;
    private final static String KAFKA_DOMAIN_TOPIC= "web-domains";

    public void load(String name) {

        Mono<WebDomainList> domainListMono= WebClient.create()
                .get()
                .uri("https://domainsdb.info/?query="+name+"&tld=bundle_all_zones")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(WebDomainList.class);

        domainListMono.subscribe(domains -> domains.getWebDomains().forEach(domain -> {

            kafkaTemplate.send(KAFKA_DOMAIN_TOPIC, domain);
            log.info("topic: {}, value: {}", KAFKA_DOMAIN_TOPIC, domain);
        }));
    }
}
