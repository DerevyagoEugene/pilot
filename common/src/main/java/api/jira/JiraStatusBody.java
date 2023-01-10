package api.jira;

import lombok.Builder;

@Builder
public class JiraStatusBody {

    private Transition transition;
}
