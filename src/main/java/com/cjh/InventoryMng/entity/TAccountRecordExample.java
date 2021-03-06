package com.cjh.InventoryMng.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TAccountRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TAccountRecordExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateStaffIsNull() {
            addCriterion("create_staff is null");
            return (Criteria) this;
        }

        public Criteria andCreateStaffIsNotNull() {
            addCriterion("create_staff is not null");
            return (Criteria) this;
        }

        public Criteria andCreateStaffEqualTo(String value) {
            addCriterion("create_staff =", value, "createStaff");
            return (Criteria) this;
        }

        public Criteria andCreateStaffNotEqualTo(String value) {
            addCriterion("create_staff <>", value, "createStaff");
            return (Criteria) this;
        }

        public Criteria andCreateStaffGreaterThan(String value) {
            addCriterion("create_staff >", value, "createStaff");
            return (Criteria) this;
        }

        public Criteria andCreateStaffGreaterThanOrEqualTo(String value) {
            addCriterion("create_staff >=", value, "createStaff");
            return (Criteria) this;
        }

        public Criteria andCreateStaffLessThan(String value) {
            addCriterion("create_staff <", value, "createStaff");
            return (Criteria) this;
        }

        public Criteria andCreateStaffLessThanOrEqualTo(String value) {
            addCriterion("create_staff <=", value, "createStaff");
            return (Criteria) this;
        }

        public Criteria andCreateStaffLike(String value) {
            addCriterion("create_staff like", value, "createStaff");
            return (Criteria) this;
        }

        public Criteria andCreateStaffNotLike(String value) {
            addCriterion("create_staff not like", value, "createStaff");
            return (Criteria) this;
        }

        public Criteria andCreateStaffIn(List<String> values) {
            addCriterion("create_staff in", values, "createStaff");
            return (Criteria) this;
        }

        public Criteria andCreateStaffNotIn(List<String> values) {
            addCriterion("create_staff not in", values, "createStaff");
            return (Criteria) this;
        }

        public Criteria andCreateStaffBetween(String value1, String value2) {
            addCriterion("create_staff between", value1, value2, "createStaff");
            return (Criteria) this;
        }

        public Criteria andCreateStaffNotBetween(String value1, String value2) {
            addCriterion("create_staff not between", value1, value2, "createStaff");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(String value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("type like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("type not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andAmountIsNull() {
            addCriterion("amount is null");
            return (Criteria) this;
        }

        public Criteria andAmountIsNotNull() {
            addCriterion("amount is not null");
            return (Criteria) this;
        }

        public Criteria andAmountEqualTo(Integer value) {
            addCriterion("amount =", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotEqualTo(Integer value) {
            addCriterion("amount <>", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThan(Integer value) {
            addCriterion("amount >", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanOrEqualTo(Integer value) {
            addCriterion("amount >=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThan(Integer value) {
            addCriterion("amount <", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThanOrEqualTo(Integer value) {
            addCriterion("amount <=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountIn(List<Integer> values) {
            addCriterion("amount in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotIn(List<Integer> values) {
            addCriterion("amount not in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountBetween(Integer value1, Integer value2) {
            addCriterion("amount between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotBetween(Integer value1, Integer value2) {
            addCriterion("amount not between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andTheDateIsNull() {
            addCriterion("the_date is null");
            return (Criteria) this;
        }

        public Criteria andTheDateIsNotNull() {
            addCriterion("the_date is not null");
            return (Criteria) this;
        }

        public Criteria andTheDateEqualTo(String value) {
            addCriterion("the_date =", value, "theDate");
            return (Criteria) this;
        }

        public Criteria andTheDateNotEqualTo(String value) {
            addCriterion("the_date <>", value, "theDate");
            return (Criteria) this;
        }

        public Criteria andTheDateGreaterThan(String value) {
            addCriterion("the_date >", value, "theDate");
            return (Criteria) this;
        }

        public Criteria andTheDateGreaterThanOrEqualTo(String value) {
            addCriterion("the_date >=", value, "theDate");
            return (Criteria) this;
        }

        public Criteria andTheDateLessThan(String value) {
            addCriterion("the_date <", value, "theDate");
            return (Criteria) this;
        }

        public Criteria andTheDateLessThanOrEqualTo(String value) {
            addCriterion("the_date <=", value, "theDate");
            return (Criteria) this;
        }

        public Criteria andTheDateLike(String value) {
            addCriterion("the_date like", value, "theDate");
            return (Criteria) this;
        }

        public Criteria andTheDateNotLike(String value) {
            addCriterion("the_date not like", value, "theDate");
            return (Criteria) this;
        }

        public Criteria andTheDateIn(List<String> values) {
            addCriterion("the_date in", values, "theDate");
            return (Criteria) this;
        }

        public Criteria andTheDateNotIn(List<String> values) {
            addCriterion("the_date not in", values, "theDate");
            return (Criteria) this;
        }

        public Criteria andTheDateBetween(String value1, String value2) {
            addCriterion("the_date between", value1, value2, "theDate");
            return (Criteria) this;
        }

        public Criteria andTheDateNotBetween(String value1, String value2) {
            addCriterion("the_date not between", value1, value2, "theDate");
            return (Criteria) this;
        }

        public Criteria andApplyDescIsNull() {
            addCriterion("apply_desc is null");
            return (Criteria) this;
        }

        public Criteria andApplyDescIsNotNull() {
            addCriterion("apply_desc is not null");
            return (Criteria) this;
        }

        public Criteria andApplyDescEqualTo(String value) {
            addCriterion("apply_desc =", value, "applyDesc");
            return (Criteria) this;
        }

        public Criteria andApplyDescNotEqualTo(String value) {
            addCriterion("apply_desc <>", value, "applyDesc");
            return (Criteria) this;
        }

        public Criteria andApplyDescGreaterThan(String value) {
            addCriterion("apply_desc >", value, "applyDesc");
            return (Criteria) this;
        }

        public Criteria andApplyDescGreaterThanOrEqualTo(String value) {
            addCriterion("apply_desc >=", value, "applyDesc");
            return (Criteria) this;
        }

        public Criteria andApplyDescLessThan(String value) {
            addCriterion("apply_desc <", value, "applyDesc");
            return (Criteria) this;
        }

        public Criteria andApplyDescLessThanOrEqualTo(String value) {
            addCriterion("apply_desc <=", value, "applyDesc");
            return (Criteria) this;
        }

        public Criteria andApplyDescLike(String value) {
            addCriterion("apply_desc like", value, "applyDesc");
            return (Criteria) this;
        }

        public Criteria andApplyDescNotLike(String value) {
            addCriterion("apply_desc not like", value, "applyDesc");
            return (Criteria) this;
        }

        public Criteria andApplyDescIn(List<String> values) {
            addCriterion("apply_desc in", values, "applyDesc");
            return (Criteria) this;
        }

        public Criteria andApplyDescNotIn(List<String> values) {
            addCriterion("apply_desc not in", values, "applyDesc");
            return (Criteria) this;
        }

        public Criteria andApplyDescBetween(String value1, String value2) {
            addCriterion("apply_desc between", value1, value2, "applyDesc");
            return (Criteria) this;
        }

        public Criteria andApplyDescNotBetween(String value1, String value2) {
            addCriterion("apply_desc not between", value1, value2, "applyDesc");
            return (Criteria) this;
        }

        public Criteria andRecordUserIdIsNull() {
            addCriterion("record_user_id is null");
            return (Criteria) this;
        }

        public Criteria andRecordUserIdIsNotNull() {
            addCriterion("record_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andRecordUserIdEqualTo(String value) {
            addCriterion("record_user_id =", value, "recordUserId");
            return (Criteria) this;
        }

        public Criteria andRecordUserIdNotEqualTo(String value) {
            addCriterion("record_user_id <>", value, "recordUserId");
            return (Criteria) this;
        }

        public Criteria andRecordUserIdGreaterThan(String value) {
            addCriterion("record_user_id >", value, "recordUserId");
            return (Criteria) this;
        }

        public Criteria andRecordUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("record_user_id >=", value, "recordUserId");
            return (Criteria) this;
        }

        public Criteria andRecordUserIdLessThan(String value) {
            addCriterion("record_user_id <", value, "recordUserId");
            return (Criteria) this;
        }

        public Criteria andRecordUserIdLessThanOrEqualTo(String value) {
            addCriterion("record_user_id <=", value, "recordUserId");
            return (Criteria) this;
        }

        public Criteria andRecordUserIdLike(String value) {
            addCriterion("record_user_id like", value, "recordUserId");
            return (Criteria) this;
        }

        public Criteria andRecordUserIdNotLike(String value) {
            addCriterion("record_user_id not like", value, "recordUserId");
            return (Criteria) this;
        }

        public Criteria andRecordUserIdIn(List<String> values) {
            addCriterion("record_user_id in", values, "recordUserId");
            return (Criteria) this;
        }

        public Criteria andRecordUserIdNotIn(List<String> values) {
            addCriterion("record_user_id not in", values, "recordUserId");
            return (Criteria) this;
        }

        public Criteria andRecordUserIdBetween(String value1, String value2) {
            addCriterion("record_user_id between", value1, value2, "recordUserId");
            return (Criteria) this;
        }

        public Criteria andRecordUserIdNotBetween(String value1, String value2) {
            addCriterion("record_user_id not between", value1, value2, "recordUserId");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andWhyIsNull() {
            addCriterion("why is null");
            return (Criteria) this;
        }

        public Criteria andWhyIsNotNull() {
            addCriterion("why is not null");
            return (Criteria) this;
        }

        public Criteria andWhyEqualTo(String value) {
            addCriterion("why =", value, "why");
            return (Criteria) this;
        }

        public Criteria andWhyNotEqualTo(String value) {
            addCriterion("why <>", value, "why");
            return (Criteria) this;
        }

        public Criteria andWhyGreaterThan(String value) {
            addCriterion("why >", value, "why");
            return (Criteria) this;
        }

        public Criteria andWhyGreaterThanOrEqualTo(String value) {
            addCriterion("why >=", value, "why");
            return (Criteria) this;
        }

        public Criteria andWhyLessThan(String value) {
            addCriterion("why <", value, "why");
            return (Criteria) this;
        }

        public Criteria andWhyLessThanOrEqualTo(String value) {
            addCriterion("why <=", value, "why");
            return (Criteria) this;
        }

        public Criteria andWhyLike(String value) {
            addCriterion("why like", value, "why");
            return (Criteria) this;
        }

        public Criteria andWhyNotLike(String value) {
            addCriterion("why not like", value, "why");
            return (Criteria) this;
        }

        public Criteria andWhyIn(List<String> values) {
            addCriterion("why in", values, "why");
            return (Criteria) this;
        }

        public Criteria andWhyNotIn(List<String> values) {
            addCriterion("why not in", values, "why");
            return (Criteria) this;
        }

        public Criteria andWhyBetween(String value1, String value2) {
            addCriterion("why between", value1, value2, "why");
            return (Criteria) this;
        }

        public Criteria andWhyNotBetween(String value1, String value2) {
            addCriterion("why not between", value1, value2, "why");
            return (Criteria) this;
        }

        public Criteria andFile1NameIsNull() {
            addCriterion("file1_name is null");
            return (Criteria) this;
        }

        public Criteria andFile1NameIsNotNull() {
            addCriterion("file1_name is not null");
            return (Criteria) this;
        }

        public Criteria andFile1NameEqualTo(String value) {
            addCriterion("file1_name =", value, "file1Name");
            return (Criteria) this;
        }

        public Criteria andFile1NameNotEqualTo(String value) {
            addCriterion("file1_name <>", value, "file1Name");
            return (Criteria) this;
        }

        public Criteria andFile1NameGreaterThan(String value) {
            addCriterion("file1_name >", value, "file1Name");
            return (Criteria) this;
        }

        public Criteria andFile1NameGreaterThanOrEqualTo(String value) {
            addCriterion("file1_name >=", value, "file1Name");
            return (Criteria) this;
        }

        public Criteria andFile1NameLessThan(String value) {
            addCriterion("file1_name <", value, "file1Name");
            return (Criteria) this;
        }

        public Criteria andFile1NameLessThanOrEqualTo(String value) {
            addCriterion("file1_name <=", value, "file1Name");
            return (Criteria) this;
        }

        public Criteria andFile1NameLike(String value) {
            addCriterion("file1_name like", value, "file1Name");
            return (Criteria) this;
        }

        public Criteria andFile1NameNotLike(String value) {
            addCriterion("file1_name not like", value, "file1Name");
            return (Criteria) this;
        }

        public Criteria andFile1NameIn(List<String> values) {
            addCriterion("file1_name in", values, "file1Name");
            return (Criteria) this;
        }

        public Criteria andFile1NameNotIn(List<String> values) {
            addCriterion("file1_name not in", values, "file1Name");
            return (Criteria) this;
        }

        public Criteria andFile1NameBetween(String value1, String value2) {
            addCriterion("file1_name between", value1, value2, "file1Name");
            return (Criteria) this;
        }

        public Criteria andFile1NameNotBetween(String value1, String value2) {
            addCriterion("file1_name not between", value1, value2, "file1Name");
            return (Criteria) this;
        }

        public Criteria andFile2NameIsNull() {
            addCriterion("file2_name is null");
            return (Criteria) this;
        }

        public Criteria andFile2NameIsNotNull() {
            addCriterion("file2_name is not null");
            return (Criteria) this;
        }

        public Criteria andFile2NameEqualTo(String value) {
            addCriterion("file2_name =", value, "file2Name");
            return (Criteria) this;
        }

        public Criteria andFile2NameNotEqualTo(String value) {
            addCriterion("file2_name <>", value, "file2Name");
            return (Criteria) this;
        }

        public Criteria andFile2NameGreaterThan(String value) {
            addCriterion("file2_name >", value, "file2Name");
            return (Criteria) this;
        }

        public Criteria andFile2NameGreaterThanOrEqualTo(String value) {
            addCriterion("file2_name >=", value, "file2Name");
            return (Criteria) this;
        }

        public Criteria andFile2NameLessThan(String value) {
            addCriterion("file2_name <", value, "file2Name");
            return (Criteria) this;
        }

        public Criteria andFile2NameLessThanOrEqualTo(String value) {
            addCriterion("file2_name <=", value, "file2Name");
            return (Criteria) this;
        }

        public Criteria andFile2NameLike(String value) {
            addCriterion("file2_name like", value, "file2Name");
            return (Criteria) this;
        }

        public Criteria andFile2NameNotLike(String value) {
            addCriterion("file2_name not like", value, "file2Name");
            return (Criteria) this;
        }

        public Criteria andFile2NameIn(List<String> values) {
            addCriterion("file2_name in", values, "file2Name");
            return (Criteria) this;
        }

        public Criteria andFile2NameNotIn(List<String> values) {
            addCriterion("file2_name not in", values, "file2Name");
            return (Criteria) this;
        }

        public Criteria andFile2NameBetween(String value1, String value2) {
            addCriterion("file2_name between", value1, value2, "file2Name");
            return (Criteria) this;
        }

        public Criteria andFile2NameNotBetween(String value1, String value2) {
            addCriterion("file2_name not between", value1, value2, "file2Name");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}