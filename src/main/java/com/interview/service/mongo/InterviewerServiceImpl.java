package com.interview.service.mongo;

import com.interview.model.Group;
import com.interview.model.Interview;
import com.interview.model.Interviewer;
import com.interview.repository.GroupRepository;
import com.interview.repository.InterviewerRepository;
import com.interview.service.InterviewerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by NSS on 27.03.2016.
 */
@Service
public class InterviewerServiceImpl  implements InterviewerService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private InterviewerRepository interviewerRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public Interviewer readInterviewer(String id) {
        Interviewer interviewer = interviewerRepository.findOne(id);
        if (interviewer != null) {
            LOG.info(String.format("Interviewer '%s' received from database by id '%s'", interviewer, id));
            return interviewer;
        }
        LOG.info(String.format("Interviewer with id '%s' doesn't exists in database. Nothing to be received", id));
        return null;
    }

    @Override
    public Interviewer findInterviewer(String login) {
        Interviewer interviewer = interviewerRepository.findByLogin(login);
        if (interviewer != null) {
            LOG.info(String.format("Interviewer '%s' received from database by login '%s'", interviewer, login));
            return interviewer;
        }
        LOG.info(String.format("Interviewer with login '%s' doesn't exists in database. Nothing to be received", login));
        return null;
    }

    @Override
    public List<Interviewer> readAllInterviewers() {
        List<Interviewer> interviewers = interviewerRepository.findAll();
        LOG.info(String.format("List of all interviewers: %s", interviewers));
        return interviewers;
    }

    @Override
    public Interviewer createInterviewer(Interviewer interviewer) {

        Interviewer receivedInterviewer = interviewerRepository.save(interviewer);
        LOG.info(String.format("Interviewer '%s' has been saved with id '%s'",
                receivedInterviewer, receivedInterviewer.getId()));
        return receivedInterviewer;
    }

    @Override
    public Interviewer updateInterviewer(Interviewer interviewer) {
        if (! interviewerRepository.exists(interviewer.getId())) {
            LOG.info(String.format("Interviewer doesn't exists in database. Nothing to be updated"));
            return null;
        }
        Interviewer receivedInterviewer = interviewerRepository.save(interviewer);
        LOG.info(String.format("Interviewer '%s' has been updated in database", receivedInterviewer));
        return receivedInterviewer;
    }

    @Override
    public boolean deleteInterviewer(String id) {
        if (interviewerRepository.exists(id)) {
            interviewerRepository.delete(id);
            LOG.info(String.format("Interviewer with id = '%s' has been deleted from database ", id));
            return true;
        }
        LOG.info(String.format("Interviewer with id '%s' doesn't exists in database. Nothing to be removed", id));
        return false;
    }

    @Override
    public Interviewer addGroupToInterviewer(Interviewer interviewer, Group group) {
        if ( !interviewer.getGroups().contains(group) ) {
            interviewer.addGroup(group);
            LOG.info(String.format("Interviewer has been added group with id = '%s'", group.getId()));
            return interviewerRepository.save(interviewer);
        } else {
            LOG.error(String.format("Interviewer contains group with id      = '%s'", group.getId()));
            return interviewer;
        }
    }

    @Override
    public boolean deleteGroupFromInterviewer(Interviewer interviewer, Group group) {
        if (interviewer.getGroups().contains(group)) {
            interviewer.removeGroup(group);
            interviewerRepository.save(interviewer);
            LOG.info(String.format("Group with id = '%s' has been deleted from interviewer ", group.getId()));
            return true;
        } else {
            LOG.error(String.format("Interviewer doesn't contain group with id = '%s'", group.getId()));
            return false;
        }
    }
}
