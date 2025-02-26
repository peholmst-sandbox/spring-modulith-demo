@ApplicationModule(
        id = "order-processing",
        displayName = "Order Processing",
        allowedDependencies = {
                "shared-kernel::component",
                "shared-kernel::domain"
        }
)
@NullMarked
package com.example.application.orderprocessing;

import org.jspecify.annotations.NullMarked;
import org.springframework.modulith.ApplicationModule;