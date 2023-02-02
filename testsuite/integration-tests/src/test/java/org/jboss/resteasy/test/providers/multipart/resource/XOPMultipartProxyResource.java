package org.jboss.resteasy.test.providers.multipart.resource;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/ITest")
public class XOPMultipartProxyResource implements XOPMultipartProxy {

   @Override
   public XOPMultipartProxyGetFileResponse getFile(String request) throws Exception {
      return getResponse(request);
   }

   @Override
   public Response putFile(XOPMultipartProxyPutFileRequest putFileRequest) throws IOException {
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      putFileRequest.getContent().writeTo(out);
      return Response.status(200).entity(new String(out.toByteArray())).build();
   }

   private XOPMultipartProxyGetFileResponse getResponse(String content) throws Exception {
      XOPMultipartProxyGetFileResponse response = new XOPMultipartProxyGetFileResponse();
      File out = Files.createTempFile("tmp", ".txt").toFile();
      out.deleteOnExit();
      try (FileWriter writer = new FileWriter(out)) {
         writer.write(content);
         DataSource fds = new FileDataSource(out);
         DataHandler handler = new DataHandler(fds);
         response.setData(handler);
      }
      return response;
   }
}
