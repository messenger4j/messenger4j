package com.github.messenger4j.send.message.template;

import static java.util.Optional.empty;

import com.github.messenger4j.internal.Lists;
import com.github.messenger4j.internal.gson.OptionalInstantToSecondsStringSerializer;
import com.github.messenger4j.send.message.template.receipt.Address;
import com.github.messenger4j.send.message.template.receipt.Adjustment;
import com.github.messenger4j.send.message.template.receipt.Item;
import com.github.messenger4j.send.message.template.receipt.Summary;
import com.google.gson.annotations.JsonAdapter;
import java.net.URL;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class ReceiptTemplate extends Template {

    private final String recipientName;
    private final String orderNumber;
    private final String paymentMethod;
    private final String currency;
    private final Summary summary;
    private final Optional<Address> address;
    private final Optional<List<Item>> elements;
    private final Optional<List<Adjustment>> adjustments;
    private final Optional<String> merchantName;
    private final Optional<URL> orderUrl;
    private final Optional<Boolean> sharable;
    @JsonAdapter(OptionalInstantToSecondsStringSerializer.class)
    private final Optional<Instant> timestamp;

    public static ReceiptTemplate create(@NonNull String recipientName,
                                         @NonNull String orderNumber,
                                         @NonNull String paymentMethod,
                                         @NonNull String currency,
                                         @NonNull Summary summary) {
        return create(recipientName, orderNumber, paymentMethod, currency, summary, empty(), empty(), empty(), empty(),
                empty(), empty(), empty());
    }

    public static ReceiptTemplate create(@NonNull String recipientName,
                                         @NonNull String orderNumber,
                                         @NonNull String paymentMethod,
                                         @NonNull String currency,
                                         @NonNull Summary summary,
                                         @NonNull Optional<Address> address,
                                         @NonNull Optional<List<Item>> elements,
                                         @NonNull Optional<List<Adjustment>> adjustments,
                                         @NonNull Optional<String> merchantName,
                                         @NonNull Optional<URL> orderUrl,
                                         @NonNull Optional<Boolean> sharable,
                                         @NonNull Optional<Instant> timestamp) {
        return new ReceiptTemplate(recipientName, orderNumber, paymentMethod, currency, summary, address,
                elements, adjustments, merchantName, orderUrl, sharable, timestamp);
    }

    private ReceiptTemplate(String recipientName, String orderNumber, String paymentMethod, String currency,
                            Summary summary, Optional<Address> address, Optional<List<Item>> elements,
                            Optional<List<Adjustment>> adjustments, Optional<String> merchantName,
                            Optional<URL> orderUrl, Optional<Boolean> sharable, Optional<Instant> timestamp) {
        super(Type.RECEIPT);
        this.recipientName = recipientName;
        this.orderNumber = orderNumber;
        this.paymentMethod = paymentMethod;
        this.currency = currency;
        this.summary = summary;
        this.address = address;
        this.elements = elements.map(Lists::immutableList);
        this.adjustments = adjustments.map(Lists::immutableList);
        this.merchantName = merchantName;
        this.orderUrl = orderUrl;
        this.sharable = sharable;
        this.timestamp = timestamp;
    }

    public String recipientName() {
        return recipientName;
    }

    public String orderNumber() {
        return orderNumber;
    }

    public String paymentMethod() {
        return paymentMethod;
    }

    public String currency() {
        return currency;
    }

    public Summary summary() {
        return summary;
    }

    public Optional<Address> address() {
        return address;
    }

    public Optional<List<Item>> elements() {
        return elements;
    }

    public Optional<List<Adjustment>> adjustments() {
        return adjustments;
    }

    public Optional<String> merchantName() {
        return merchantName;
    }

    public Optional<URL> orderUrl() {
        return orderUrl;
    }

    public Optional<Boolean> sharable() {
        return sharable;
    }

    public Optional<Instant> timestamp() {
        return timestamp;
    }
}
