package com.avantica.auth;

import com.microsoft.aad.adal4j.AuthenticationContext;
import com.microsoft.aad.adal4j.AuthenticationResult;

import javax.naming.ServiceUnavailableException;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by max.macalupu on 23/05/2016.
 */
public class EntryPoint {

    private final static String AUTHORITY = "https://login.windows.net/common";
    private final static String CLIENT_ID = "4a63455a-foo-4ac6-baar-0d046cf1c3f7";
    private final static String ID = "max.macalupu@avantica.net";
    private final static String PASSWORD = "Max$1234";

    private final static String RESOURCE_FILES = "https://foobar.sharepoint.com";
    private final static String RESOURCE_GRAPH = "00000003-0000-0000-c000-000000000000";
    private final static String FILES_ENDPOINT = "https://msopentechtest01-my.sharepoint.com/_api/v1.0/me";
    private final static String GRAPH_ENDPOINT = "https://graph.microsoft.com/beta/myOrganization";



//    private static void retrieveGraphServicesFile() throws Exception {
//
//        AuthenticationResult result = getAccessTokenFromUserCredentials(RESOURCE_GRAPH, ID, PASSWORD);
//
//        System.out.println("Access Token - " + result.getAccessToken());
//        System.out.println("Refresh Token - " + result.getRefreshToken());
//
//        JavaDependencyResolver resolver = new JavaDependencyResolver(result.getAccessToken());
//        resolver.getLogger().setEnabled(true);
//        resolver.getLogger().setLogLevel(LogLevel.VERBOSE);
//
//        GraphServiceClient client = new GraphServiceClient(GRAPH_ENDPOINT, resolver);
//        String filename = UUID.randomUUID().toString() + ".txt";
//
//        Item newFile = new Item();
//        newFile.setType("File");
//        newFile.setName(filename);
//
//        String payload = "My Content";
//        Item addedFile = client.getUsers().getById(ID).getFiles().add(newFile).get();
//        client.getUsers().getById(ID).getFiles().getById(addedFile.getId())
//                .asFile().getOperations().uploadContent(payload.getBytes()).get();
//
//        byte[] content = client.getUsers().getById(ID).getFiles().getById(addedFile.getId()).asFile().getOperations().content().get();
//        String retrieved = new String(content, "UTF-8");
//
//        System.out.println();
//        System.out.println(retrieved);
//    }
//
//    private static void retrieveGraphServicesFile() throws Exception {
//
//        AuthenticationResult result = getAccessTokenFromUserCredentials(RESOURCE_GRAPH, ID, PASSWORD);
//
//        System.out.println("Access Token - " + result.getAccessToken());
//        System.out.println("Refresh Token - " + result.getRefreshToken());
//
//        JavaDependencyResolver resolver = new JavaDependencyResolver(result.getAccessToken());
//        resolver.getLogger().setEnabled(true);
//        resolver.getLogger().setLogLevel(LogLevel.VERBOSE);
//
//        GraphServiceClient client = new GraphServiceClient(GRAPH_ENDPOINT, resolver);
//        String filename = UUID.randomUUID().toString() + ".txt";
//
//        Item newFile = new Item();
//        newFile.setType("File");
//        newFile.setName(filename);
//
//        String payload = "My Content";
//        Item addedFile = client.getUsers().getById(ID).getFiles().add(newFile).get();
//        client.getUsers().getById(ID).getFiles().getById(addedFile.getId())
//                .asFile().getOperations().uploadContent(payload.getBytes()).get();
//
//        byte[] content = client.getUsers().getById(ID).getFiles().getById(addedFile.getId()).asFile().getOperations().content().get();
//        String retrieved = new String(content, "UTF-8");
//
//        System.out.println();
//        System.out.println(retrieved);
//    }

    private static AuthenticationResult getAccessTokenFromUserCredentials(String resource,
                                                                          String username, String password) throws Exception {
        AuthenticationContext context;
        AuthenticationResult result = null;
        ExecutorService service = null;
        try {
            service = Executors.newFixedThreadPool(1);
            context = new AuthenticationContext(AUTHORITY, false, service);
            Future<AuthenticationResult> future = context.acquireToken(
                    resource, CLIENT_ID, username, password,
                    null);
            result = future.get();
        } finally {
            service.shutdown();
        }

        if (result == null) {
            throw new ServiceUnavailableException(
                    "authentication result was null");
        }
        return result;
    }
}