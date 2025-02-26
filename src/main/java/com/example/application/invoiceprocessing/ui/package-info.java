@ApplicationModule(
        allowedDependencies = {
                "invoiceprocessing",
                "sharedkernel::*"
        }
)
package com.example.application.invoiceprocessing.ui;

import org.springframework.modulith.ApplicationModule;