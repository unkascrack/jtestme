package es.jtestme.collector.service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamImplicitCollection;

import es.jtestme.collector.domain.Application;
import es.jtestme.collector.domain.ApplicationState;
import es.jtestme.collector.domain.Owner;
import es.jtestme.collector.domain.Verificator;
import es.jtestme.collector.domain.reference.StateType;
import es.jtestme.domain.VerificatorResult;

@Service
public class CollectorService {

    transient static Logger logger = LoggerFactory.getLogger(CollectorService.class);

    @Autowired
    private transient MailService mailService;

    /**
     * 
     */
    @Scheduled(cron = "10 * * * * ?")
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void verifyAllApplications() {
        List<Application> applications = Application.findAllApplicationsByStartWatching();
        // FIXME: realizar la ejecución en paralelo (concurrent)
        for (Application application : applications) {
            verifiyApplication(application);
        }
    }

    /**
     * @param application
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void verifiyApplication(Application application) {
        ApplicationState lastState = ApplicationState.findLastApplicationState(application.getId());
        if (isVerificationNeed(application, lastState)) {
            executeVerificators(application, lastState);
        }
    }

    private void executeVerificators(Application application, ApplicationState actualState) {
        ApplicationState lastState = getApplicationState(application);
        if (actualState == null || actualState.getStateType() != lastState.getStateType()) {
            lastState.persist();
            sendNotifications(application, lastState);
        }
    }

    private ApplicationState getApplicationState(Application application) {
        HttpResponse response = getHttpResponse(application.getUrl());
        ApplicationState state = new ApplicationState();
        state.setApplication(application);
        state.setCode(response.getCode());
        state.setMessage(response.getMessage());
        state.setContent(response.getContent());
        state.setStateDate(new Date());
        state.setStateType(StateType.getStateTypeByHttpResponseCode(response.getCode()));
        if (state.getStateType().isOk()) {
            Set<Verificator> verificators = getVerificators(state);
            if (CollectionUtils.isNotEmpty(verificators)) {
                state.setVerificators(verificators);
                if (CollectionUtils.exists(verificators, new Predicate() {
                    @Override
                    public boolean evaluate(Object verificator) {
                        return !((Verificator) verificator).isSuccess();
                    }
                })) {
                    state.setStateType(StateType.ERROR);
                }
            }
        }
        return state;
    }

    private Set<Verificator> getVerificators(ApplicationState state) {
        VerificatorList verificatorList = processVerificators(state.getContent());

        Set<Verificator> verificators = new HashSet<Verificator>();
        for (VerificatorResult result : verificatorList.getVerificators()) {
            Verificator verificator = new Verificator();
            verificator.setApplicationState(state);
            verificator.setName(result.getName());
            verificator.setType(result.getType());
            verificator.setSuccess(result.isSuccess());
            verificator.setMessage(result.getMessage());
            verificator.setCause(result.getCauseString());
            verificators.add(verificator);
        }
        return verificators;
    }

    private VerificatorList processVerificators(String xml) {
        VerificatorList verificatorList = new VerificatorList();
        try {
            XStream xstream = new XStream();
            xstream.processAnnotations(VerificatorList.class);
            verificatorList = (VerificatorList) xstream.fromXML(xml);
        } catch (Throwable t) {
            VerificatorResult verificator = new VerificatorResult();
            verificator.setName("no-verificator");
            verificator.setType("no-verificator");
            verificator.setSuccess(false);
            verificator.setMessage(t.getMessage());
            verificator.setCause(t);
            verificatorList.addVerificator(verificator);
        }
        return verificatorList;
    }

    private transient static final Integer TIMEOUT = 1000;

    private HttpResponse getHttpResponse(String url) {
        HttpResponse response = new HttpResponse();
        HttpURLConnection connection = null;
        Scanner scanner = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(TIMEOUT);
            response.setCode(connection.getResponseCode());
            response.setMessage(connection.getResponseMessage());
            if (connection.getResponseCode() != 200) {
                scanner = new Scanner(connection.getErrorStream());
            } else {
                scanner = new Scanner(connection.getInputStream());
            }
            scanner.useDelimiter("\\Z");
            response.setContent(scanner.next());
        } catch (final Throwable e) {
            response.setCode(0);
            response.setMessage(e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (scanner != null) {
                scanner.close();
            }
        }
        return response;
    }

    private boolean isVerificationNeed(Application application, ApplicationState lastState) {
        // TODO: add new property in Application to set the period in minus to verify, by default 1 minute
        return (lastState == null && application.isStartWatching());
    }

    private void sendNotifications(Application application, ApplicationState lastState) {
        if (application.isMailingOk() && lastState.getStateType().isOk() || application.isMailingError()
                && lastState.getStateType().isError() || application.isMailingNoConnect()
                && lastState.getStateType().isNoConnect()) {
            List<String> mailTo = new ArrayList<String>();
            for (Owner owner : application.getOwners()) {
                mailTo.add(owner.getEmail());
            }
            if (!mailTo.isEmpty()) {
                String message = StringUtils.defaultIfBlank(lastState.getContent(), lastState.getMessage());
                mailService.sendMessage(mailTo.toArray(new String[mailTo.size()]), lastState.getStateType().toString(),
                        message);
            }
        }
    }

    private class HttpResponse {

        private int code = 0;
        private String message;
        private String content;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    @XStreamAlias("verificators")
    private class VerificatorList {

        @XStreamImplicit
        private List<VerificatorResult> verificators = new ArrayList<VerificatorResult>();

        public List<VerificatorResult> getVerificators() {
            return verificators;
        }

        public void addVerificator(VerificatorResult verificator) {
            this.verificators.add(verificator);
        }
    }
}
