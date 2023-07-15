package com.csullagrita.school.service;

import com.csullagrita.centralsystem.xmlws.SchoolXmlWs;
import com.csullagrita.centralsystem.xmlws.SchoolXmlWsImplService;
import com.csullagrita.school.exception.SomethingWentWrongException;
import com.csullagrita.school.aspect.RetryHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CenterSystemService {

    private final Random random = new Random();
    Logger logger = LoggerFactory.getLogger(CenterSystemService.class);

    @RetryHandler
    public int getUsedFreeSemester(long centralId) throws SomethingWentWrongException {
        if (random.nextBoolean()) {
            logger.info("updateUsedSemestersForStudents was called");
            SchoolXmlWs schoolXmlWsImplPort = new SchoolXmlWsImplService().getSchoolXmlWsImplPort();
            int numberOfRemainingSemesters = schoolXmlWsImplPort.getNumberOfRemainingSemesters(centralId);
            //this endpoint is for used semester, the other is for remaining, from this reason:
            return 10 - numberOfRemainingSemesters;
        } else {
            throw new SomethingWentWrongException("Something went wrong in getUsedFreeSemester call");
        }
    }
}
