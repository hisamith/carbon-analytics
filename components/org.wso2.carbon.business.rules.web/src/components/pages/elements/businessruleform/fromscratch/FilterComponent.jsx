/*
 *  Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

import React from 'react';
// Material UI Components
import TextField from 'material-ui/TextField';
import Typography from 'material-ui/Typography';
import Collapse from 'material-ui/transitions/Collapse';
import AppBar from 'material-ui/AppBar';
import Toolbar from 'material-ui/Toolbar';
import ExpandMoreIcon from 'material-ui-icons/ExpandMore';
import AddIcon from 'material-ui-icons/Add';
import { IconButton } from 'material-ui';
import Paper from 'material-ui/Paper';
// App Components
import Property from '../Property';
import FilterRule from './FilterRule';
// App Utilities
import BusinessRulesUtilityFunctions from '../../../../../utils/BusinessRulesUtilityFunctions';
// App Constants
import BusinessRulesConstants from '../../../../../constants/BusinessRulesConstants';
import BusinessRulesMessages from '../../../../../constants/BusinessRulesMessages';
// CSS
import '../../../../../index.css';

/**
 * Represents the filter component of business rules from scratch form, which contains filter rules, rule logic and
 * a button for adding filter rule
 */
class FilterComponent extends React.Component {
    addFilterRule() {
        let filterRules = this.props.ruleComponents.filterRules;
        filterRules.push(['', '', '']);
        const ruleLogic = this.autoGenerateRuleLogic(filterRules, this.props.ruleComponents.ruleLogic);
        this.updateRuleComponents(filterRules, ruleLogic);
    }

    autoGenerateRuleLogic(filterRules, ruleLogic) {
        if (filterRules.length === 0) {
            return '';
        }
        if (ruleLogic !== '') {
            // To avoid cases like '1 AND 2 AND 2', where 2 was deleted and inserted again
            if (!this.getExistingFilterRuleNumbers(ruleLogic).includes(filterRules.length.toString())) {
                return ruleLogic + ' AND ' + filterRules.length;
            }
            return ruleLogic;
        }
        // No rule logic is present
        // Concatenate each filter rule numbers with AND and return
        const numbers = [];
        for (let i = 0; i < filterRules.length; i++) {
            numbers.push(i + 1);
        }
        return numbers.join(' AND ');
    }

    getExistingFilterRuleNumbers(ruleLogic) {
        const existingFilterRuleNumbers = [];
        const regExp = /(\d+)/gm;
        let matches;
        while ((matches = regExp.exec(ruleLogic)) !== null) {
            if (matches.index === regExp.lastIndex) {
                regExp.lastIndex++;
            }
            existingFilterRuleNumbers.push(matches[1]);
        }
        return existingFilterRuleNumbers;
    }

    updateFilterRule(index, filterRule) {
        const filterRules = this.props.ruleComponents.filterRules; // TODO 'ruleComponents' should be a prop
        filterRules[index] = filterRule;
        this.updateRuleComponents(filterRules, this.props.ruleComponents.ruleLogic);
    }

    deleteFilterRule(index) {
        let filterRules = this.props.ruleComponents.filterRules;
        filterRules.splice(index, 1);
        this.updateRuleComponents(filterRules, this.props.ruleComponents.ruleLogic);
    }

    updateRuleLogic(ruleLogic) {
        this.updateRuleComponents(this.props.ruleComponents.filterRules, ruleLogic);
    }

    updateRuleComponents(filterRules, ruleLogic) { // Updates Filter component
        const ruleComponents = this.props.ruleComponents;
        ruleComponents.filterRules = filterRules;
        ruleComponents.ruleLogic = ruleLogic;
        this.props.onUpdate(ruleComponents);
    }

