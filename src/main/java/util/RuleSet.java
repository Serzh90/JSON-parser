package util;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.List;
import java.util.stream.Collectors;

public class RuleSet {
    private final String mainExpression;
    private final List<String> contains;
    private String result;

    public RuleSet(@NotNull String mainExpression, @Nullable List<String> contains, @NotNull String result) {
        this.mainExpression = mainExpression;
        this.contains = contains;
        this.result = result;
    }

    public boolean match(Record rec){
        String currentExpression = mainExpression;
        ExpressionParser parser = new SpelExpressionParser();

        if(mainExpression.contains("$price")) {
            currentExpression = currentExpression.replace("$price", String.valueOf(rec.getPrice()))
            .replace(" = ", " == ");
        }

        if(mainExpression.contains("$description") && contains != null) {
            List<String> containsExp = contains.stream().map(con -> "'"
                    + rec.getDescription()
                    + "'"
                    + ".contains('"
                    + con.replaceAll("\\s+", "")
                    + "')")
                    .collect(Collectors.toList());

            String descriptionCheck = String.join(" AND ", containsExp);
            currentExpression = currentExpression.replace("$description", descriptionCheck);
        }
        return parser.parseExpression(currentExpression).getValue(Boolean.class);
    }

    public String getResult() {
        return result;
    }

    public List<String> getContains() {
        return contains;
    }
}
