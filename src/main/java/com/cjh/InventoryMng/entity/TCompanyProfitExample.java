package com.cjh.InventoryMng.entity;

import java.util.ArrayList;
import java.util.List;

public class TCompanyProfitExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TCompanyProfitExample() {
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

        public Criteria andGoodsProfitIsNull() {
            addCriterion("goods_profit is null");
            return (Criteria) this;
        }

        public Criteria andGoodsProfitIsNotNull() {
            addCriterion("goods_profit is not null");
            return (Criteria) this;
        }

        public Criteria andGoodsProfitEqualTo(Integer value) {
            addCriterion("goods_profit =", value, "goodsProfit");
            return (Criteria) this;
        }

        public Criteria andGoodsProfitNotEqualTo(Integer value) {
            addCriterion("goods_profit <>", value, "goodsProfit");
            return (Criteria) this;
        }

        public Criteria andGoodsProfitGreaterThan(Integer value) {
            addCriterion("goods_profit >", value, "goodsProfit");
            return (Criteria) this;
        }

        public Criteria andGoodsProfitGreaterThanOrEqualTo(Integer value) {
            addCriterion("goods_profit >=", value, "goodsProfit");
            return (Criteria) this;
        }

        public Criteria andGoodsProfitLessThan(Integer value) {
            addCriterion("goods_profit <", value, "goodsProfit");
            return (Criteria) this;
        }

        public Criteria andGoodsProfitLessThanOrEqualTo(Integer value) {
            addCriterion("goods_profit <=", value, "goodsProfit");
            return (Criteria) this;
        }

        public Criteria andGoodsProfitIn(List<Integer> values) {
            addCriterion("goods_profit in", values, "goodsProfit");
            return (Criteria) this;
        }

        public Criteria andGoodsProfitNotIn(List<Integer> values) {
            addCriterion("goods_profit not in", values, "goodsProfit");
            return (Criteria) this;
        }

        public Criteria andGoodsProfitBetween(Integer value1, Integer value2) {
            addCriterion("goods_profit between", value1, value2, "goodsProfit");
            return (Criteria) this;
        }

        public Criteria andGoodsProfitNotBetween(Integer value1, Integer value2) {
            addCriterion("goods_profit not between", value1, value2, "goodsProfit");
            return (Criteria) this;
        }

        public Criteria andManagerProfitIsNull() {
            addCriterion("manager_profit is null");
            return (Criteria) this;
        }

        public Criteria andManagerProfitIsNotNull() {
            addCriterion("manager_profit is not null");
            return (Criteria) this;
        }

        public Criteria andManagerProfitEqualTo(Integer value) {
            addCriterion("manager_profit =", value, "managerProfit");
            return (Criteria) this;
        }

        public Criteria andManagerProfitNotEqualTo(Integer value) {
            addCriterion("manager_profit <>", value, "managerProfit");
            return (Criteria) this;
        }

        public Criteria andManagerProfitGreaterThan(Integer value) {
            addCriterion("manager_profit >", value, "managerProfit");
            return (Criteria) this;
        }

        public Criteria andManagerProfitGreaterThanOrEqualTo(Integer value) {
            addCriterion("manager_profit >=", value, "managerProfit");
            return (Criteria) this;
        }

        public Criteria andManagerProfitLessThan(Integer value) {
            addCriterion("manager_profit <", value, "managerProfit");
            return (Criteria) this;
        }

        public Criteria andManagerProfitLessThanOrEqualTo(Integer value) {
            addCriterion("manager_profit <=", value, "managerProfit");
            return (Criteria) this;
        }

        public Criteria andManagerProfitIn(List<Integer> values) {
            addCriterion("manager_profit in", values, "managerProfit");
            return (Criteria) this;
        }

        public Criteria andManagerProfitNotIn(List<Integer> values) {
            addCriterion("manager_profit not in", values, "managerProfit");
            return (Criteria) this;
        }

        public Criteria andManagerProfitBetween(Integer value1, Integer value2) {
            addCriterion("manager_profit between", value1, value2, "managerProfit");
            return (Criteria) this;
        }

        public Criteria andManagerProfitNotBetween(Integer value1, Integer value2) {
            addCriterion("manager_profit not between", value1, value2, "managerProfit");
            return (Criteria) this;
        }

        public Criteria andFreightCostIsNull() {
            addCriterion("freight_cost is null");
            return (Criteria) this;
        }

        public Criteria andFreightCostIsNotNull() {
            addCriterion("freight_cost is not null");
            return (Criteria) this;
        }

        public Criteria andFreightCostEqualTo(Integer value) {
            addCriterion("freight_cost =", value, "freightCost");
            return (Criteria) this;
        }

        public Criteria andFreightCostNotEqualTo(Integer value) {
            addCriterion("freight_cost <>", value, "freightCost");
            return (Criteria) this;
        }

        public Criteria andFreightCostGreaterThan(Integer value) {
            addCriterion("freight_cost >", value, "freightCost");
            return (Criteria) this;
        }

        public Criteria andFreightCostGreaterThanOrEqualTo(Integer value) {
            addCriterion("freight_cost >=", value, "freightCost");
            return (Criteria) this;
        }

        public Criteria andFreightCostLessThan(Integer value) {
            addCriterion("freight_cost <", value, "freightCost");
            return (Criteria) this;
        }

        public Criteria andFreightCostLessThanOrEqualTo(Integer value) {
            addCriterion("freight_cost <=", value, "freightCost");
            return (Criteria) this;
        }

        public Criteria andFreightCostIn(List<Integer> values) {
            addCriterion("freight_cost in", values, "freightCost");
            return (Criteria) this;
        }

