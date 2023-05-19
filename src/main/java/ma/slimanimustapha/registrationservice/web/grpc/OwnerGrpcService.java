package ma.slimanimustapha.registrationservice.web.grpc;

import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import ma.slimanimustapha.registrationservice.entities.Owner;
import ma.slimanimustapha.registrationservice.repositories.OwnerRepository;
import ma.slimanimustapha.registrationservice.repositories.VehicleRepository;
import ma.slimanimustapha.registrationservice.web.grpc.stubs.OwnerOuterClass;
import ma.slimanimustapha.registrationservice.web.grpc.stubs.OwnerOuterClass.OwnerList;
import ma.slimanimustapha.registrationservice.web.grpc.stubs.OwnerServiceGrpc;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@AllArgsConstructor
public class OwnerGrpcService extends OwnerServiceGrpc.OwnerServiceImplBase{
    private final OwnerRepository ownerRepository;
    private final VehicleRepository vehicleRepository;

    @Override
    public void getOwners(OwnerOuterClass.Empty request, StreamObserver<OwnerOuterClass.OwnerList> responseObserver) {
        Iterable<Owner> owners = ownerRepository.findAll();
        OwnerList.Builder responseBuilder = OwnerList.newBuilder();
        for (Owner owner : owners) {
            OwnerOuterClass.Owner grpcOwner = OwnerOuterClass.Owner.newBuilder()
                    .setId(owner.getId().intValue())
                    .setName(owner.getName())
                    .setEmail(owner.getEmail())
                    .setBirthDate(owner.getBirthDate())
                    .build();
            responseBuilder.addOwners(grpcOwner);
        }
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }
}
