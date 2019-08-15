package org.fanlychie.testing.sample.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("test")
public class TestEnvironmentService implements EnvironmentService {

    @Override
    public String getEnvironment() {
        return "TEST";
    }

}
