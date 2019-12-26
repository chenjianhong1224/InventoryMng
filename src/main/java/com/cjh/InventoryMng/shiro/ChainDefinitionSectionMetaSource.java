package com.cjh.InventoryMng.shiro;

import java.text.MessageFormat;
import java.util.List;

import org.apache.shiro.config.Ini;
import org.apache.shiro.config.Ini.Section;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.util.Factory;
import org.apache.shiro.web.config.IniFilterChainResolverFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.cjh.InventoryMng.entity.TResourceInfo;
import com.cjh.InventoryMng.entity.TResourceInfoExample;
import com.cjh.InventoryMng.mapper.TResourceInfoMapper;
import com.cjh.InventoryMng.mapper.TResourceRoleMapper;

public class ChainDefinitionSectionMetaSource implements Factory<Ini.Section> {

	@Autowired
	TResourceRoleMapper tResourceRoleMapper;

	@Autowired
	TResourceInfoMapper tResourceInfoMapper;

	private String filterChainDefinitions;

	public static final String PREMISSION_STRING = "perms[\"{0}\"]";

	@Override
	public Section getInstance() {
		Ini ini = new Ini();
		filterChainDefinitions = "/static/**=anon";
		ini.load(filterChainDefinitions);

		// 权限拦截
		Section section = ini.getSection(IniFilterChainResolverFactory.URLS);
		if (CollectionUtils.isEmpty(section)) {
			section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
		}

		TResourceInfoExample resourceExample = new TResourceInfoExample();
		List<TResourceInfo> tResourceInfoList = tResourceInfoMapper.selectByExample(resourceExample);
		for (TResourceInfo tResourceInfo : tResourceInfoList) {
			if (!StringUtils.isEmpty(tResourceInfo.getPerms())
					&& !StringUtils.isEmpty(tResourceInfo.getResourceUrl())) {
				section.put(tResourceInfo.getResourceUrl(),
						MessageFormat.format(PREMISSION_STRING, tResourceInfo.getPerms()));
			}
		}

		// 配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
		section.put("/logout", "logout");
		section.put("/other/**", "anon");
		section.put("/error/**", "anon");
		section.put("/css/**", "anon");
		section.put("/img/**", "anon");
		section.put("/login", "anon");
		section.put("/js/**", "anon");
		section.put("/jquery/**", "anon");
		section.put("/aui/**", "anon");
		section.put("/lib/**", "anon");
		section.put("/validateCode/**", "anon");
		section.put("/layui/**", "anon");
		section.put("/mobiscroll/**", "anon");
		// <!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边
		// <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
		section.put("/**", "authc");
		return section;
	}

	public void setFilterChainDefinitions(String filterChainDefinitions) {
		this.filterChainDefinitions = filterChainDefinitions;
	}

	public Class<?> getObjectType() {
		return this.getClass();
	}

	public boolean isSingleton() {
		return false;
	}
}
