package com.wyona.multitenantservice.webapp.controllers.v1;

import com.wyona.multitenantservice.webapp.models.SimilarSentence;
import com.wyona.multitenantservice.webapp.services.TenantService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiImplicitParam;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for a multi-tenant service (Version 1)
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/v1/corpus") 
public class SemanticSearchController {

    @Autowired
    public SemanticSearchController() {
    }

    @Autowired
    TenantService tenantService;

    /**
     * REST interface to search for similar sentences
     */
    @RequestMapping(value = "/{corpus_id}/similar", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value="Search for similar sentences")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "Authorization", value = "Bearer JWT", 
                      required = false, dataType = "string", paramType = "header") })
    public ResponseEntity<?> getSimilarSentences(
        @ApiParam(name = "sentence", value = "Sentence for which similar sentences are searched, e.g. 'What is the best mountain bike in the price range of USD 2000?'",required = true)
        @RequestParam(value = "sentence", required = true) String sentence,
        @ApiParam(name = "corpus_id", value = "Corpus/Tenant Id",required = true)
        @PathVariable(value = "corpus_id", required = true) String corpusId,
        HttpServletRequest request) {

            log.info("Get sentences from corpus '" + corpusId + "' which are similar to sentence '" + sentence + "' ...");

            if (tenantService.existsTenant(corpusId)) {
/*
                StringBuilder sb = new StringBuilder("{");
                sb.append("\"query_sentence\":\"" + sentence + "\",");
                sb.append("\"corpus_id\":\"" + corpusId + "\",");
                // TODO: Add tenant name
                //sb.append("\"tenant_name\":\"" + tenantService.getTenant(corpusId) + "\",");
                sb.append("\"ip\":\"" + getRemoteAddress(request) + "\"");
                sb.append("}");
*/

                List<SimilarSentence> similarSentences = new ArrayList<SimilarSentence>();
                similarSentences.add(new SimilarSentence("Best mountain bike under USD 2000?", 0.1));
                similarSentences.add(new SimilarSentence("Where can I buy a good mountain bike for less than USD 2500?", 0.2));

                return new ResponseEntity<>(similarSentences, HttpStatus.OK);
            } else {
                log.error("No such corpus/tenant '" + corpusId + "'!");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
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
