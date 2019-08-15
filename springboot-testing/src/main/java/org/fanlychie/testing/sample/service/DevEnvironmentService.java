package org.fanlychie.testing.sample.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"dev", "default"})
public class DevEnvironmentService implements EnvironmentService {

    @Override
    public String getEnvironment() {
        return "DEVELOP";
    }

}
