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
    public void saveOwner(OwnerOuterClass.OwnerRequest request, StreamObserver<OwnerOuterClass.Owner> responseObserver) {
        Owner owner = Owner.builder()
                .id(null)
                .email(request.getEmail())
                .name(request.getName())
                .birthDate(request.getBirthDate())
                .build();
        owner = ownerRepository.save(owner);
        responseObserver.onNext(OwnerOuterClass.Owner.newBuilder()
                .setId(owner.getId().intValue())
                .setName(owner.getName())
                .setEmail(owner.getEmail())
                .setBirthDate(owner.getBirthDate())
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void updateOwner(OwnerOuterClass.OwnerUpdateRequest request, StreamObserver<OwnerOuterClass.Owner> responseObserver) {
        Long ownerId = (long)request.getId();
        if(ownerRepository.existsById(ownerId)){
            System.out.println("Owner exists");
            Owner owner = ownerRepository.findById(ownerId).get();
            owner.setName(request.getName());
            owner.setEmail(request.getEmail());
            owner.setBirthDate(request.getBirthDate());
            System.out.println("Owner updated");
            System.out.println(owner.getName());
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

    @Override
    public void deleteOwner(OwnerOuterClass.OwnerId request, StreamObserver<OwnerOuterClass.Empty> responseObserver) {
        Long ownerId = (long)request.getId();
        if(ownerRepository.existsById(ownerId)){
            ownerRepository.deleteById(ownerId);
        }
        responseObserver.onNext(OwnerOuterClass.Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void getVehicules(OwnerOuterClass.Empty request, StreamObserver<OwnerOuterClass.VehiculeList> responseObserver) {
        Iterable<Vehicle> vehicles = vehicleRepository.findAll();
        OwnerOuterClass.VehiculeList.Builder responseBuilder = OwnerOuterClass.VehiculeList.newBuilder();
        for (Vehicle vehicle : vehicles) {
            OwnerOuterClass.Vehicule vehiculeResponse = OwnerOuterClass.Vehicule.newBuilder()
                    .setId(vehicle.getId().intValue())
                    .setRegNumber(vehicle.getRegNumber())
                    .setBrand(vehicle.getBrand())
                    .setFiscalPower(vehicle.getFiscalPower())
                    .setModel(vehicle.getModel())
                    .build();
            responseBuilder.addVehicules(vehiculeResponse);
        }
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void getVehicule(OwnerOuterClass.VehicleId request, StreamObserver<OwnerOuterClass.Vehicule> responseObserver) {
        Long vehicleId = (long)request.getId();
        if(vehicleRepository.existsById(vehicleId)){
            Vehicle vehicle = vehicleRepository.findById(vehicleId).orElse(null);
            OwnerOuterClass.Vehicule vehiculeResponse = OwnerOuterClass.Vehicule.newBuilder()
                    .setId(vehicle.getId().intValue())
                    .setRegNumber(vehicle.getRegNumber())
                    .setBrand(vehicle.getBrand())
                    .setFiscalPower(vehicle.getFiscalPower())
                    .setModel(vehicle.getModel())
                    .setOwner(OwnerOuterClass.Owner.newBuilder()
                            .setId(vehicle.getOwner().getId().intValue())
                            .setName(vehicle.getOwner().getName())
                            .setEmail(vehicle.getOwner().getEmail())
                            .setBirthDate(vehicle.getOwner().getBirthDate())
                            .build())
                    .build();
            responseObserver.onNext(vehiculeResponse);
        }
        responseObserver.onCompleted();
    }

    @Override
    public void saveVehicule(OwnerOuterClass.VehiculeRequest request, StreamObserver<OwnerOuterClass.Vehicule> responseObserver) {
        Vehicle vehicle = Vehicle.builder()
                .id(null)
                .regNumber(request.getRegNumber())
                .brand(request.getBrand())
                .fiscalPower(request.getFiscalPower())
                .model(request.getModel())
                .build();
        vehicle = vehicleRepository.save(vehicle);
        OwnerOuterClass.Vehicule vehiculeResponse = OwnerOuterClass.Vehicule.newBuilder()
                .setId(vehicle.getId().intValue())
                .setRegNumber(vehicle.getRegNumber())
                .setBrand(vehicle.getBrand())
                .setFiscalPower(vehicle.getFiscalPower())
                .setModel(vehicle.getModel())
                .build();
        responseObserver.onNext(vehiculeResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void updateVehicule(OwnerOuterClass.VehiculeUpdateRequest request, StreamObserver<OwnerOuterClass.Vehicule> responseObserver) {
        Long vehicleId = (long)request.getId();
        if(vehicleRepository.existsById(vehicleId)){
            Vehicle vehicle = vehicleRepository.findById(vehicleId).get();
            vehicle.setRegNumber(request.getRegNumber());
            vehicle.setBrand(request.getBrand());
            vehicle.setFiscalPower(request.getFiscalPower());
            vehicle.setModel(request.getModel());
            vehicle = vehicleRepository.save(vehicle);

            OwnerOuterClass.Vehicule vehiculeResponse = OwnerOuterClass.Vehicule.newBuilder()
                    .setId(vehicle.getId().intValue())
                    .setRegNumber(vehicle.getRegNumber())
                    .setBrand(vehicle.getBrand())
                    .setFiscalPower(vehicle.getFiscalPower())
                    .setModel(vehicle.getModel())
                    .build();
            responseObserver.onNext(vehiculeResponse);
            responseObserver.onCompleted();
        }
    }

    @Override
    public void deleteVehicule(OwnerOuterClass.VehicleId request, StreamObserver<OwnerOuterClass.Empty> responseObserver) {
        Long vehicleId = (long)request.getId();
        if(vehicleRepository.existsById(vehicleId)){
            vehicleRepository.deleteById(vehicleId);
        }
        responseObserver.onNext(OwnerOuterClass.Empty.newBuilder().build());
        responseObserver.onCompleted();
    }
}
