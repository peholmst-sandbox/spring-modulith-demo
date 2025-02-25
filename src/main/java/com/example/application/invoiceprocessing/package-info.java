@ApplicationModule(allowedDependencies = {
        "orderprocessing",
        "paymentprocessing"
})
@NullMarked
package com.example.application.invoiceprocessing;

import org.jspecify.annotations.NullMarked;
import org.springframework.modulith.ApplicationModule;