        public Criteria andFreightCostNotIn(List<Integer> values) {
            addCriterion("freight_cost not in", values, "freightCost");
            return (Criteria) this;
        }

        public Criteria andFreightCostBetween(Integer value1, Integer value2) {
            addCriterion("freight_cost between", value1, value2, "freightCost");
            return (Criteria) this;
        }

        public Criteria andFreightCostNotBetween(Integer value1, Integer value2) {
            addCriterion("freight_cost not between", value1, value2, "freightCost");
            return (Criteria) this;
        }

        public Criteria andAllCostIsNull() {
            addCriterion("all_cost is null");
            return (Criteria) this;
        }

        public Criteria andAllCostIsNotNull() {
            addCriterion("all_cost is not null");
            return (Criteria) this;
        }

        public Criteria andAllCostEqualTo(Integer value) {
            addCriterion("all_cost =", value, "allCost");
            return (Criteria) this;
        }

        public Criteria andAllCostNotEqualTo(Integer value) {
            addCriterion("all_cost <>", value, "allCost");
            return (Criteria) this;
        }

        public Criteria andAllCostGreaterThan(Integer value) {
            addCriterion("all_cost >", value, "allCost");
            return (Criteria) this;
        }

        public Criteria andAllCostGreaterThanOrEqualTo(Integer value) {
            addCriterion("all_cost >=", value, "allCost");
            return (Criteria) this;
        }

        public Criteria andAllCostLessThan(Integer value) {
            addCriterion("all_cost <", value, "allCost");
            return (Criteria) this;
        }

        public Criteria andAllCostLessThanOrEqualTo(Integer value) {
            addCriterion("all_cost <=", value, "allCost");
            return (Criteria) this;
        }

        public Criteria andAllCostIn(List<Integer> values) {
            addCriterion("all_cost in", values, "allCost");
            return (Criteria) this;
        }

        public Criteria andAllCostNotIn(List<Integer> values) {
            addCriterion("all_cost not in", values, "allCost");
            return (Criteria) this;
        }

        public Criteria andAllCostBetween(Integer value1, Integer value2) {
            addCriterion("all_cost between", value1, value2, "allCost");
            return (Criteria) this;
        }

        public Criteria andAllCostNotBetween(Integer value1, Integer value2) {
            addCriterion("all_cost not between", value1, value2, "allCost");
            return (Criteria) this;
        }

        public Criteria andApprovedAccountIsNull() {
            addCriterion("approved_account is null");
            return (Criteria) this;
        }

        public Criteria andApprovedAccountIsNotNull() {
            addCriterion("approved_account is not null");
            return (Criteria) this;
        }

        public Criteria andApprovedAccountEqualTo(Integer value) {
            addCriterion("approved_account =", value, "approvedAccount");
            return (Criteria) this;
        }

        public Criteria andApprovedAccountNotEqualTo(Integer value) {
            addCriterion("approved_account <>", value, "approvedAccount");
            return (Criteria) this;
        }

        public Criteria andApprovedAccountGreaterThan(Integer value) {
            addCriterion("approved_account >", value, "approvedAccount");
            return (Criteria) this;
        }

        public Criteria andApprovedAccountGreaterThanOrEqualTo(Integer value) {
            addCriterion("approved_account >=", value, "approvedAccount");
            return (Criteria) this;
        }

        public Criteria andApprovedAccountLessThan(Integer value) {
            addCriterion("approved_account <", value, "approvedAccount");
            return (Criteria) this;
        }

        public Criteria andApprovedAccountLessThanOrEqualTo(Integer value) {
            addCriterion("approved_account <=", value, "approvedAccount");
            return (Criteria) this;
        }

        public Criteria andApprovedAccountIn(List<Integer> values) {
            addCriterion("approved_account in", values, "approvedAccount");
            return (Criteria) this;
        }

        public Criteria andApprovedAccountNotIn(List<Integer> values) {
            addCriterion("approved_account not in", values, "approvedAccount");
            return (Criteria) this;
        }

        public Criteria andApprovedAccountBetween(Integer value1, Integer value2) {
            addCriterion("approved_account between", value1, value2, "approvedAccount");
            return (Criteria) this;
        }

        public Criteria andApprovedAccountNotBetween(Integer value1, Integer value2) {
            addCriterion("approved_account not between", value1, value2, "approvedAccount");
            return (Criteria) this;
        }

        public Criteria andMonthIsNull() {
            addCriterion("month is null");
            return (Criteria) this;
        }

        public Criteria andMonthIsNotNull() {
            addCriterion("month is not null");
            return (Criteria) this;
        }

        public Criteria andMonthEqualTo(String value) {
            addCriterion("month =", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthNotEqualTo(String value) {
            addCriterion("month <>", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthGreaterThan(String value) {
            addCriterion("month >", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthGreaterThanOrEqualTo(String value) {
            addCriterion("month >=", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthLessThan(String value) {
            addCriterion("month <", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthLessThanOrEqualTo(String value) {
            addCriterion("month <=", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthLike(String value) {
            addCriterion("month like", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthNotLike(String value) {
            addCriterion("month not like", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthIn(List<String> values) {
            addCriterion("month in", values, "month");
            return (Criteria) this;
        }

        public Criteria andMonthNotIn(List<String> values) {
            addCriterion("month not in", values, "month");
            return (Criteria) this;
        }

        public Criteria andMonthBetween(String value1, String value2) {
            addCriterion("month between", value1, value2, "month");
            return (Criteria) this;
        }

        public Criteria andMonthNotBetween(String value1, String value2) {
            addCriterion("month not between", value1, value2, "month");
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