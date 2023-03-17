package api.jira;

import lombok.Builder;

@Builder
public class To {

    private String id;
    private String name;
    private StatusCategory statusCategory;
}
