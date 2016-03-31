package com.interview.model.dto;

import com.interview.model.Group;
import com.interview.model.Interviewer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NSS on 31.03.2016.
 */
public class InterviewerDTO implements Serializable {

    private String id;

    private String name;

    private String login;

    private List<String> groupIdList = new ArrayList();

    public InterviewerDTO(Interviewer interviewer) {
        id = interviewer.getId();
        name = interviewer.getFirstName();
        login = interviewer.getLogin();
        for(Group group : interviewer.getGroups()) {
            groupIdList.add(group.getId());
        }
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    public List<String> getGroupIdList() {
        return groupIdList;
    }
    public void setGroupIdList(List<String> groupIdList) {
        this.groupIdList = groupIdList;
    }
}
