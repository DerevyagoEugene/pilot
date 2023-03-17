package model;

import lombok.Builder;

@Builder
public class RegistrationDetails {

    private String cif;

    private String source;

    @Override
    public String toString() {
        return String.format("RegistrationDetails{cif='%s', source='%s'}", cif, source);
    }
}
