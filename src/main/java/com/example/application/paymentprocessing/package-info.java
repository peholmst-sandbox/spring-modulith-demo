@ApplicationModule(
        allowedDependencies = {
                "sharedkernel::*"
        }
)
@NullMarked
package com.example.application.paymentprocessing;

import org.jspecify.annotations.NullMarked;
import org.springframework.modulith.ApplicationModule;