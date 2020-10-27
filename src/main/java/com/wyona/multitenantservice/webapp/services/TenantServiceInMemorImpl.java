package com.wyona.multitenantservice.webapp.services;

import com.wyona.multitenantservice.webapp.models.Tenant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class TenantServiceInMemorImpl implements TenantService {

    HashMap<UUID, Tenant> tenantHashMap = new HashMap<>();

    @Override
    public String createTenant(String name) {
        Tenant newTenant = new Tenant(name);

        //check if tenant already exist, if does return its UUID
        for(Map.Entry<UUID, Tenant> entry : tenantHashMap.entrySet()) {
            if(entry.getValue().getName().equals(newTenant.getName())) {
                log.info("Tenant already exists");
                return entry.getKey().toString();
            }
        }

        UUID tenantUUID = UUID.randomUUID();
        tenantHashMap.put(tenantUUID, newTenant);

        return tenantUUID.toString();
    }

    @Override
    public boolean existsTenant(String uuid) {
        Tenant tenant = tenantHashMap.get(UUID.fromString(uuid));
        if(tenant != null)
            return true;

        return false;
    }
}
