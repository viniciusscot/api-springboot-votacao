package com.sicredi.votacao.bootstrap.utils;

import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;

@Component
public class DateUtils {

    public Date getDate() {
        Calendar c = Calendar.getInstance();

        return c.getTime();
    }

    public Date addMinutesToDate(Date date, BigInteger minutes) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        c.add(Calendar.MINUTE, minutes.intValue());

        return c.getTime();
    }

}
