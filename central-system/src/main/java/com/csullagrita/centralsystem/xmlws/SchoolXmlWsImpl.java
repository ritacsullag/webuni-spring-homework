package com.csullagrita.centralsystem.xmlws;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class SchoolXmlWsImpl implements SchoolXmlWs {

    private final Random random = new Random();
    Logger logger = LoggerFactory.getLogger(SchoolXmlWsImpl.class);

    @Override
    public int getNumberOfRemainingSemesters(long centralId) {
        logger.info(String.format("Get number of remaining semester for %s", centralId));
        return random.nextInt(0, 10);
    }
}
