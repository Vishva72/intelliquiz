package org.bits.IntelliQuiz.dto;

import java.util.List;

public class AddParticipantsRequest {
    private List<String> emails;

    public AddParticipantsRequest() {}

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }
}
