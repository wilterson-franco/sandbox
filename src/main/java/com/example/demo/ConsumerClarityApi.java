package com.example.demo;

import com.demo.A2aCriteria;
import com.demo.ConsumerClarityApiApi;
import com.demo.RequestPayload;
import com.demo.ResponsePayload;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

public class ConsumerClarityApi implements ConsumerClarityApiApi {

    private static final Logger log = LoggerFactory.getLogger(ConsumerClarityApi.class);

    @Override
    public ResponseEntity<ResponsePayload> search(RequestPayload requestPayload) {

        List<A2aCriteria> a2aCriteria = requestPayload.getA2aCriteria();

        for (var criteria : a2aCriteria) {

            log.info("transactionType: {}", criteria.getTransactionType());
            log.info("transactionValue: {}", criteria.getTransactionValue());

//            criteria.getA2aRail()
        }

        return ConsumerClarityApiApi.super.search(requestPayload);
    }
}
