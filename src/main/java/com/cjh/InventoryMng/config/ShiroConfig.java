package com.cjh.InventoryMng.config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.cjh.InventoryMng.shiro.ChainDefinitionSectionMetaSource;
import com.cjh.InventoryMng.shiro.MyFormAuthenticationFilter;
import com.cjh.InventoryMng.shiro.MyShiroRealm;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

import org.apache.shiro.mgt.SecurityManager;

@Configuration
public class ShiroConfig {

	@Bean
	public HashedCredentialsMatcher HashedCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
		matcher.setHashIterations(1);
		matcher.setHashAlgorithmName("MD5");
		matcher.setStoredCredentialsHexEncoded(true);
		return matcher;
	}

	@Bean
	@Order(1)
	public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager,
			ChainDefinitionSectionMetaSource chainDefinitionSectionMetaSource) {
		MyFormAuthenticationFilter myFormAuthenticationFilter = new MyFormAuthenticationFilter();
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		// 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
		shiroFilterFactoryBean.setLoginUrl("/login");
		// 登录成功后要跳转的链接
		shiroFilterFactoryBean.setSuccessUrl("/index");

		// 未授权界面;
		shiroFilterFactoryBean.setUnauthorizedUrl("/error/code403");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(chainDefinitionSectionMetaSource.getInstance());
		Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
		filters.put("authc", myFormAuthenticationFilter);
		shiroFilterFactoryBean.setFilters(filters);
		return shiroFilterFactoryBean;
	}

	@Bean
	public MyShiroRealm myShiroRealm() {
		MyShiroRealm myShiroRealm = new MyShiroRealm();
		myShiroRealm.setCredentialsMatcher(HashedCredentialsMatcher());
		return myShiroRealm;
	}

	@Bean
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(myShiroRealm());
		return securityManager;
	}

	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	public ChainDefinitionSectionMetaSource chainDefinitionSectionMetaSource() {
		return new ChainDefinitionSectionMetaSource();
	}

	@Bean
	public MyFormAuthenticationFilter myFormAuthenticationFilter() {
		return new MyFormAuthenticationFilter();
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

	@Bean(name = "shiroDialect")
	public ShiroDialect shiroDialect() {
		return new ShiroDialect();
	}
}
