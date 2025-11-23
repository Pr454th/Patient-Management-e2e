package com.pm.billing_service.grpc;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import billing.BillingServiceGrpc.BillingServiceImplBase;

import java.util.UUID;

@GrpcService
@Slf4j
public class BillingGrpcService extends BillingServiceImplBase {
    @Override
    public void createBillingAccount(billing.BillingRequest billingRequest, StreamObserver<billing.BillingResponse> requestStreamObserver)
    {
      log.info("createBillingAccount request received {}", billingRequest.toString());

      // Business logic - e.g save to db, perform and do some calculations...

        billing.BillingResponse response= billing.BillingResponse.newBuilder()
                .setAccountId(billingRequest.getPatientId())
                .setStatus("ACTIVE")
                .build();

        requestStreamObserver.onNext(response);
        requestStreamObserver.onCompleted();
    }
}
