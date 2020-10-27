package com.wyona.multitenantservice.webapp.services;

public interface TenantService {
    String createTenant(String name);
    boolean existsTenant(String uuid);
}
