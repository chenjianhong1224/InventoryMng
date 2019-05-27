package com.cjh.InventoryMng.entity;

import java.util.ArrayList;
import java.util.List;

public class TPlatformProfitExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TPlatformProfitExample() {
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
            addCriterion("Id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("Id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("Id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("Id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("Id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("Id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("Id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("Id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("Id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("Id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("Id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("Id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andMemberIdIsNull() {
            addCriterion("member_id is null");
            return (Criteria) this;
        }

        public Criteria andMemberIdIsNotNull() {
            addCriterion("member_id is not null");
            return (Criteria) this;
        }

        public Criteria andMemberIdEqualTo(Integer value) {
            addCriterion("member_id =", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotEqualTo(Integer value) {
            addCriterion("member_id <>", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdGreaterThan(Integer value) {
            addCriterion("member_id >", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("member_id >=", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdLessThan(Integer value) {
            addCriterion("member_id <", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdLessThanOrEqualTo(Integer value) {
            addCriterion("member_id <=", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdIn(List<Integer> values) {
            addCriterion("member_id in", values, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotIn(List<Integer> values) {
            addCriterion("member_id not in", values, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdBetween(Integer value1, Integer value2) {
            addCriterion("member_id between", value1, value2, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotBetween(Integer value1, Integer value2) {
            addCriterion("member_id not between", value1, value2, "memberId");
            return (Criteria) this;
        }

        public Criteria andSettleDateIsNull() {
            addCriterion("settle_date is null");
            return (Criteria) this;
        }

        public Criteria andSettleDateIsNotNull() {
            addCriterion("settle_date is not null");
            return (Criteria) this;
        }

        public Criteria andSettleDateEqualTo(String value) {
            addCriterion("settle_date =", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateNotEqualTo(String value) {
            addCriterion("settle_date <>", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateGreaterThan(String value) {
            addCriterion("settle_date >", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateGreaterThanOrEqualTo(String value) {
            addCriterion("settle_date >=", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateLessThan(String value) {
            addCriterion("settle_date <", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateLessThanOrEqualTo(String value) {
            addCriterion("settle_date <=", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateLike(String value) {
            addCriterion("settle_date like", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateNotLike(String value) {
            addCriterion("settle_date not like", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateIn(List<String> values) {
            addCriterion("settle_date in", values, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateNotIn(List<String> values) {
            addCriterion("settle_date not in", values, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateBetween(String value1, String value2) {
            addCriterion("settle_date between", value1, value2, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateNotBetween(String value1, String value2) {
            addCriterion("settle_date not between", value1, value2, "settleDate");
            return (Criteria) this;
        }

        public Criteria andMeituanProfitIsNull() {
            addCriterion("meituan_profit is null");
            return (Criteria) this;
        }

        public Criteria andMeituanProfitIsNotNull() {
            addCriterion("meituan_profit is not null");
            return (Criteria) this;
        }

        public Criteria andMeituanProfitEqualTo(Integer value) {
            addCriterion("meituan_profit =", value, "meituanProfit");
            return (Criteria) this;
        }

        public Criteria andMeituanProfitNotEqualTo(Integer value) {
            addCriterion("meituan_profit <>", value, "meituanProfit");
            return (Criteria) this;
        }

        public Criteria andMeituanProfitGreaterThan(Integer value) {
            addCriterion("meituan_profit >", value, "meituanProfit");
            return (Criteria) this;
        }

        public Criteria andMeituanProfitGreaterThanOrEqualTo(Integer value) {
            addCriterion("meituan_profit >=", value, "meituanProfit");
            return (Criteria) this;
        }

        public Criteria andMeituanProfitLessThan(Integer value) {
            addCriterion("meituan_profit <", value, "meituanProfit");
            return (Criteria) this;
        }

        public Criteria andMeituanProfitLessThanOrEqualTo(Integer value) {
            addCriterion("meituan_profit <=", value, "meituanProfit");
            return (Criteria) this;
        }

        public Criteria andMeituanProfitIn(List<Integer> values) {
            addCriterion("meituan_profit in", values, "meituanProfit");
            return (Criteria) this;
        }

        public Criteria andMeituanProfitNotIn(List<Integer> values) {
            addCriterion("meituan_profit not in", values, "meituanProfit");
            return (Criteria) this;
        }

        public Criteria andMeituanProfitBetween(Integer value1, Integer value2) {
            addCriterion("meituan_profit between", value1, value2, "meituanProfit");
            return (Criteria) this;
        }

        public Criteria andMeituanProfitNotBetween(Integer value1, Integer value2) {
            addCriterion("meituan_profit not between", value1, value2, "meituanProfit");
            return (Criteria) this;
        }

        public Criteria andElemeProfitIsNull() {
            addCriterion("eleme_profit is null");
            return (Criteria) this;
        }

        public Criteria andElemeProfitIsNotNull() {
            addCriterion("eleme_profit is not null");
            return (Criteria) this;
        }

        public Criteria andElemeProfitEqualTo(Integer value) {
            addCriterion("eleme_profit =", value, "elemeProfit");
            return (Criteria) this;
        }

        public Criteria andElemeProfitNotEqualTo(Integer value) {
            addCriterion("eleme_profit <>", value, "elemeProfit");
            return (Criteria) this;
        }

        public Criteria andElemeProfitGreaterThan(Integer value) {
            addCriterion("eleme_profit >", value, "elemeProfit");
            return (Criteria) this;
        }

        public Criteria andElemeProfitGreaterThanOrEqualTo(Integer value) {
            addCriterion("eleme_profit >=", value, "elemeProfit");
            return (Criteria) this;
        }

        public Criteria andElemeProfitLessThan(Integer value) {
            addCriterion("eleme_profit <", value, "elemeProfit");
            return (Criteria) this;
        }

        public Criteria andElemeProfitLessThanOrEqualTo(Integer value) {
            addCriterion("eleme_profit <=", value, "elemeProfit");
            return (Criteria) this;
        }

        public Criteria andElemeProfitIn(List<Integer> values) {
            addCriterion("eleme_profit in", values, "elemeProfit");
            return (Criteria) this;
        }

        public Criteria andElemeProfitNotIn(List<Integer> values) {
            addCriterion("eleme_profit not in", values, "elemeProfit");
            return (Criteria) this;
        }

        public Criteria andElemeProfitBetween(Integer value1, Integer value2) {
            addCriterion("eleme_profit between", value1, value2, "elemeProfit");
            return (Criteria) this;
        }

        public Criteria andElemeProfitNotBetween(Integer value1, Integer value2) {
            addCriterion("eleme_profit not between", value1, value2, "elemeProfit");
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