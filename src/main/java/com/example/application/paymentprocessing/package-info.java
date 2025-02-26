@ApplicationModule(
        id = "payment-processing",
        displayName = "Payment Processing",
        allowedDependencies = {
                "shared-kernel::component",
                "shared-kernel::domain"
        }
)
@NullMarked
package com.example.application.paymentprocessing;

import org.jspecify.annotations.NullMarked;
import org.springframework.modulith.ApplicationModule;