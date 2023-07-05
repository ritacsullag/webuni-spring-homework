package com.csullagrita.school.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.RevisionType;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryData<T> {
    private T data;
    private RevisionType revisionType;
    private int revision;
    private Date date;
}
