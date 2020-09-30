package com.wyona.multitenantservice.webapp.controllers.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

//import org.hibernate.validator.constraints.NotEmpty;
//import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiImplicitParam;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.HashMap;

/**
 * Controller for a multi-tenant service (Version 1)
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/v1") 
public class ServiceController {

    @Autowired
    public ServiceController() {
    }

    /**
     * REST interface to handle a generic service request
     */
    @RequestMapping(value = "/service", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value="Handle a generic service request")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "Authorization", value = "Bearer JWT", 
                      required = false, dataType = "string", paramType = "header") })
    public ResponseEntity<?> getAnswer(
        @ApiParam(name = "input", value = "Generic input",required = true)
        @RequestParam(value = "input", required = true) String input,
        @ApiParam(name = "tenantId", value = "Tenant Id",required = true)
        @RequestParam(value = "tenantId", required = true) String tenantId,
        HttpServletRequest request) {

            log.info("Handle service request for tenant '" + tenantId + "' ...");
 
            StringBuilder sb = new StringBuilder("{");
            sb.append("\"input\":\"" + input + "\",");
            sb.append("\"tenant-id\":\"" + tenantId + "\",");
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
