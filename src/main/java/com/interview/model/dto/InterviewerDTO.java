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

    private String fullName;

    private String login;

    private List<GroupDTO> groupDTOList = new ArrayList();

    public InterviewerDTO(Interviewer interviewer) {
        id = interviewer.getId();
        fullName = interviewer.getLastName() + " " + interviewer.getFirstName();
        login = interviewer.getLogin();
        for(Group group : interviewer.getGroups()) {
            groupDTOList.add(new GroupDTO(group));
        }
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    public List<GroupDTO> getGroupDTOList() {
        return groupDTOList;
    }
    public void setGroupDTOList(List<GroupDTO> groupDTOList) {
        this.groupDTOList = groupDTOList;
    }
}
