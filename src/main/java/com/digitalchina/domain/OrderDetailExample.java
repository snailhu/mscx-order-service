package com.digitalchina.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OrderDetailExample() {
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

        public Criteria andOrderIdIsNull() {
            addCriterion("order_id is null");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNotNull() {
            addCriterion("order_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrderIdEqualTo(Long value) {
            addCriterion("order_id =", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotEqualTo(Long value) {
            addCriterion("order_id <>", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThan(Long value) {
            addCriterion("order_id >", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(Long value) {
            addCriterion("order_id >=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThan(Long value) {
            addCriterion("order_id <", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(Long value) {
            addCriterion("order_id <=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIn(List<Long> values) {
            addCriterion("order_id in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotIn(List<Long> values) {
            addCriterion("order_id not in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdBetween(Long value1, Long value2) {
            addCriterion("order_id between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotBetween(Long value1, Long value2) {
            addCriterion("order_id not between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andResourceIdIsNull() {
            addCriterion("resource_id is null");
            return (Criteria) this;
        }

        public Criteria andResourceIdIsNotNull() {
            addCriterion("resource_id is not null");
            return (Criteria) this;
        }

        public Criteria andResourceIdEqualTo(Integer value) {
            addCriterion("resource_id =", value, "resourceId");
            return (Criteria) this;
        }

        public Criteria andResourceIdNotEqualTo(Integer value) {
            addCriterion("resource_id <>", value, "resourceId");
            return (Criteria) this;
        }

        public Criteria andResourceIdGreaterThan(Integer value) {
            addCriterion("resource_id >", value, "resourceId");
            return (Criteria) this;
        }

        public Criteria andResourceIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("resource_id >=", value, "resourceId");
            return (Criteria) this;
        }

        public Criteria andResourceIdLessThan(Integer value) {
            addCriterion("resource_id <", value, "resourceId");
            return (Criteria) this;
        }

        public Criteria andResourceIdLessThanOrEqualTo(Integer value) {
            addCriterion("resource_id <=", value, "resourceId");
            return (Criteria) this;
        }

        public Criteria andResourceIdIn(List<Integer> values) {
            addCriterion("resource_id in", values, "resourceId");
            return (Criteria) this;
        }

        public Criteria andResourceIdNotIn(List<Integer> values) {
            addCriterion("resource_id not in", values, "resourceId");
            return (Criteria) this;
        }

        public Criteria andResourceIdBetween(Integer value1, Integer value2) {
            addCriterion("resource_id between", value1, value2, "resourceId");
            return (Criteria) this;
        }

        public Criteria andResourceIdNotBetween(Integer value1, Integer value2) {
            addCriterion("resource_id not between", value1, value2, "resourceId");
            return (Criteria) this;
        }

        public Criteria andResourceTypeIsNull() {
            addCriterion("resource_type is null");
            return (Criteria) this;
        }

        public Criteria andResourceTypeIsNotNull() {
            addCriterion("resource_type is not null");
            return (Criteria) this;
        }

        public Criteria andResourceTypeEqualTo(String value) {
            addCriterion("resource_type =", value, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeNotEqualTo(String value) {
            addCriterion("resource_type <>", value, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeGreaterThan(String value) {
            addCriterion("resource_type >", value, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeGreaterThanOrEqualTo(String value) {
            addCriterion("resource_type >=", value, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeLessThan(String value) {
            addCriterion("resource_type <", value, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeLessThanOrEqualTo(String value) {
            addCriterion("resource_type <=", value, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeLike(String value) {
            addCriterion("resource_type like", value, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeNotLike(String value) {
            addCriterion("resource_type not like", value, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeIn(List<String> values) {
            addCriterion("resource_type in", values, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeNotIn(List<String> values) {
            addCriterion("resource_type not in", values, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeBetween(String value1, String value2) {
            addCriterion("resource_type between", value1, value2, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeNotBetween(String value1, String value2) {
            addCriterion("resource_type not between", value1, value2, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceNameIsNull() {
            addCriterion("resource_name is null");
            return (Criteria) this;
        }

        public Criteria andResourceNameIsNotNull() {
            addCriterion("resource_name is not null");
            return (Criteria) this;
        }

        public Criteria andResourceNameEqualTo(String value) {
            addCriterion("resource_name =", value, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameNotEqualTo(String value) {
            addCriterion("resource_name <>", value, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameGreaterThan(String value) {
            addCriterion("resource_name >", value, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameGreaterThanOrEqualTo(String value) {
            addCriterion("resource_name >=", value, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameLessThan(String value) {
            addCriterion("resource_name <", value, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameLessThanOrEqualTo(String value) {
            addCriterion("resource_name <=", value, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameLike(String value) {
            addCriterion("resource_name like", value, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameNotLike(String value) {
            addCriterion("resource_name not like", value, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameIn(List<String> values) {
            addCriterion("resource_name in", values, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameNotIn(List<String> values) {
            addCriterion("resource_name not in", values, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameBetween(String value1, String value2) {
            addCriterion("resource_name between", value1, value2, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameNotBetween(String value1, String value2) {
            addCriterion("resource_name not between", value1, value2, "resourceName");
            return (Criteria) this;
        }

        public Criteria andChargeRuleIdIsNull() {
            addCriterion("charge_rule_id is null");
            return (Criteria) this;
        }

        public Criteria andChargeRuleIdIsNotNull() {
            addCriterion("charge_rule_id is not null");
            return (Criteria) this;
        }

        public Criteria andChargeRuleIdEqualTo(Integer value) {
            addCriterion("charge_rule_id =", value, "chargeRuleId");
            return (Criteria) this;
        }

        public Criteria andChargeRuleIdNotEqualTo(Integer value) {
            addCriterion("charge_rule_id <>", value, "chargeRuleId");
            return (Criteria) this;
        }

        public Criteria andChargeRuleIdGreaterThan(Integer value) {
            addCriterion("charge_rule_id >", value, "chargeRuleId");
            return (Criteria) this;
        }

        public Criteria andChargeRuleIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("charge_rule_id >=", value, "chargeRuleId");
            return (Criteria) this;
        }

        public Criteria andChargeRuleIdLessThan(Integer value) {
            addCriterion("charge_rule_id <", value, "chargeRuleId");
            return (Criteria) this;
        }

        public Criteria andChargeRuleIdLessThanOrEqualTo(Integer value) {
            addCriterion("charge_rule_id <=", value, "chargeRuleId");
            return (Criteria) this;
        }

        public Criteria andChargeRuleIdIn(List<Integer> values) {
            addCriterion("charge_rule_id in", values, "chargeRuleId");
            return (Criteria) this;
        }

        public Criteria andChargeRuleIdNotIn(List<Integer> values) {
            addCriterion("charge_rule_id not in", values, "chargeRuleId");
            return (Criteria) this;
        }

        public Criteria andChargeRuleIdBetween(Integer value1, Integer value2) {
            addCriterion("charge_rule_id between", value1, value2, "chargeRuleId");
            return (Criteria) this;
        }

        public Criteria andChargeRuleIdNotBetween(Integer value1, Integer value2) {
            addCriterion("charge_rule_id not between", value1, value2, "chargeRuleId");
            return (Criteria) this;
        }

        public Criteria andChargeRuleNameIsNull() {
            addCriterion("charge_rule_name is null");
            return (Criteria) this;
        }

        public Criteria andChargeRuleNameIsNotNull() {
            addCriterion("charge_rule_name is not null");
            return (Criteria) this;
        }

        public Criteria andChargeRuleNameEqualTo(String value) {
            addCriterion("charge_rule_name =", value, "chargeRuleName");
            return (Criteria) this;
        }

        public Criteria andChargeRuleNameNotEqualTo(String value) {
            addCriterion("charge_rule_name <>", value, "chargeRuleName");
            return (Criteria) this;
        }

        public Criteria andChargeRuleNameGreaterThan(String value) {
            addCriterion("charge_rule_name >", value, "chargeRuleName");
            return (Criteria) this;
        }

        public Criteria andChargeRuleNameGreaterThanOrEqualTo(String value) {
            addCriterion("charge_rule_name >=", value, "chargeRuleName");
            return (Criteria) this;
        }

        public Criteria andChargeRuleNameLessThan(String value) {
            addCriterion("charge_rule_name <", value, "chargeRuleName");
            return (Criteria) this;
        }

        public Criteria andChargeRuleNameLessThanOrEqualTo(String value) {
            addCriterion("charge_rule_name <=", value, "chargeRuleName");
            return (Criteria) this;
        }

        public Criteria andChargeRuleNameLike(String value) {
            addCriterion("charge_rule_name like", value, "chargeRuleName");
            return (Criteria) this;
        }

        public Criteria andChargeRuleNameNotLike(String value) {
            addCriterion("charge_rule_name not like", value, "chargeRuleName");
            return (Criteria) this;
        }

        public Criteria andChargeRuleNameIn(List<String> values) {
            addCriterion("charge_rule_name in", values, "chargeRuleName");
            return (Criteria) this;
        }

        public Criteria andChargeRuleNameNotIn(List<String> values) {
            addCriterion("charge_rule_name not in", values, "chargeRuleName");
            return (Criteria) this;
        }

        public Criteria andChargeRuleNameBetween(String value1, String value2) {
            addCriterion("charge_rule_name between", value1, value2, "chargeRuleName");
            return (Criteria) this;
        }

        public Criteria andChargeRuleNameNotBetween(String value1, String value2) {
            addCriterion("charge_rule_name not between", value1, value2, "chargeRuleName");
            return (Criteria) this;
        }

        public Criteria andChargeRuleDesIsNull() {
            addCriterion("charge_rule_des is null");
            return (Criteria) this;
        }

        public Criteria andChargeRuleDesIsNotNull() {
            addCriterion("charge_rule_des is not null");
            return (Criteria) this;
        }

        public Criteria andChargeRuleDesEqualTo(String value) {
            addCriterion("charge_rule_des =", value, "chargeRuleDes");
            return (Criteria) this;
        }

        public Criteria andChargeRuleDesNotEqualTo(String value) {
            addCriterion("charge_rule_des <>", value, "chargeRuleDes");
            return (Criteria) this;
        }

        public Criteria andChargeRuleDesGreaterThan(String value) {
            addCriterion("charge_rule_des >", value, "chargeRuleDes");
            return (Criteria) this;
        }

        public Criteria andChargeRuleDesGreaterThanOrEqualTo(String value) {
            addCriterion("charge_rule_des >=", value, "chargeRuleDes");
            return (Criteria) this;
        }

        public Criteria andChargeRuleDesLessThan(String value) {
            addCriterion("charge_rule_des <", value, "chargeRuleDes");
            return (Criteria) this;
        }

        public Criteria andChargeRuleDesLessThanOrEqualTo(String value) {
            addCriterion("charge_rule_des <=", value, "chargeRuleDes");
            return (Criteria) this;
        }

        public Criteria andChargeRuleDesLike(String value) {
            addCriterion("charge_rule_des like", value, "chargeRuleDes");
            return (Criteria) this;
        }

        public Criteria andChargeRuleDesNotLike(String value) {
            addCriterion("charge_rule_des not like", value, "chargeRuleDes");
            return (Criteria) this;
        }

        public Criteria andChargeRuleDesIn(List<String> values) {
            addCriterion("charge_rule_des in", values, "chargeRuleDes");
            return (Criteria) this;
        }

        public Criteria andChargeRuleDesNotIn(List<String> values) {
            addCriterion("charge_rule_des not in", values, "chargeRuleDes");
            return (Criteria) this;
        }

        public Criteria andChargeRuleDesBetween(String value1, String value2) {
            addCriterion("charge_rule_des between", value1, value2, "chargeRuleDes");
            return (Criteria) this;
        }

        public Criteria andChargeRuleDesNotBetween(String value1, String value2) {
            addCriterion("charge_rule_des not between", value1, value2, "chargeRuleDes");
            return (Criteria) this;
        }

        public Criteria andChargeRuleTypeIsNull() {
            addCriterion("charge_rule_type is null");
            return (Criteria) this;
        }

        public Criteria andChargeRuleTypeIsNotNull() {
            addCriterion("charge_rule_type is not null");
            return (Criteria) this;
        }

        public Criteria andChargeRuleTypeEqualTo(Integer value) {
            addCriterion("charge_rule_type =", value, "chargeRuleType");
            return (Criteria) this;
        }

        public Criteria andChargeRuleTypeNotEqualTo(Integer value) {
            addCriterion("charge_rule_type <>", value, "chargeRuleType");
            return (Criteria) this;
        }

        public Criteria andChargeRuleTypeGreaterThan(Integer value) {
            addCriterion("charge_rule_type >", value, "chargeRuleType");
            return (Criteria) this;
        }

        public Criteria andChargeRuleTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("charge_rule_type >=", value, "chargeRuleType");
            return (Criteria) this;
        }

        public Criteria andChargeRuleTypeLessThan(Integer value) {
            addCriterion("charge_rule_type <", value, "chargeRuleType");
            return (Criteria) this;
        }

        public Criteria andChargeRuleTypeLessThanOrEqualTo(Integer value) {
            addCriterion("charge_rule_type <=", value, "chargeRuleType");
            return (Criteria) this;
        }

        public Criteria andChargeRuleTypeIn(List<Integer> values) {
            addCriterion("charge_rule_type in", values, "chargeRuleType");
            return (Criteria) this;
        }

        public Criteria andChargeRuleTypeNotIn(List<Integer> values) {
            addCriterion("charge_rule_type not in", values, "chargeRuleType");
            return (Criteria) this;
        }

        public Criteria andChargeRuleTypeBetween(Integer value1, Integer value2) {
            addCriterion("charge_rule_type between", value1, value2, "chargeRuleType");
            return (Criteria) this;
        }

        public Criteria andChargeRuleTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("charge_rule_type not between", value1, value2, "chargeRuleType");
            return (Criteria) this;
        }

        public Criteria andItemCashIsNull() {
            addCriterion("item_cash is null");
            return (Criteria) this;
        }

        public Criteria andItemCashIsNotNull() {
            addCriterion("item_cash is not null");
            return (Criteria) this;
        }

        public Criteria andItemCashEqualTo(Double value) {
            addCriterion("item_cash =", value, "itemCash");
            return (Criteria) this;
        }

        public Criteria andItemCashNotEqualTo(Double value) {
            addCriterion("item_cash <>", value, "itemCash");
            return (Criteria) this;
        }

        public Criteria andItemCashGreaterThan(Double value) {
            addCriterion("item_cash >", value, "itemCash");
            return (Criteria) this;
        }

        public Criteria andItemCashGreaterThanOrEqualTo(Double value) {
            addCriterion("item_cash >=", value, "itemCash");
            return (Criteria) this;
        }

        public Criteria andItemCashLessThan(Double value) {
            addCriterion("item_cash <", value, "itemCash");
            return (Criteria) this;
        }

        public Criteria andItemCashLessThanOrEqualTo(Double value) {
            addCriterion("item_cash <=", value, "itemCash");
            return (Criteria) this;
        }

        public Criteria andItemCashIn(List<Double> values) {
            addCriterion("item_cash in", values, "itemCash");
            return (Criteria) this;
        }

        public Criteria andItemCashNotIn(List<Double> values) {
            addCriterion("item_cash not in", values, "itemCash");
            return (Criteria) this;
        }

        public Criteria andItemCashBetween(Double value1, Double value2) {
            addCriterion("item_cash between", value1, value2, "itemCash");
            return (Criteria) this;
        }

        public Criteria andItemCashNotBetween(Double value1, Double value2) {
            addCriterion("item_cash not between", value1, value2, "itemCash");
            return (Criteria) this;
        }

        public Criteria andItemNumberIsNull() {
            addCriterion("item_number is null");
            return (Criteria) this;
        }

        public Criteria andItemNumberIsNotNull() {
            addCriterion("item_number is not null");
            return (Criteria) this;
        }

        public Criteria andItemNumberEqualTo(Integer value) {
            addCriterion("item_number =", value, "itemNumber");
            return (Criteria) this;
        }

        public Criteria andItemNumberNotEqualTo(Integer value) {
            addCriterion("item_number <>", value, "itemNumber");
            return (Criteria) this;
        }

        public Criteria andItemNumberGreaterThan(Integer value) {
            addCriterion("item_number >", value, "itemNumber");
            return (Criteria) this;
        }

        public Criteria andItemNumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("item_number >=", value, "itemNumber");
            return (Criteria) this;
        }

        public Criteria andItemNumberLessThan(Integer value) {
            addCriterion("item_number <", value, "itemNumber");
            return (Criteria) this;
        }

        public Criteria andItemNumberLessThanOrEqualTo(Integer value) {
            addCriterion("item_number <=", value, "itemNumber");
            return (Criteria) this;
        }

        public Criteria andItemNumberIn(List<Integer> values) {
            addCriterion("item_number in", values, "itemNumber");
            return (Criteria) this;
        }

        public Criteria andItemNumberNotIn(List<Integer> values) {
            addCriterion("item_number not in", values, "itemNumber");
            return (Criteria) this;
        }

        public Criteria andItemNumberBetween(Integer value1, Integer value2) {
            addCriterion("item_number between", value1, value2, "itemNumber");
            return (Criteria) this;
        }

        public Criteria andItemNumberNotBetween(Integer value1, Integer value2) {
            addCriterion("item_number not between", value1, value2, "itemNumber");
            return (Criteria) this;
        }

        public Criteria andItemCashTotalIsNull() {
            addCriterion("item_cash_total is null");
            return (Criteria) this;
        }

        public Criteria andItemCashTotalIsNotNull() {
            addCriterion("item_cash_total is not null");
            return (Criteria) this;
        }

        public Criteria andItemCashTotalEqualTo(Double value) {
            addCriterion("item_cash_total =", value, "itemCashTotal");
            return (Criteria) this;
        }

        public Criteria andItemCashTotalNotEqualTo(Double value) {
            addCriterion("item_cash_total <>", value, "itemCashTotal");
            return (Criteria) this;
        }

        public Criteria andItemCashTotalGreaterThan(Double value) {
            addCriterion("item_cash_total >", value, "itemCashTotal");
            return (Criteria) this;
        }

        public Criteria andItemCashTotalGreaterThanOrEqualTo(Double value) {
            addCriterion("item_cash_total >=", value, "itemCashTotal");
            return (Criteria) this;
        }

        public Criteria andItemCashTotalLessThan(Double value) {
            addCriterion("item_cash_total <", value, "itemCashTotal");
            return (Criteria) this;
        }

        public Criteria andItemCashTotalLessThanOrEqualTo(Double value) {
            addCriterion("item_cash_total <=", value, "itemCashTotal");
            return (Criteria) this;
        }

        public Criteria andItemCashTotalIn(List<Double> values) {
            addCriterion("item_cash_total in", values, "itemCashTotal");
            return (Criteria) this;
        }

        public Criteria andItemCashTotalNotIn(List<Double> values) {
            addCriterion("item_cash_total not in", values, "itemCashTotal");
            return (Criteria) this;
        }

        public Criteria andItemCashTotalBetween(Double value1, Double value2) {
            addCriterion("item_cash_total between", value1, value2, "itemCashTotal");
            return (Criteria) this;
        }

        public Criteria andItemCashTotalNotBetween(Double value1, Double value2) {
            addCriterion("item_cash_total not between", value1, value2, "itemCashTotal");
            return (Criteria) this;
        }

        public Criteria andAreaIsNull() {
            addCriterion("area is null");
            return (Criteria) this;
        }

        public Criteria andAreaIsNotNull() {
            addCriterion("area is not null");
            return (Criteria) this;
        }

        public Criteria andAreaEqualTo(String value) {
            addCriterion("area =", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotEqualTo(String value) {
            addCriterion("area <>", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThan(String value) {
            addCriterion("area >", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThanOrEqualTo(String value) {
            addCriterion("area >=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThan(String value) {
            addCriterion("area <", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThanOrEqualTo(String value) {
            addCriterion("area <=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLike(String value) {
            addCriterion("area like", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotLike(String value) {
            addCriterion("area not like", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaIn(List<String> values) {
            addCriterion("area in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotIn(List<String> values) {
            addCriterion("area not in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaBetween(String value1, String value2) {
            addCriterion("area between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotBetween(String value1, String value2) {
            addCriterion("area not between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeIsNull() {
            addCriterion("created_time is null");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeIsNotNull() {
            addCriterion("created_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeEqualTo(Date value) {
            addCriterion("created_time =", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeNotEqualTo(Date value) {
            addCriterion("created_time <>", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeGreaterThan(Date value) {
            addCriterion("created_time >", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("created_time >=", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeLessThan(Date value) {
            addCriterion("created_time <", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeLessThanOrEqualTo(Date value) {
            addCriterion("created_time <=", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeIn(List<Date> values) {
            addCriterion("created_time in", values, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeNotIn(List<Date> values) {
            addCriterion("created_time not in", values, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeBetween(Date value1, Date value2) {
            addCriterion("created_time between", value1, value2, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeNotBetween(Date value1, Date value2) {
            addCriterion("created_time not between", value1, value2, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedByIsNull() {
            addCriterion("created_by is null");
            return (Criteria) this;
        }

        public Criteria andCreatedByIsNotNull() {
            addCriterion("created_by is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedByEqualTo(String value) {
            addCriterion("created_by =", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotEqualTo(String value) {
            addCriterion("created_by <>", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByGreaterThan(String value) {
            addCriterion("created_by >", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByGreaterThanOrEqualTo(String value) {
            addCriterion("created_by >=", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByLessThan(String value) {
            addCriterion("created_by <", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByLessThanOrEqualTo(String value) {
            addCriterion("created_by <=", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByLike(String value) {
            addCriterion("created_by like", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotLike(String value) {
            addCriterion("created_by not like", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByIn(List<String> values) {
            addCriterion("created_by in", values, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotIn(List<String> values) {
            addCriterion("created_by not in", values, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByBetween(String value1, String value2) {
            addCriterion("created_by between", value1, value2, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotBetween(String value1, String value2) {
            addCriterion("created_by not between", value1, value2, "createdBy");
            return (Criteria) this;
        }

        public Criteria andUdpatedTimeIsNull() {
            addCriterion("udpated_time is null");
            return (Criteria) this;
        }

        public Criteria andUdpatedTimeIsNotNull() {
            addCriterion("udpated_time is not null");
            return (Criteria) this;
        }

        public Criteria andUdpatedTimeEqualTo(Date value) {
            addCriterion("udpated_time =", value, "udpatedTime");
            return (Criteria) this;
        }

        public Criteria andUdpatedTimeNotEqualTo(Date value) {
            addCriterion("udpated_time <>", value, "udpatedTime");
            return (Criteria) this;
        }

        public Criteria andUdpatedTimeGreaterThan(Date value) {
            addCriterion("udpated_time >", value, "udpatedTime");
            return (Criteria) this;
        }

        public Criteria andUdpatedTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("udpated_time >=", value, "udpatedTime");
            return (Criteria) this;
        }

        public Criteria andUdpatedTimeLessThan(Date value) {
            addCriterion("udpated_time <", value, "udpatedTime");
            return (Criteria) this;
        }

        public Criteria andUdpatedTimeLessThanOrEqualTo(Date value) {
            addCriterion("udpated_time <=", value, "udpatedTime");
            return (Criteria) this;
        }

        public Criteria andUdpatedTimeIn(List<Date> values) {
            addCriterion("udpated_time in", values, "udpatedTime");
            return (Criteria) this;
        }

        public Criteria andUdpatedTimeNotIn(List<Date> values) {
            addCriterion("udpated_time not in", values, "udpatedTime");
            return (Criteria) this;
        }

        public Criteria andUdpatedTimeBetween(Date value1, Date value2) {
            addCriterion("udpated_time between", value1, value2, "udpatedTime");
            return (Criteria) this;
        }

        public Criteria andUdpatedTimeNotBetween(Date value1, Date value2) {
            addCriterion("udpated_time not between", value1, value2, "udpatedTime");
            return (Criteria) this;
        }

        public Criteria andUpdatedByIsNull() {
            addCriterion("updated_by is null");
            return (Criteria) this;
        }

        public Criteria andUpdatedByIsNotNull() {
            addCriterion("updated_by is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatedByEqualTo(String value) {
            addCriterion("updated_by =", value, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedByNotEqualTo(String value) {
            addCriterion("updated_by <>", value, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedByGreaterThan(String value) {
            addCriterion("updated_by >", value, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedByGreaterThanOrEqualTo(String value) {
            addCriterion("updated_by >=", value, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedByLessThan(String value) {
            addCriterion("updated_by <", value, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedByLessThanOrEqualTo(String value) {
            addCriterion("updated_by <=", value, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedByLike(String value) {
            addCriterion("updated_by like", value, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedByNotLike(String value) {
            addCriterion("updated_by not like", value, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedByIn(List<String> values) {
            addCriterion("updated_by in", values, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedByNotIn(List<String> values) {
            addCriterion("updated_by not in", values, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedByBetween(String value1, String value2) {
            addCriterion("updated_by between", value1, value2, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedByNotBetween(String value1, String value2) {
            addCriterion("updated_by not between", value1, value2, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andDefaulTimeIsNull() {
            addCriterion("defaul_time is null");
            return (Criteria) this;
        }

        public Criteria andDefaulTimeIsNotNull() {
            addCriterion("defaul_time is not null");
            return (Criteria) this;
        }

        public Criteria andDefaulTimeEqualTo(Integer value) {
            addCriterion("defaul_time =", value, "defaulTime");
            return (Criteria) this;
        }

        public Criteria andDefaulTimeNotEqualTo(Integer value) {
            addCriterion("defaul_time <>", value, "defaulTime");
            return (Criteria) this;
        }

        public Criteria andDefaulTimeGreaterThan(Integer value) {
            addCriterion("defaul_time >", value, "defaulTime");
            return (Criteria) this;
        }

        public Criteria andDefaulTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("defaul_time >=", value, "defaulTime");
            return (Criteria) this;
        }

        public Criteria andDefaulTimeLessThan(Integer value) {
            addCriterion("defaul_time <", value, "defaulTime");
            return (Criteria) this;
        }

        public Criteria andDefaulTimeLessThanOrEqualTo(Integer value) {
            addCriterion("defaul_time <=", value, "defaulTime");
            return (Criteria) this;
        }

        public Criteria andDefaulTimeIn(List<Integer> values) {
            addCriterion("defaul_time in", values, "defaulTime");
            return (Criteria) this;
        }

        public Criteria andDefaulTimeNotIn(List<Integer> values) {
            addCriterion("defaul_time not in", values, "defaulTime");
            return (Criteria) this;
        }

        public Criteria andDefaulTimeBetween(Integer value1, Integer value2) {
            addCriterion("defaul_time between", value1, value2, "defaulTime");
            return (Criteria) this;
        }

        public Criteria andDefaulTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("defaul_time not between", value1, value2, "defaulTime");
            return (Criteria) this;
        }

        public Criteria andResourceDelayTimeIsNull() {
            addCriterion("resource_delay_time is null");
            return (Criteria) this;
        }

        public Criteria andResourceDelayTimeIsNotNull() {
            addCriterion("resource_delay_time is not null");
            return (Criteria) this;
        }

        public Criteria andResourceDelayTimeEqualTo(Integer value) {
            addCriterion("resource_delay_time =", value, "resourceDelayTime");
            return (Criteria) this;
        }

        public Criteria andResourceDelayTimeNotEqualTo(Integer value) {
            addCriterion("resource_delay_time <>", value, "resourceDelayTime");
            return (Criteria) this;
        }

        public Criteria andResourceDelayTimeGreaterThan(Integer value) {
            addCriterion("resource_delay_time >", value, "resourceDelayTime");
            return (Criteria) this;
        }

        public Criteria andResourceDelayTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("resource_delay_time >=", value, "resourceDelayTime");
            return (Criteria) this;
        }

        public Criteria andResourceDelayTimeLessThan(Integer value) {
            addCriterion("resource_delay_time <", value, "resourceDelayTime");
            return (Criteria) this;
        }

        public Criteria andResourceDelayTimeLessThanOrEqualTo(Integer value) {
            addCriterion("resource_delay_time <=", value, "resourceDelayTime");
            return (Criteria) this;
        }

        public Criteria andResourceDelayTimeIn(List<Integer> values) {
            addCriterion("resource_delay_time in", values, "resourceDelayTime");
            return (Criteria) this;
        }

        public Criteria andResourceDelayTimeNotIn(List<Integer> values) {
            addCriterion("resource_delay_time not in", values, "resourceDelayTime");
            return (Criteria) this;
        }

        public Criteria andResourceDelayTimeBetween(Integer value1, Integer value2) {
            addCriterion("resource_delay_time between", value1, value2, "resourceDelayTime");
            return (Criteria) this;
        }

        public Criteria andResourceDelayTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("resource_delay_time not between", value1, value2, "resourceDelayTime");
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