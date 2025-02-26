@ApplicationModule(
        id = "invoice-processing",
        displayName = "Invoice Processing",
        allowedDependencies = {
                "shared-kernel::domain",
                "shared-kernel::component",
                "order-processing::service",
                "payment-processing::service"
        })
@NullMarked
package com.example.application.invoiceprocessing;

import org.jspecify.annotations.NullMarked;
import org.springframework.modulith.ApplicationModule;