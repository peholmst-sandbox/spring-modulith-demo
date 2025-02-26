package com.example.application;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

class ModuleTest {

    @Test
    public void verifyModuleStructure() {
        var modules = ApplicationModules.of(Application.class);
        System.out.println(modules);
        modules.verify();
    }
}
