package com.sicredi.votacao.adapter.transportlayers.cronjob;

import com.sicredi.votacao.internal.interactors.session.EndSessionUseCase;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ResultOfVotationSchedulle {

    private final EndSessionUseCase endSessionUseCase;

    public ResultOfVotationSchedulle(EndSessionUseCase endSessionUseCase) {
        this.endSessionUseCase = endSessionUseCase;
    }

    @Scheduled(cron = "0 * * * * ?")
    public void finishedVotation() {

        this.endSessionUseCase.execute();

    }

}
