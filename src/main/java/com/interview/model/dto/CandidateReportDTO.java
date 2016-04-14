package com.interview.model.dto;

import com.interview.model.Candidate;
import com.interview.model.Interview;

/**
 * Created by Artem Pozdeev on 14.04.2016.
 */
public class CandidateReportDTO  implements Comparable<CandidateReportDTO> {

    private Float result = 0f;
    private String fullName = "";
    private String id = "";

    public CandidateReportDTO(Candidate candidate, Interview interview) {
        if (candidate != null){
            this.id = candidate.getId();
            this.fullName = candidate.getLastName() + " " + candidate.getFirstName();
        }
        if (interview != null) {
            this.result = interview.getResult();
        }
    }
    public Float getResult() {
        return result;
    }

    public void setResult(Float result) {
        this.result = result;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int compareTo(CandidateReportDTO o) {
        return o.getResult().compareTo(result);
    }
}
