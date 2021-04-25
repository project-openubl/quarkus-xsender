package io.github.project.openubl.quarkus.xsender.deployment;

import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.nativeimage.NativeImageProxyDefinitionBuildItem;
import io.quarkus.deployment.builditem.nativeimage.ReflectiveClassBuildItem;
import io.quarkus.deployment.builditem.nativeimage.RuntimeInitializedClassBuildItem;

import java.util.Arrays;
import java.util.List;

class QuarkusXsenderProcessor {

    private static final String FEATURE = "quarkus-xsender";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    void httpProxies(BuildProducer<NativeImageProxyDefinitionBuildItem> proxies) {
        proxies.produce(new NativeImageProxyDefinitionBuildItem(
                "service.sunat.gob.pe.billservice.BillService",
                "javax.xml.ws.BindingProvider",
                "java.io.Closeable",
                "org.apache.cxf.endpoint.Client"
        ));
    }

    @BuildStep
    void reflective(BuildProducer<ReflectiveClassBuildItem> reflectiveClass) {
        reflectiveClass.produce(
                new ReflectiveClassBuildItem(
                        true,
                        true,
                        "service.sunat.gob.pe.billservice.BillService",
                        "service.sunat.gob.pe.billservice.GetStatus",
                        "service.sunat.gob.pe.billservice.GetStatusResponse",
                        "service.sunat.gob.pe.billservice.ObjectFactory",
                        "service.sunat.gob.pe.billservice.SendBill",
                        "service.sunat.gob.pe.billservice.SendBillResponse",
                        "service.sunat.gob.pe.billservice.SendPack",
                        "service.sunat.gob.pe.billservice.SendPackResponse",
                        "service.sunat.gob.pe.billservice.SendSummary",
                        "service.sunat.gob.pe.billservice.SendSummaryResponse",
                        "service.sunat.gob.pe.billservice.StatusResponse"
                )
        );

        reflectiveClass.produce(
                new ReflectiveClassBuildItem(
                        false,
                        false,
                        "javax.xml.ws.BindingProvider",
                        "java.io.Closeable",
                        "org.apache.cxf.endpoint.Client"
                )
        );

        reflectiveClass.produce(
                new ReflectiveClassBuildItem(
                        true,
                        false,
                        "javax.xml.bind.annotation.XmlType",
                        "javax.xml.bind.annotation.XmlElementDecl"
                )
        );
    }

    @BuildStep
    List<RuntimeInitializedClassBuildItem> runtimeInitializedClasses() {
        return Arrays.asList(
                new RuntimeInitializedClassBuildItem("org.apache.wss4j.common.saml.builder.SAML1ComponentBuilder"),
                new RuntimeInitializedClassBuildItem("org.apache.wss4j.common.saml.builder.SAML2ComponentBuilder"),
                new RuntimeInitializedClassBuildItem("org.ehcache.core.osgi.OsgiServiceLoader")
        );
    }
}
