package com.fhery021.joke.monolith;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.fhery021.joke.monolith");

        noClasses()
            .that()
            .resideInAnyPackage("com.fhery021.joke.monolith.service..")
            .or()
            .resideInAnyPackage("com.fhery021.joke.monolith.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.fhery021.joke.monolith.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
