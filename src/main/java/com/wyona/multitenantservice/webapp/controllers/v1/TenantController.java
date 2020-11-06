package com.wyona.multitenantservice.webapp.controllers.v1;

import com.wyona.multitenantservice.webapp.services.TenantService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.ApiOperation;

import com.wyona.multitenantservice.webapp.models.Tenant;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/tenant")
public class TenantController {

    @Autowired
    TenantService tenantService;

    /**
     * Create tenant
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
    @ApiOperation(value="Create a new tenant")
    public ResponseEntity<?> createTenant(
        @RequestBody Tenant tenant,
        HttpServletRequest request) {

        String tenantId = tenantService.createTenant(tenant.getName());

        StringBuilder sb = new StringBuilder("{");
        sb.append("\"tenant_id\":\"" + tenantId + "\"");
        sb.append(",");
        sb.append("\"ip\":\"" + getRemoteAddress(request) + "\"");
        sb.append("}");

        return new ResponseEntity<>(sb.toString(), HttpStatus.OK);
    }

    /**
     * Get remote host address
     * @return remote host address, e.g. '178.197.227.93'
     */
    public static String getRemoteAddress(HttpServletRequest request) {
        String remoteAddress = request.getRemoteAddr();
        String xForwardedFor = request.getHeader("X-FORWARDED-FOR");
        if (xForwardedFor != null) {
            remoteAddress = xForwardedFor;
        }
        return remoteAddress;
    }
}
