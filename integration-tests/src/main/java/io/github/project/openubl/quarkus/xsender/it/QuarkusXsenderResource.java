/*
* Licensed to the Apache Software Foundation (ASF) under one or more
* contributor license agreements.  See the NOTICE file distributed with
* this work for additional information regarding copyright ownership.
* The ASF licenses this file to You under the Apache License, Version 2.0
* (the "License"); you may not use this file except in compliance with
* the License.  You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package io.github.project.openubl.quarkus.xsender.it;

import io.github.project.openubl.xsender.XSender;
import io.github.project.openubl.xsender.XSenderFileResponse;
import io.github.project.openubl.xsender.company.CompanyCredentials;
import io.github.project.openubl.xsender.company.CompanyCredentialsBuilder;
import io.github.project.openubl.xsender.company.CompanyURLs;
import io.github.project.openubl.xsender.company.CompanyURLsBuilder;
import io.github.project.openubl.xsender.discovery.FileAnalyzer;
import io.github.project.openubl.xsender.discovery.XMLFileAnalyzer;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.io.InputStream;

@Path("/quarkus-xsender")
@ApplicationScoped
public class QuarkusXsenderResource {

    @GET
    public String hello() throws Exception {
        // On for company
        CompanyURLs companyURLs = CompanyURLsBuilder.aCompanyURLs()
                .withInvoice("https://e-beta.sunat.gob.pe/ol-ti-itcpfegem-beta/billService")
                .withDespatch("https://e-beta.sunat.gob.pe/ol-ti-itemision-otroscpe-gem-beta/billService")
                .withPerceptionRetention("https://e-beta.sunat.gob.pe/ol-ti-itemision-guia-gem-beta/billService")
                .build();

        CompanyCredentials credentials = CompanyCredentialsBuilder.aCompanyCredentials()
                .withUsername("12345678959MODDATOS")
                .withPassword("MODDATOS")
                .build();

        // Using the facade
        InputStream is = QuarkusXsenderResource.class.getClassLoader().getResourceAsStream("META-INF/resources/invoice.xml");
        FileAnalyzer fileAnalyzer = new XMLFileAnalyzer(is, companyURLs);

        XSenderFileResponse fileResponse = XSender.getInstance()
                .sendXmlFile(fileAnalyzer.getZipFile(), fileAnalyzer.getFileDeliveryTarget(), credentials);

        return fileResponse.getCdrReader().getCdrStatus().get().toString();
    }

}
