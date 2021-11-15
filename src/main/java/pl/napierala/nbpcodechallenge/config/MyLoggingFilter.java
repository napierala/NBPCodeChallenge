package pl.napierala.nbpcodechallenge.config;

import com.google.common.collect.Lists;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class MyLoggingFilter extends AbstractRequestLoggingFilter {

    /**
     * All values here will be matched with the incoming url using the starts with method,
     * if it is true then the request and it's payload will not be logged.
     */
    private static final List<String> LOGGING_URL_STARTS_WITH_BLACKLIST = Lists.newArrayList(
            "/public/h2-console",
            "/v3/api-docs",
            "/swagger-ui",
            "/user/register"
    );

    @Override
    protected boolean shouldLog(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return LOGGING_URL_STARTS_WITH_BLACKLIST.stream().noneMatch(uri::startsWith);
    }

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        logger.info(message);
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        logger.info(message);
    }
}