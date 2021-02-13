package com.wyona.multitenantservice.webapp.services;

/**
 *
 */
public interface TenantService {

    /**
     * @param name Tenant name, e.g. "Wyona"
     * @return UUID of newly created tenant, e.g. "8ee9f114-9113-404b-8947-2367d4bd0da5"
     */
    String createTenant(String name);

    /**
     * @param uuid UUID of tenant
     * @return true when tenant exists and false otherwise
     */
    boolean existsTenant(String uuid);
}
