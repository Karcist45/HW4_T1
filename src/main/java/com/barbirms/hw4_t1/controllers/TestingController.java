package com.barbirms.hw4_t1.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestingController {
    @RequestMapping("/test/common")
    public String testCommonContent() {
        return "common content";
    }

    @RequestMapping("/test/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String testAdminContent() {
        return "admin content";
    }

    @RequestMapping("/test/premium")
    @PreAuthorize("hasRole('PREMIUM_USER')")
    public String testPremiumContent() {
        return "premium content";
    }

    @RequestMapping("/test/guest")
    @PreAuthorize("hasRole('GUEST')")
    public String testGuestContent() {
        return "guest content";
    }
}
