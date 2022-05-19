package com.sicredi.votacao.adapter.transportlayers.restapi;

import com.sicredi.votacao.adapter.transportlayers.mapper.SessionMapper;
import com.sicredi.votacao.adapter.transportlayers.openapi.api.SessionsApi;
import com.sicredi.votacao.adapter.transportlayers.openapi.model.SessionInput;
import com.sicredi.votacao.adapter.transportlayers.openapi.model.SessionResult;
import com.sicredi.votacao.internal.interactors.session.OpenVoteSessionUseCase;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/")
@Api(tags = "Session")
public class SessionControllerImpl implements SessionsApi {
    private final OpenVoteSessionUseCase openVoteSessionUseCase;

    public SessionControllerImpl(OpenVoteSessionUseCase openVoteSessionUseCase) {
        this.openVoteSessionUseCase = openVoteSessionUseCase;
    }

    @Override
    public ResponseEntity<SessionResult> openVoteSession(final SessionInput sessionInput) {
        final var session = SessionMapper.INSTANCE.map(sessionInput);
        final var sessionSaved = this.openVoteSessionUseCase.execute(session);
        final var response = SessionMapper.INSTANCE.map(sessionSaved);
        return ResponseEntity.status(CREATED).body(response);
    }
}
