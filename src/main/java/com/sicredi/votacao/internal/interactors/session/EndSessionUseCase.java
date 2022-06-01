package com.sicredi.votacao.internal.interactors.session;

import com.sicredi.votacao.internal.interactors.kafka.SendMessageUseCase;
import org.springframework.stereotype.Service;

@Service
public class EndSessionUseCase {

    private final GetFinishedSessionsUseCase getFinishedSessionsUseCase;
    private final UpdateSessionUseCase updateSessionUseCase;
    private final SendMessageUseCase sendMessageUseCase;

    public EndSessionUseCase(GetFinishedSessionsUseCase getFinishedSessionsUseCase, UpdateSessionUseCase updateSessionUseCase, SendMessageUseCase sendMessageUseCase) {
        this.getFinishedSessionsUseCase = getFinishedSessionsUseCase;
        this.updateSessionUseCase = updateSessionUseCase;
        this.sendMessageUseCase = sendMessageUseCase;
    }

    public void execute() {

        this.getFinishedSessionsUseCase.execute()
                .stream().map(s -> this.updateSessionUseCase.execute(s.setFinished(Boolean.TRUE)))
                .forEach(s -> this.sendMessageUseCase.execute(s));

        return;
    }
}
