package com.example.demo.api;

import com.example.demo.api.dto.IssuerInboundRequest;
import com.example.demo.api.dto.IssuerUriResponse;
import com.example.demo.service.IssuerFlowService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/issuers")
public class IssuerController {

    private final IssuerFlowService issuerFlowService;

    public IssuerController(IssuerFlowService issuerFlowService) {
        this.issuerFlowService = issuerFlowService;
    }

    @PostMapping("/resolve-uri")
    public ResponseEntity<IssuerUriResponse> resolveUri(@Valid @RequestBody IssuerInboundRequest request) {
        String uri = issuerFlowService.process(request);
        return ResponseEntity.ok(new IssuerUriResponse(uri));
    }
}