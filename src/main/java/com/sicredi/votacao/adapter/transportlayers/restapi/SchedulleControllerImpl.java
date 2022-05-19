package com.sicredi.votacao.adapter.transportlayers.restapi;

import com.sicredi.votacao.adapter.transportlayers.mapper.SchedulleMapper;
import com.sicredi.votacao.adapter.transportlayers.openapi.api.SchedullesApi;
import com.sicredi.votacao.adapter.transportlayers.openapi.model.ResultOfSchedulleResult;
import com.sicredi.votacao.adapter.transportlayers.openapi.model.SchedulleInput;
import com.sicredi.votacao.adapter.transportlayers.openapi.model.SchedulleResult;
import com.sicredi.votacao.internal.interactors.schedulle.*;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/")
@Api(tags = "Schedulle")
public class SchedulleControllerImpl implements SchedullesApi {

    private final CreateSchedulleUseCase createSchedulleUseCase;
    private final DeleteSchedulleByIdUseCase deleteSchedulleByIdUseCase;
    private final GetSchedulleByIdUseCase getSchedulleByIdUseCase;
    private final GetAllSchedullesUseCase getAllSchedullesUseCase;
    private final UpdateSchedulleUseCase updateSchedulleUseCase;

    private final GetResultsOfSchedulleSessionVoteUseCase getResultsOfSchedulleSessionVoteUseCase;

    public SchedulleControllerImpl(CreateSchedulleUseCase createSchedulleUseCase,
                                   DeleteSchedulleByIdUseCase deleteSchedulleByIdUseCase,
                                   GetSchedulleByIdUseCase getSchedulleByIdUseCase,
                                   GetAllSchedullesUseCase getAllSchedullesUseCase,
                                   UpdateSchedulleUseCase updateSchedulleUseCase,
                                   GetResultsOfSchedulleSessionVoteUseCase getResultsOfSchedulleSessionVoteUseCase) {
        this.createSchedulleUseCase = createSchedulleUseCase;
        this.deleteSchedulleByIdUseCase = deleteSchedulleByIdUseCase;
        this.getSchedulleByIdUseCase = getSchedulleByIdUseCase;
        this.getAllSchedullesUseCase = getAllSchedullesUseCase;
        this.updateSchedulleUseCase = updateSchedulleUseCase;
        this.getResultsOfSchedulleSessionVoteUseCase = getResultsOfSchedulleSessionVoteUseCase;
    }

    @Override
    public ResponseEntity<SchedulleResult> createSchedulle(final SchedulleInput schedulleInput) {
        final var schedulle = SchedulleMapper.INSTANCE.map(schedulleInput);
        final var schedulleSaved = this.createSchedulleUseCase.execute(schedulle);
        final var response = SchedulleMapper.INSTANCE.map(schedulleSaved);
        return ResponseEntity.status(CREATED).body(response);
    }

    @Override
    public ResponseEntity<Void> deleteSchedulle(final String schedulleId) {
        this.deleteSchedulleByIdUseCase.execute(schedulleId);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<SchedulleResult> getSchedulle(final String schedulleId) {
        final var schedulle = this.getSchedulleByIdUseCase.execute(schedulleId);
        final var response = SchedulleMapper.INSTANCE.map(schedulle);
        return ResponseEntity.status(OK).body(response);
    }

    @Override
    public ResponseEntity<List<SchedulleResult>> getAllSchedulles() {
        final var response = this.getAllSchedullesUseCase.execute().stream()
                .map(a -> SchedulleMapper.INSTANCE.map(a))
                .collect(Collectors.toList());
        return ResponseEntity.status(OK).body(response);
    }

    @Override
    public ResponseEntity<SchedulleResult> updateSchedulle(final String schedulleId, final SchedulleInput schedulleInput) {
        final var schedulle = SchedulleMapper.INSTANCE.map(schedulleInput);
        final var schedulleSaved = this.updateSchedulleUseCase.execute(schedulleId, schedulle);
        final var response = SchedulleMapper.INSTANCE.map(schedulleSaved);
        return ResponseEntity.status(OK).body(response);
    }

    @Override
    public ResponseEntity<List<ResultOfSchedulleResult>> getResults(final String schedulleId) {
        final var result = this.getResultsOfSchedulleSessionVoteUseCase.execute(schedulleId);
        final var response = result.stream()
                .map(r -> SchedulleMapper.INSTANCE.map(r))
                .collect(Collectors.toList());
        return ResponseEntity.status(OK).body(response);
    }

}