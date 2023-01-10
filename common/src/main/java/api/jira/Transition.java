package api.jira;

import lombok.Builder;

@Builder
public class Transition {

    private String id;
    private String name;
    private Boolean hasScreen;
    private Boolean isGlobal;
    private Boolean isInitial;
    private Boolean isConditional;
    private Boolean isLooped;
    private To to;
}
