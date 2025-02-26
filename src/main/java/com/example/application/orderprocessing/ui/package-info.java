@ApplicationModule(
        allowedDependencies = {
                "orderprocessing",
                "sharedkernel::*"
        }
)
package com.example.application.orderprocessing.ui;

import org.springframework.modulith.ApplicationModule;