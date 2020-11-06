package com.wyona.multitenantservice.webapp.services;

import com.wyona.multitenantservice.webapp.models.Tenant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class TenantServiceInMemoryImpl implements TenantService {

    HashMap<UUID, Tenant> tenantHashMap = new HashMap<>();

    @Override
    public String createTenant(String name) {
        Tenant tenant = new Tenant(name);

        // INFO: Check whether tenant already exist, if so, then return its UUID
        for(Map.Entry<UUID, Tenant> entry : tenantHashMap.entrySet()) {
            if(entry.getValue().getName().equals(tenant.getName())) {
                log.info("Tenant '" + name + "' already exists");
                return entry.getKey().toString();
            }
        }

        UUID tenantUUID = UUID.randomUUID();
        tenantHashMap.put(tenantUUID, tenant);

        log.info("New tenant '" + tenant.getName() + "' created with UUID '" + tenantUUID + "'.");

        return tenantUUID.toString();
    }

    @Override
    public boolean existsTenant(String uuid) {
        UUID _uuid = null;
        try {
            _uuid = UUID.fromString(uuid);
        } catch(Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
        Tenant tenant = tenantHashMap.get(_uuid);
        if(tenant != null)
            return true;

        return false;
    }
}
