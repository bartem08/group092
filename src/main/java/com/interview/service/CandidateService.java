package com.interview.service;

import com.interview.model.Candidate;
import com.interview.model.Interview;

import java.util.List;

public interface CandidateService {

    Candidate createCandidate(Candidate candidate);

    Candidate readCandidate(String id);

    List<Candidate> readAllCandidates();

    Candidate updateCandidate(Candidate candidate);

    boolean deleteCandidate(String id);

    void deleteAllCandidates();

    boolean addInterviewToCandidate(String idCandidate, String idInterview);

    boolean removeInterviewFromCandidate(String idCandidate, String idInterview);

    Interview getInterviewOfCandidate(String idCandidate, String idInterview);

    List<Interview> getInterviewListOfCandidate(String id);

}
