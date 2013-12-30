package kauppalehtiHarjoitus.rssHarjoitus.config;import static org.springframework.context.annotation.ComponentScan.Filter;import java.util.ArrayList;import java.util.List;import org.springframework.context.MessageSource;import org.springframework.context.annotation.Bean;import org.springframework.context.annotation.ComponentScan;import org.springframework.context.annotation.Configuration;import org.springframework.context.support.ReloadableResourceBundleMessageSource;import org.springframework.core.annotation.Order;import org.springframework.stereotype.Controller;import org.springframework.validation.Validator;import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;import org.springframework.web.accept.ContentNegotiationManager;import org.springframework.web.servlet.ViewResolver;import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;import org.springframework.web.servlet.view.BeanNameViewResolver;import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;import org.thymeleaf.extras.springsecurity3.dialect.SpringSecurityDialect;import org.thymeleaf.spring3.SpringTemplateEngine;import org.thymeleaf.spring3.view.ThymeleafViewResolver;import org.thymeleaf.templateresolver.ServletContextTemplateResolver;import org.thymeleaf.templateresolver.TemplateResolver;import kauppalehtiHarjoitus.rssHarjoitus.Application;import kauppalehtiHarjoitus.rssHarjoitus.backend.RssFeedViewer;@Configuration@ComponentScan(basePackageClasses = Application.class, includeFilters = @Filter(Controller.class), useDefaultFilters = false)class WebMvcConfig extends WebMvcConfigurationSupport {    private static final String MESSAGE_SOURCE = "/WEB-INF/i18n/messages";    private static final String VIEWS = "/WEB-INF/views/";    private static final String RESOURCES_HANDLER = "/resources/";    private static final String RESOURCES_LOCATION = RESOURCES_HANDLER + "**";    @Override    public RequestMappingHandlerMapping requestMappingHandlerMapping() {        RequestMappingHandlerMapping requestMappingHandlerMapping = super.requestMappingHandlerMapping();        requestMappingHandlerMapping.setUseSuffixPatternMatch(false);        requestMappingHandlerMapping.setUseTrailingSlashMatch(false);        return requestMappingHandlerMapping;    }    @Bean(name = "messageSource")    public MessageSource messageSource() {        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();        messageSource.setBasename(MESSAGE_SOURCE);        messageSource.setCacheSeconds(5);        return messageSource;    }    @Bean    public TemplateResolver templateResolver() {        TemplateResolver templateResolver = new ServletContextTemplateResolver();        templateResolver.setPrefix(VIEWS);        templateResolver.setSuffix(".html");        templateResolver.setTemplateMode("HTML5");        templateResolver.setCacheable(false);        return templateResolver;    }    @Bean    public SpringTemplateEngine templateEngine() {        SpringTemplateEngine templateEngine = new SpringTemplateEngine();        templateEngine.setTemplateResolver(templateResolver());        templateEngine.addDialect(new SpringSecurityDialect());        return templateEngine;    }    @Bean    public ThymeleafViewResolver viewResolver() {        ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver( );        thymeleafViewResolver.setTemplateEngine(templateEngine());        thymeleafViewResolver.setCharacterEncoding("UTF-8");        return thymeleafViewResolver;    }    @Bean	public BeanNameViewResolver beanNameViewResolver(){    	BeanNameViewResolver resolver = new BeanNameViewResolver();		resolver.setOrder(1);    	return resolver;	}		@Bean	public static RssFeedViewer rssFeedViewer(){		return new RssFeedViewer();	}	    @Override    public Validator getValidator() {        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();        validator.setValidationMessageSource(messageSource());        return validator;    }    @Override    public void addResourceHandlers(ResourceHandlerRegistry registry) {        registry.addResourceHandler(RESOURCES_HANDLER).addResourceLocations(RESOURCES_LOCATION);    }    @Override    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {        configurer.enable();    }}