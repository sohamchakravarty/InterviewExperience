package com.interview.legacy.support;

import com.interview.legacy.domain.OrderItem;
import com.interview.legacy.domain.OrderRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NaiveJsonParser {
    public OrderRequest parseOrderRequest(String body) {
        String customerId = readString(body, "customerId");
        String externalId = readString(body, "externalId");
        String currency = defaultIfBlank(readString(body, "currency"), "USD");
        String promoCode = readString(body, "promoCode");
        boolean expedite = readBoolean(body, "expedite");
        List<OrderItem> items = readItems(body);

        return new OrderRequest(customerId, externalId, currency, expedite, promoCode, items, body);
    }

    private String readString(String body, String field) {
        Pattern pattern = Pattern.compile("\"" + Pattern.quote(field) + "\"\\s*:\\s*\"([^\"]*)\"");
        Matcher matcher = pattern.matcher(body);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    private boolean readBoolean(String body, String field) {
        Pattern pattern = Pattern.compile("\"" + Pattern.quote(field) + "\"\\s*:\\s*(true|false)");
        Matcher matcher = pattern.matcher(body);
        return matcher.find() && Boolean.parseBoolean(matcher.group(1));
    }

    private List<OrderItem> readItems(String body) {
        List<OrderItem> items = new ArrayList<>();
        Pattern arrayPattern = Pattern.compile("\"items\"\\s*:\\s*\\[(.*)]", Pattern.DOTALL);
        Matcher arrayMatcher = arrayPattern.matcher(body);
        if (!arrayMatcher.find()) {
            return items;
        }

        String itemBlock = arrayMatcher.group(1);
        Pattern itemPattern = Pattern.compile("\\{([^}]*)}");
        Matcher itemMatcher = itemPattern.matcher(itemBlock);
        while (itemMatcher.find()) {
            String itemPayload = itemMatcher.group(1);
            String sku = readString("{" + itemPayload + "}", "sku");
            int quantity = readInt("{" + itemPayload + "}", "quantity");
            double unitPrice = readDouble("{" + itemPayload + "}", "unitPrice");
            items.add(new OrderItem(sku, quantity, unitPrice));
        }
        return items;
    }

    private int readInt(String body, String field) {
        Pattern pattern = Pattern.compile("\"" + Pattern.quote(field) + "\"\\s*:\\s*(-?\\d+)");
        Matcher matcher = pattern.matcher(body);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return 0;
    }

    private double readDouble(String body, String field) {
        Pattern pattern = Pattern.compile("\"" + Pattern.quote(field) + "\"\\s*:\\s*(-?\\d+(\\.\\d+)?)");
        Matcher matcher = pattern.matcher(body);
        if (matcher.find()) {
            return Double.parseDouble(matcher.group(1));
        }
        return 0.0;
    }

    private String defaultIfBlank(String value, String fallback) {
        if (value == null || value.isBlank()) {
            return fallback;
        }
        return value;
    }
}
