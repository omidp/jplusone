package com.grexdev.nplusone.core;

import com.grexdev.nplusone.core.properties.NPlusOneProperties;
import com.grexdev.nplusone.core.proxy.ProxiedRootsBeanPostProcessor;
import com.grexdev.nplusone.core.registry.RootNode;
import com.grexdev.nplusone.core.tracking.ActivationStateListener;
import com.grexdev.nplusone.core.tracking.TrackingContext;
import com.grexdev.nplusone.core.tracking.TrackingStateListener;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(NPlusOneProperties.class)
@ConditionalOnProperty(prefix = "nplusone", name = "enabled", matchIfMissing = true)
public class NPlusOneAutoConfiguration {

    private final NPlusOneProperties nPlusOneProperties;

    @Bean
    public TrackingContext proxyContext() {
        return new TrackingContext(nPlusOneProperties.getApplicationRootPackage(), nPlusOneProperties.isDebugMode());
    }

    @Bean
    public RootNode rootNode() {
        return new RootNode();
    }

    @Bean
    public TrackingStateListener trackingStateListener(TrackingContext context, RootNode rootNode) {
        return new TrackingStateListener(context, rootNode);
    }

    @Bean
    public ActivationStateListener activationStateListener(TrackingContext trackingContext, TrackingStateListener stateListener) {
        return new ActivationStateListener(stateListener, trackingContext);
    }

    @Bean
    public BeanPostProcessor proxiedRootsBeanPostProcessor(ActivationStateListener stateListener) {
        return new ProxiedRootsBeanPostProcessor(stateListener);
    }

}
