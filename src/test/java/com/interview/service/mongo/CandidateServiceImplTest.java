package com.interview.service.mongo;

import com.interview.config.MvcConfigurer;
import com.interview.model.Candidate;
import com.interview.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.GregorianCalendar;

import static org.testng.Assert.*;

@SpringApplicationConfiguration(MvcConfigurer.class)
@WebIntegrationTest("server.port:0")
@DirtiesContext
public class CandidateServiceImplTest extends AbstractTestNGSpringContextTests{
    @Autowired
    private CandidateService candidateService;
    private Candidate candidate;

    @Test
    public void givenCandidateToAddThenWhenCallingReadCandidateCandidateAddedExpected() {
        candidate = candidateService.createCandidate(new Candidate("Belaya", "Ann", new GregorianCalendar(2016, 7, 6)));

        assertNotNull(candidate);
        assertEquals(candidate.getLastName(), "Belaya");
        assertEquals(candidate.getFirstName(), "Ann");
        assertEquals(candidate.getDate(), new GregorianCalendar(2016, 7, 6));

        candidateService.deleteCandidate(candidate.getId());
    }

    @Test
    public void givenCandidateInDatabaseThenWhenCallingReadCandidateReturnedExpected() {
        candidate = candidateService.createCandidate(new Candidate("Belaya", "Ann", new GregorianCalendar(2016, 7, 6)));

        assertEquals(candidateService.readCandidate(candidate.getId()), candidate);

        candidateService.deleteCandidate(candidate.getId());
    }

    @Test
    public void givenCandidateToUpdateThenWhenCallingGetCandidateUpdatedExpected(){
        candidate = candidateService.createCandidate(new Candidate("Belaya", "Ann", new GregorianCalendar(2016, 7, 6)));

        Candidate candidateToUpdate = new Candidate("Bila","Hanna",new GregorianCalendar(2016,6,7));
        candidateToUpdate.setId(candidate.getId());

        candidate = candidateService.updateCandidate(candidateToUpdate);

        assertEquals(candidate.getLastName(), "Bila");
        assertEquals(candidate.getFirstName(), "Hanna");
        assertEquals(candidate.getDate(), new GregorianCalendar(2016, 6, 7));

        candidateService.deleteCandidate(candidate.getId());
    }

    @Test
    public void givenCandidateToDeleteThenWhenCallingGetCandidateRemovedExpected(){
        candidate = candidateService.createCandidate(new Candidate("Belaya", "Ann", new GregorianCalendar(2016, 7, 6)));
        assertNotNull(candidateService.readCandidate(candidate.getId()));

        candidateService.deleteCandidate(candidate.getId());
        assertNull(candidateService.readCandidate(candidate.getId()));
    }
}
