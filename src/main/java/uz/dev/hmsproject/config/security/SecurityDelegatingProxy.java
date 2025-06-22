package uz.dev.hmsproject.config.security;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class SecurityDelegatingProxy extends AbstractSecurityWebApplicationInitializer {

    @Override
    protected boolean isAsyncSecuritySupported() {
        return true;
    }

    @Override
    protected boolean enableHttpSessionEventPublisher() {
        return true;
    }
}