package com.cjh.InventoryMng.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.cjh.InventoryMng.constants.Constants;
import com.cjh.InventoryMng.entity.TGroupMap;
import com.cjh.InventoryMng.entity.TGroupMapExample;
import com.cjh.InventoryMng.entity.TMemberInfo;
import com.cjh.InventoryMng.entity.TMemberInfoExample;
import com.cjh.InventoryMng.entity.TMemberInfoExample.Criteria;
import com.cjh.InventoryMng.entity.TMemberReduce;
import com.cjh.InventoryMng.entity.TMemberReduceExample;
import com.cjh.InventoryMng.entity.TSysParam;
import com.cjh.InventoryMng.entity.TSysParamExample;
import com.cjh.InventoryMng.entity.TUserInfo;
import com.cjh.InventoryMng.entity.TUserInfoExample;
import com.cjh.InventoryMng.entity.TUserRole;
import com.cjh.InventoryMng.entity.TUserRoleExample;
import com.cjh.InventoryMng.entity.VMemberBillInfo;
import com.cjh.InventoryMng.mapper.CustomQueryMapper;
import com.cjh.InventoryMng.mapper.TGroupMapMapper;
import com.cjh.InventoryMng.mapper.TMemberInfoMapper;
import com.cjh.InventoryMng.mapper.TMemberReduceMapper;
import com.cjh.InventoryMng.mapper.TSysParamMapper;
import com.cjh.InventoryMng.mapper.TUserInfoMapper;
import com.cjh.InventoryMng.mapper.TUserRoleMapper;
import com.cjh.InventoryMng.service.MemberService;
import com.cjh.InventoryMng.entity.VMemberSupplierMap;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

	@Autowired
	private TMemberInfoMapper tMemberInfoMapper;

	@Autowired
	private TSysParamMapper tSysParamMapper;

	@Autowired
	private CustomQueryMapper customQueryMapper;

	@Autowired
	private TUserInfoMapper tUserInfoMapper;

	@Autowired
	private TUserRoleMapper tUserRoleMapper;

	@Autowired
	private TGroupMapMapper tGroupMapMapper;

	@Autowired
	private TMemberReduceMapper tMemberReduceMapper;

	@Override
	public List<TMemberInfo> getAllEffectiveMember() {
		TMemberInfoExample example = new TMemberInfoExample();
		example.createCriteria().andStatusEqualTo(1);
		List<TMemberInfo> resultList = tMemberInfoMapper.selectByExample(example);
		return resultList;
	}

	@Override
	public TMemberInfo getMemberInfo(String userId) {
		TMemberInfoExample example = new TMemberInfoExample();
		example.createCriteria().andUserIdEqualTo(userId);
		List<TMemberInfo> returnList = tMemberInfoMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(returnList)) {
			return null;
		}
		return returnList.get(0);
	}

	@Override
	public TMemberInfo getMemberInfo(Integer memberId) {
		return tMemberInfoMapper.selectByPrimaryKey(memberId);
	}

	@Override
	public List<TMemberInfo> getEffectiveMember(String brandId) {
		TMemberInfoExample example = new TMemberInfoExample();
		example.createCriteria().andBrandEqualTo(brandId);
		return tMemberInfoMapper.selectByExample(example);
	}

	@Override
	public String getMemberBrand(Integer memberId) {
		TMemberInfo tMemberInfo = tMemberInfoMapper.selectByPrimaryKey(memberId);
		TSysParamExample example = new TSysParamExample();
		example.createCriteria().andParamNameEqualTo(Constants.BrandsysParamName)
				.andParamKeyEqualTo(tMemberInfo.getBrand());
		List<TSysParam> result = tSysParamMapper.selectByExample(example);
		return CollectionUtils.isEmpty(result) ? null : result.get(0).getParamValue();
	}

	@Override
	public Page<VMemberBillInfo> getMemberBill(String brandId, Integer memberId, String beginDate, String endDate,
			int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		return customQueryMapper.queryMemberBill(brandId, memberId, beginDate, endDate);
	}

	@Override
	public Integer getMemberId(String memberName, String brandId) {
		TMemberInfoExample example = new TMemberInfoExample();
		example.createCriteria().andBrandEqualTo(brandId).andMemberNameEqualTo(memberName);
		List<TMemberInfo> returnList = tMemberInfoMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(returnList)) {
			return null;
		}
		return returnList.get(0).getId();
	}

	@Override
	public Page<TMemberInfo> getAllMember(String memberName, String brandId, int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		TMemberInfoExample example = new TMemberInfoExample();
		Criteria criteria = example.createCriteria();
		if (!brandId.equals("0")) {
			criteria.andBrandEqualTo(brandId);
		}
		if (!StringUtils.isEmpty(memberName)) {
			criteria.andMemberNameLike("%" + memberName + "%");
		}
		return tMemberInfoMapper.selectByExample(example);
	}

	@Override
	public boolean deleteMember(Integer id) {
		TMemberInfo record = new TMemberInfo();
		record.setStatus(0);
		TMemberInfoExample example = new TMemberInfoExample();
		example.createCriteria().andIdEqualTo(id);
		tMemberInfoMapper.updateByExampleSelective(record, example);
		TUserInfoExample userExample = new TUserInfoExample();
		userExample.createCriteria().andUserIdEqualTo(tMemberInfoMapper.selectByPrimaryKey(id).getUserId());
		TUserInfo userRecord = new TUserInfo();
		userRecord.setStatus(0);
		tUserInfoMapper.updateByExampleSelective(userRecord, userExample);
		return true;
	}

	@Override
	public boolean newMember(String memberName, String city, String province, String phone, String address,
			String brand, String accNo, String accName, Integer roleId) {
		TUserInfo userRecord = new TUserInfo();
		userRecord.setPhone(phone);
		userRecord.setUserName(memberName);
		userRecord.setUserType(1);
		userRecord.setStatus(1);
		Date createTime = new Date();
		userRecord.setCreateTime(createTime);
		String password = "1haozhou666";
		Md5Hash md5Hash = new Md5Hash(password, "", 1);
		userRecord.setPassword(md5Hash.toString());
		userRecord.setUserId(phone);
		tUserInfoMapper.insert(userRecord);
		TMemberInfo record = new TMemberInfo();
		record.setAddress(address);
		record.setBrand(brand);
		record.setMemberName(memberName);
		record.setCity(city);
		record.setProvince(province);
		record.setPhone(phone);
		record.setStatus(1);
		record.setUserId(phone);
		record.setAccName(accName);
		record.setAccNo(accNo);
		tMemberInfoMapper.insert(record);
		TUserRole userRoleRecord = new TUserRole();
		userRoleRecord.setRoleId(roleId);
		userRoleRecord.setUserId(phone);
		userRoleRecord.setStatus(1);
		tUserRoleMapper.insert(userRoleRecord);
		return true;
	}

	@Override
	public boolean updateMember(Integer id, String memberName, String city, String province, String phone,
			String address, String brand, String accNo, String accName, Integer roleId) {
		TMemberInfo record = new TMemberInfo();
		record.setAddress(address);
		record.setBrand(brand);
		record.setCity(city);
		record.setProvince(province);
		record.setStatus(1);
		record.setMemberName(memberName);
		record.setAccName(accName);
		record.setAccNo(accNo);
		record.setPhone(phone);
		record.setId(id);
		tMemberInfoMapper.updateByPrimaryKeySelective(record);
		TUserInfoExample example = new TUserInfoExample();
		String userId = tMemberInfoMapper.selectByPrimaryKey(id).getUserId();
		example.createCriteria().andUserIdEqualTo(userId);
		TUserInfo userRecord = new TUserInfo();
		userRecord.setPhone(phone);
		userRecord.setStatus(1);
		tUserInfoMapper.updateByExampleSelective(userRecord, example);
		TUserRoleExample userRoleExample = new TUserRoleExample();
		userRoleExample.createCriteria().andUserIdEqualTo(userId).andRoleIdEqualTo(roleId);
		TUserRole deleteRecord = new TUserRole();
		deleteRecord.setStatus(0);
		TUserRoleExample deleteExample = new TUserRoleExample();
		deleteExample.createCriteria().andUserIdEqualTo(userId);
		tUserRoleMapper.updateByExampleSelective(deleteRecord, deleteExample);
		List<TUserRole> tUserRoleList = tUserRoleMapper.selectByExample(userRoleExample);
		if (CollectionUtils.isEmpty(tUserRoleList)) {
			TUserRole userRoleRecord = new TUserRole();
			userRoleRecord.setRoleId(roleId);
			userRoleRecord.setUserId(userId);
			userRoleRecord.setStatus(1);
			tUserRoleMapper.insert(userRoleRecord);
		} else {
			TUserRole userRoleRecord = new TUserRole();
			userRoleRecord.setStatus(1);
			userRoleRecord.setId(tUserRoleList.get(0).getId());
			tUserRoleMapper.updateByPrimaryKeySelective(userRoleRecord);
		}
		return true;
	}

	@Override
	public Page<VMemberSupplierMap> querySupplierOfMember(Integer memberId, int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		return customQueryMapper.querySupplierOfMember(memberId);
	}

	@Override
	public boolean mapSupplier(Integer memberId, Integer supplierId, boolean bindFlag) {
		TGroupMapExample example = new TGroupMapExample();
		example.createCriteria().andMemberIdEqualTo(memberId).andSupplierIdEqualTo(supplierId);
		List<TGroupMap> tGroupMapList = tGroupMapMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(tGroupMapList)) {
			if (bindFlag) {
				TGroupMap record = new TGroupMap();
				record.setMemberId(memberId);
				record.setSupplierId(supplierId);
				record.setStatus(1);
				tGroupMapMapper.insert(record);
			} else {
				return false;
			}
		} else {
			TGroupMap record = new TGroupMap();
			if (bindFlag) {
				record.setStatus(1);
			} else {
				record.setStatus(0);
			}
			record.setId(tGroupMapList.get(0).getId());
			tGroupMapMapper.updateByPrimaryKeySelective(record);
		}
		return true;
	}

	@Override
	public Page<TMemberReduce> getMemberReduce(Integer memberId, String beginDate, String endDate, int pageNo,
			int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		TMemberReduceExample example = new TMemberReduceExample();
		com.cjh.InventoryMng.entity.TMemberReduceExample.Criteria c = example.createCriteria()
				.andReduceDateGreaterThanOrEqualTo(beginDate).andReduceDateLessThanOrEqualTo(endDate);
		if (memberId != null) {
			c.andMemberIdEqualTo(memberId);
		}
		return tMemberReduceMapper.selectByExample(example);
	}

}
