package com.wyona.multitenantservice.webapp.services;

/**
 *
 */
public interface TenantService {

    /**
     * @param name Tenant name, e.g. "Wyona"
     * @return UUID of newly created tenant
     */
    String createTenant(String name);

    /**
     * @param uuid UUID of tenant
     * @return true when tenant exists and false otherwise
     */
    boolean existsTenant(String uuid);
}
