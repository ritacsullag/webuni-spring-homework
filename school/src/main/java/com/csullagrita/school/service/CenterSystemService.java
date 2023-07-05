package com.csullagrita.school.service;

import com.csullagrita.school.exception.SomethingWentWrongException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CenterSystemService {

    private final Random random = new Random();
    Logger logger = LoggerFactory.getLogger(CenterSystemService.class);

    public int getUsedFreeSemester(long centralId) throws SomethingWentWrongException {
        if (random.nextBoolean()) {
            logger.info("updateUsedSemestersForStudents was called");
            return random.nextInt(1, 12);
        } else {
            throw new SomethingWentWrongException("Something went wrong in getUsedFreeSemester call");
        }
    }
}
