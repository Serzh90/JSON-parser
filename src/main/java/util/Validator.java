package util;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.sun.istack.internal.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Validator {
    private final File config;
    private static List<RuleSet> ruleSet;

    public Validator(@NotNull File config) {
        this.config = config;
    }

    public Validator init() throws IOException {
        List<String> validationConfig = Files.readLines(config, Charsets.UTF_8);
        ruleSet = validationConfig.stream().map(line -> loadRuleSet(line)).collect(Collectors.toList());
        return this;
    }

    private RuleSet loadRuleSet(String rule) {
        String endExp = rule.substring(rule.indexOf("THEN"), rule.length());
        String mainExp = rule.replace(endExp, "");
        String result = endExp.replace("THEN", "")
                .replaceAll("\\s+", "");
        List<String> contains = null;
        if (rule.contains("CONTAINS")) {
            contains = Arrays.asList(mainExp.substring(mainExp.indexOf("(") + 1, mainExp.indexOf(")")).split(","));
            mainExp = mainExp.replace(mainExp.substring(mainExp.indexOf("CONTAINS") - 1, mainExp.indexOf(")") + 1), "");
        }
        RuleSet ruleSet = new RuleSet(mainExp, contains, result);
        return ruleSet;
    }

    public RecordResult validate(Record rec) {
        for (RuleSet rule : ruleSet) {
            if (rule.match(rec)) {
                return new RecordResult(rec.getId(), rule.getResult());
            }
        }
        return new RecordResult(rec.getId(), "green");  //default
    }

    public List<RuleSet> getRuleSet() {
        return ruleSet;
    }

}
