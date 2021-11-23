package com.cloud.domainloader.controller;

import com.cloud.domainloader.service.WebDomainLoaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/domain")
@RequiredArgsConstructor
public class WebDomainController {

    private final WebDomainLoaderService webDomainLoaderService;

    @GetMapping("/load/{domain}")
    public String load(@PathVariable("domain") final String name) {
        webDomainLoaderService.load(name);
        return "Domain loader has scrapped your data";
    }
}
