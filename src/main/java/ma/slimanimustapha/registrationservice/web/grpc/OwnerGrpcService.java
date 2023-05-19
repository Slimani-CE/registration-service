package ma.slimanimustapha.registrationservice.web.grpc;

import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import ma.slimanimustapha.registrationservice.entities.Owner;
import ma.slimanimustapha.registrationservice.entities.Vehicle;
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
            OwnerOuterClass.Owner ownerResponse = OwnerOuterClass.Owner.newBuilder()
                    .setId(owner.getId().intValue())
                    .setName(owner.getName())
                    .setEmail(owner.getEmail())
                    .setBirthDate(owner.getBirthDate())
                    .build();
            responseBuilder.addOwners(ownerResponse);
        }
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void getOwner(OwnerOuterClass.OwnerId request, StreamObserver<OwnerOuterClass.Owner> responseObserver) {
        Long ownerId = (long)request.getId();
        if(ownerRepository.existsById(ownerId)){
            Owner owner = ownerRepository.findById(ownerId).orElse(null);
            OwnerOuterClass.Owner ownerResponse = OwnerOuterClass.Owner.newBuilder()
                    .setId(owner.getId().intValue())
                    .setName(owner.getName())
                    .setEmail(owner.getEmail())
                    .setBirthDate(owner.getBirthDate())
                    .build();
            responseObserver.onNext(ownerResponse);
        }
        responseObserver.onCompleted();
    }

    @Override
    public void updateOwner(OwnerOuterClass.OwnerUpdateRequest request, StreamObserver<OwnerOuterClass.Owner> responseObserver) {
        Long ownerId = (long)request.getId();
        if(ownerRepository.existsById(ownerId)){
            System.out.println("Owner exists");
            Owner owner = ownerRepository.findById(ownerId).get();
            if (request.getName().isEmpty()) owner.setName(request.getName());
            if (request.getEmail().isEmpty()) owner.setEmail(request.getEmail());
            if (request.getBirthDate().isEmpty()) owner.setBirthDate(request.getBirthDate());

            owner = ownerRepository.save(owner);

            OwnerOuterClass.Owner ownerResponse = OwnerOuterClass.Owner.newBuilder()
                    .setId(owner.getId().intValue())
                    .setName(owner.getName())
                    .setEmail(owner.getEmail())
                    .setBirthDate(owner.getBirthDate())
                    .build();
            responseObserver.onNext(ownerResponse);
            responseObserver.onCompleted();
        }
    }
}
