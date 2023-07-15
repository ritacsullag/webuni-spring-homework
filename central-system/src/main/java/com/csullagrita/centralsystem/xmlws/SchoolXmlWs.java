package com.csullagrita.centralsystem.xmlws;

import jakarta.jws.WebService;

@WebService
public interface SchoolXmlWs {

    public int getNumberOfRemainingSemesters(long centralId);
}
