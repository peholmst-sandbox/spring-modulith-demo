@ApplicationModule(
        allowedDependencies = {
                "paymentprocessing",
                "sharedkernel::*"
        }
)
package com.example.application.paymentprocessing.ui;

import org.springframework.modulith.ApplicationModule;