    displayFilterRulesTable() {
        if (this.props.ruleComponents.filterRules.length > 0) {
            let exposedInputStreamFields = null; // To display selectable field options to each filter rule

            // If an input rule template has been selected
            if (!BusinessRulesUtilityFunctions.isEmpty(this.props.selectedInputRuleTemplate)) {
                exposedInputStreamFields = this.props.getFields( // TODO i stopped here. this's not found
                    this.props.selectedInputRuleTemplate.templates[0].exposedStreamDefinition);
            }

            return (
                <div style={{ width: '100%', overflowX: 'auto' }}>
                    <div style={{ width: '100%', minWidth: 560 }}>
                        <div style={{ float: 'left', width: '10%', height: 30 }}>
                            <Typography />
                        </div>
                        <div style={{ float: 'left', width: '30%', height: 30 }}>
                            <Typography type="caption">
                                Attribute
                            </Typography>
                        </div>
                        <div style={{ float: 'left', width: '20%', height: 30 }}>
                            <center>
                                <Typography type="caption">
                                    Operator
                                </Typography>
                            </center>
                        </div>
                        <div style={{ float: 'left', width: '30%', height: 30 }}>
                            <Typography type="caption">
                                Value/Attribute
                            </Typography>
                        </div>
                        <div style={{ float: 'left', width: '10%', height: 30 }}>
                            <Typography />
                        </div>
                        {this.props.ruleComponents[BusinessRulesConstants.FILTER_RULES_KEY].map((filterRule, index) =>
                            (<FilterRule
                                key={index}
                                mode={this.props.formMode} // TODO refactor to 'disable', conditionally
                                filterRuleIndex={index}
                                filterRule={filterRule}
                                exposedStreamDefinition={
                                    this.props.selectedInputRuleTemplate.templates[0].exposedStreamDefinition}
                                getFieldNames={streamDefinition => this.props.getFieldNames(streamDefinition)}
                                exposedInputStreamFields={exposedInputStreamFields}
                                onUpdate={value => this.updateFilterRule(index, value)}
                                onRemove={() => this.deleteFilterRule(index)}
                            />))}
                    </div>
                </div>);
        }
        return null;
    }

    displayAddFilterButton() {
        if (this.props.formMode !== BusinessRulesConstants.BUSINESS_RULE_FORM_MODE_VIEW) {
            return (
                <IconButton
                    color="primary"
                    style={this.props.style.addFilterRuleButton}
                    aria-label="Remove"
                    onClick={() => this.addFilterRule()}
                >
                    <AddIcon />
                </IconButton>);
        }
        return null;
    }

    displayRuleLogic() {
        if (this.props.ruleComponents.filterRules.length > 0) {
            return (
                <TextField
                    id="ruleLogic"
                    name="ruleLogic"
                    label="Rule Logic"
                    helperText={
                        (!this.props.ruleLogicWarn) ?
                            BusinessRulesMessages.RULE_LOGIC_HELPER_TEXT :
                            (BusinessRulesMessages.RULE_LOGIC_WARNING +
                                '. ' + BusinessRulesMessages.RULE_LOGIC_HELPER_TEXT)}
                    value={this.props.ruleComponents.ruleLogic}
                    onChange={e => this.updateRuleLogic(e.target.value)}
                    error={this.props.ruleLogicWarn} // TODO might improve this
                    disabled={this.props.formMode === BusinessRulesConstants.BUSINESS_RULE_FORM_MODE_VIEW}
                    fullWidth
                />);
        }
        return null;
    }

    displayExpandButton() {
        if (!BusinessRulesUtilityFunctions.isEmpty(this.props.selectedInputRuleTemplate)) {
            return (
                <IconButton onClick={() => this.props.toggleExpansion()}>
                    <ExpandMoreIcon />
                </IconButton>);
        }
        return null;
    }

    render() {
        return (
            <div>
                <AppBar position="static" color="default">
                    <Toolbar>
                        <Typography type="subheading">Filters</Typography>
                        {this.displayExpandButton()}
                    </Toolbar>
                </AppBar>
                <Paper>
                    <Collapse in={this.props.isExpanded} transitionDuration="auto" unmountOnExit>
                        <div style={this.props.style.paperContainer}>
                            <br />
                            {this.displayFilterRulesTable()}
                            <br />
                            {this.displayAddFilterButton()}
                            <br />
                            <br />
                            {this.displayRuleLogic()}
                            <br />
                        </div>
                    </Collapse>
                </Paper>
            </div>);
    }
}

export default FilterComponent;
