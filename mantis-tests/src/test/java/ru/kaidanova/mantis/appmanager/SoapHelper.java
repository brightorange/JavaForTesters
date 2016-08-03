package ru.kaidanova.mantis.appmanager;

import biz.futureware.mantis.rpc.soap.client.*;
import ru.kaidanova.mantis.model.Issue;
import ru.kaidanova.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class SoapHelper {

    private final ApplicationManager app;

    public SoapHelper(ApplicationManager app) throws MalformedURLException, RemoteException, ServiceException {
        this.app = app;
    }

    public Set<Project> getProjects() throws MalformedURLException, ServiceException, RemoteException {
        MantisConnectPortType mc = getMantisConnect();
        ProjectData[] projects = mc.mc_projects_get_user_accessible("administrator", "root");
        return Arrays.asList(projects).stream()
                .map((p) -> new Project().withId(p.getId().intValue()).withName(p.getName()))
                .collect(Collectors.toSet());
    }

    public Issue getIssueById(int id) throws RemoteException, MalformedURLException, ServiceException {
        MantisConnectPortType mc = getMantisConnect();
        IssueData IssueData = mc.mc_issue_get("administrator", "root", BigInteger.valueOf(id));
        return new Issue().withStatus(IssueData.getStatus().getName())
                .withId(IssueData.getId().intValue()).withSummary(IssueData.getSummary())
                .withDescription(IssueData.getDescription()).withId(IssueData.getId().intValue())
                .withProject(new Project().withId(IssueData.getProject().getId().intValue()).withName(IssueData.getProject().getName()));
    }

    private MantisConnectPortType getMantisConnect() throws ServiceException, MalformedURLException {
        return new MantisConnectLocator().getMantisConnectPort(new URL(app.getProperty("mantis.connectPort")));
    }

    public Issue addIssue(Issue issue) throws MalformedURLException, ServiceException, RemoteException {
        MantisConnectPortType mc = getMantisConnect();
        IssueData issueData = new IssueData();
        String[] categories = mc.mc_project_get_categories(app.getProperty("mantis.adminLogin"), app.getProperty("mantis.adminPassword"),
                BigInteger.valueOf(issue.getProject().getId()));
        issueData.setCategory(categories[0]);
        issueData.setSummary(issue.getSummary());
        issueData.setDescription(issue.getDescription());
        issueData.setProject(new ObjectRef(BigInteger.valueOf(issue.getProject().getId()), issue.getProject().getName()));
        BigInteger issueId = mc.mc_issue_add(app.getProperty("mantis.adminLogin"), app.getProperty("mantis.adminPassword"), issueData);
        IssueData newIssueData = mc.mc_issue_get(app.getProperty("mantis.adminLogin"), app.getProperty("mantis.adminPassword"), issueId);
        return new Issue().withId(newIssueData.getId().intValue()).withSummary(newIssueData.getSummary())
        .withDescription(newIssueData.getDescription()).withId(newIssueData.getId().intValue())
        .withProject(new Project().withId(newIssueData.getProject().getId().intValue()).withName(newIssueData.getProject().getName()));
    }
}
