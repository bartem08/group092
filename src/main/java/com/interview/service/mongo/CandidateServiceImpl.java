package com.interview.service.mongo;

import com.interview.model.Candidate;
import com.interview.model.Interview;
import com.interview.repository.CandidateRepository;
import com.interview.repository.InterviewRepository;
import com.interview.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CandidateServiceImpl implements CandidateService{
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private InterviewRepository interviewRepository;

    @Override
    public Candidate createCandidate(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    @Override
    public Candidate readCandidate(String id) {
        return candidateRepository.findOne(id);
    }

    @Override
    public List<Candidate> readAllCandidates() {
        return candidateRepository.findAll();
    }

    @Override
    public Candidate updateCandidate(Candidate candidate) {
        Candidate dbCandidate = candidateRepository.findOne(candidate.getId());

        if (dbCandidate != null){
            return candidateRepository.save(candidate);
        }

        return null;
    }

    @Override
    public boolean deleteCandidate(String id) {
        Candidate candidate = candidateRepository.findOne(id);

        if (candidate != null){
            candidateRepository.delete(candidate);

            return true;
        }

        return false;
    }

    @Override
    public void deleteAllCandidates() {
        candidateRepository.deleteAll();
    }

    @Override
    public boolean addInterviewToCandidate(String idCandidate, String idInterview) {
        Interview interview = interviewRepository.findOne(idInterview);

        if(interview != null){
            Candidate candidate = candidateRepository.findOne(idCandidate);

            if(candidate != null){
                candidate.getInterviewList().add(interview);

                candidateRepository.save(candidate);

                return true;
            }
        }

        return false;
    }

    @Override
    public boolean removeInterviewFromCandidate(String idCandidate, String idInterview) {
        Interview interview = interviewRepository.findOne(idInterview);

        if(interview != null){
            Candidate candidate = candidateRepository.findOne(idCandidate);

            if(candidate != null){
                candidate.getInterviewList().remove(interview);

                candidateRepository.save(candidate);

                return true;
            }
        }

        return false;
    }

    @Override
    public Interview getInterviewOfCandidate(String idCandidate, String idInterview) {
        Interview interview = interviewRepository.findOne(idInterview);

        if(interview != null){
            Candidate candidate = candidateRepository.findOne(idCandidate);

            if(candidate != null){
                for (Interview dbInterview : candidate.getInterviewList()){
                    if(interview.equals(dbInterview)){
                        return interview;
                    }
                }
            }
        }

        return null;
    }

    @Override
    public List<Interview> getInterviewListOfCandidate(String id) {
        return candidateRepository.findOne(id).getInterviewList();
    }
}
