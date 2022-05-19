package com.sicredi.votacao.adapter.transportlayers.restapi;

import com.sicredi.votacao.adapter.transportlayers.mapper.AssociateMapper;
import com.sicredi.votacao.adapter.transportlayers.openapi.api.AssociatesApi;
import com.sicredi.votacao.adapter.transportlayers.openapi.model.AssociateInput;
import com.sicredi.votacao.adapter.transportlayers.openapi.model.AssociateResult;
import com.sicredi.votacao.internal.interactors.associate.*;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/")
@Api(tags = "Associate")
public class AssociateControllerImpl implements AssociatesApi {

    private final CreateAssociateUseCase createAssociateUseCase;
    private final DeleteAssociateByIdUseCase deleteAssociateByIdUseCase;
    private final GetAssociateByIdUseCase getAssociateByIdUseCase;
    private final GetAllAssociatesUseCase getAllAssociatesUseCase;
    private final UpdateAssociateUseCase updateAssociateUseCase;

    public AssociateControllerImpl(CreateAssociateUseCase createAssociateUseCase,
                                   DeleteAssociateByIdUseCase deleteAssociateByIdUseCase,
                                   GetAssociateByIdUseCase getAssociateByIdUseCase,
                                   GetAllAssociatesUseCase getAllAssociatesUseCase,
                                   UpdateAssociateUseCase updateAssociateUseCase) {
        this.createAssociateUseCase = createAssociateUseCase;
        this.deleteAssociateByIdUseCase = deleteAssociateByIdUseCase;
        this.getAssociateByIdUseCase = getAssociateByIdUseCase;
        this.getAllAssociatesUseCase = getAllAssociatesUseCase;
        this.updateAssociateUseCase = updateAssociateUseCase;
    }

    @Override
    public ResponseEntity<AssociateResult> createAssociate(final AssociateInput associateInput) {
        final var associate = AssociateMapper.INSTANCE.map(associateInput);
        final var associateSaved = this.createAssociateUseCase.execute(associate);
        final var response = AssociateMapper.INSTANCE.map(associateSaved);
        return ResponseEntity.status(CREATED).body(response);
    }

    @Override
    public ResponseEntity<Void> deleteAssociate(final String associateId) {
        this.deleteAssociateByIdUseCase.execute(associateId);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<AssociateResult> getAssociate(final String associateId) {
        final var associate = this.getAssociateByIdUseCase.execute(associateId);
        final var response = AssociateMapper.INSTANCE.map(associate);
        return ResponseEntity.status(OK).body(response);
    }

    @Override
    public ResponseEntity<List<AssociateResult>> getAllAssociates() {
        final var response = this.getAllAssociatesUseCase.execute().stream()
                .map(a -> AssociateMapper.INSTANCE.map(a))
                .collect(Collectors.toList());
        return ResponseEntity.status(OK).body(response);
    }

    @Override
    public ResponseEntity<AssociateResult> updateAssociate(final String associateId, final AssociateInput associateInput) {
        final var associate = AssociateMapper.INSTANCE.map(associateInput);
        final var associateSaved = this.updateAssociateUseCase.execute(associateId, associate);
        final var response = AssociateMapper.INSTANCE.map(associateSaved);
        return ResponseEntity.status(OK).body(response);
    }

}