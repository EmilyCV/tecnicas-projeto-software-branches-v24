package com.pece.agencia.architecture;

import com.pece.agencia.api.bootstrap.ApiApplication;
import com.pece.agencia.api.common.archunit.MayParseExceptionMessage;
import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaMethodCall;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

import static java.lang.String.format;
import static org.jmolecules.archunit.JMoleculesArchitectureRules.StereotypeLookup.defaultLookup;
import static org.jmolecules.archunit.JMoleculesArchitectureRules.ensureOnionSimple;

class ArchitectureTest {
    private static class ExceptionGetMessageCalledByNonSuppressedPredicate extends DescribedPredicate<JavaMethodCall> {
        public ExceptionGetMessageCalledByNonSuppressedPredicate() {
            super("call to Exception.getMessage by methods not annotated with @SuppressWarnings");
        }

        private boolean isTargetExceptionGetMessageCall(JavaMethodCall call) {
            boolean isException = call.getTarget().getOwner().isAssignableTo(Throwable.class);
            String targetName = call.getTarget().getName();
            return isException && "getMessage".equals(targetName);
        }

        private boolean callerMayNotParseExceptionMessage(JavaMethodCall call) {
            return !call.getOrigin().isAnnotatedWith(MayParseExceptionMessage.class);
        }
        @Override
        public boolean test(JavaMethodCall call) {
            return isTargetExceptionGetMessageCall(call) && callerMayNotParseExceptionMessage(call);
        }
    }

    @Nested
    @DisplayName("ACL upstream tests")
    class AclUpstreamRulesTest {
        private void checkAclUpstreamRule(String acl) {
            JavaClasses importedClasses = importMainClasses();
            ArchRuleDefinition.noClasses()
                    .that()
                    .resideOutsideOfPackages(
                            format("com.pece.agencia.api.core.infrastructure.adapters.acl.%s..", acl),
                            format("com.pece.agencia.api.%s..", acl)
                    )
                    .should()
                    .dependOnClassesThat()
                    .resideInAnyPackage(format("com.pece.agencia.api.%s..", acl))
                    .because(format("Apenas classes do pacote acl.%s podem acessar o context %s", acl, acl))
                    .check(importedClasses);

        }


        @Test
        @DisplayName("Somente classes da ACL podem acessar o contexto delimitado locacao veiculo")
        void onlyAclVeiculoServiceCanAccessVeiculoPackage() {
            checkAclUpstreamRule("veiculo");
        }

        @Test
        @DisplayName("Somente classes da ACL podem acessar o contexto delimitado hoteleira")
        void onlyAclHotelariaServiceCanAccessHotelariaPackage() {
            checkAclUpstreamRule("hotelaria");
        }

        @Test
        @DisplayName("Somente classes da ACL podem acessar o contexto delimitado translado aereo")
        void onlyAclTransladoAereoServiceCanAccessAereoPackage() {
            checkAclUpstreamRule("aereo");
        }


        @Test
        @DisplayName("Somente classes da ACL podem acessar o contexto delimitado de pagamento")
        void onlyAclPagamentoServiceCanAccessPagamentoPackage() {
            checkAclUpstreamRule("pagamento");
        }
    }


    @DisplayName("Garanta que não há violações de arquitetura modular (acesso via APIs)")
    @Test
    void ensureModuleAccessThruAPIs() {
        ApplicationModules.of(ApiApplication.class)
                .detectViolations()
                .throwIfPresent();
    }

    @Test
    @DisplayName("Somente classes do pacote stripe podem acessar com.stripe")
    void onlyStripeServiceCanAccessStripePackage() {
        JavaClasses importedClasses = importMainClasses();
        ArchRuleDefinition.noClasses()
                .that()
                .resideOutsideOfPackages("com.pece.agencia.api.pagamento.internal.stripe..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage("com.stripe..")
                .because("Somente classes do pacote stripe podem acessar com.stripe e seus subpacotes")
                .check(importedClasses);
    }

    private JavaClasses importMainClasses() {
        // Importa apenas classes do diretório de produção (target/classes)
        return new ClassFileImporter().importPath("target/classes");
    }


    @Nested
    @DisplayName("Garanta que as regras de ports and adapters sejam seguidas")
    class PortsAndAdaptersRulesTest {
        @DisplayName("Adapters nao podem depender uns dos outros")
        @Test
        void adaptersShouldNotDependOnEachOther()     {
            SlicesRuleDefinition.slices().matching("..adapters.(*)..")
                    .should().notDependOnEachOther()
                    .because("Adapters não devem depender uns dos outros")
                    .check(importMainClasses());
        }
    }


    @Nested
    @DisplayName("Garanta que as regras de camadas seguem seguidas")
    class LayeredArchitectureRulesTest {
        @DisplayName("Garantir regras de camadas Onion Architecture")
        @Test
        public void ensureLayerRules() {
            ensureOnionSimple(defaultLookup().withParentPackageTraversal()).check(importMainClasses());
        }
    }

    @Test
    @DisplayName("Não permitir parsing da mensagem de exceção (getMessage)")
    void shouldNotParseExceptionMessage() {
        JavaClasses importedClasses = importMainClasses();

        ArchRuleDefinition.noClasses()
                .should().callMethodWhere(new ExceptionGetMessageCalledByNonSuppressedPredicate())
                .because("Não é permitido fazer parsing da mensagem de exceção (getMessage)")
                .check(importedClasses);
    }
}
