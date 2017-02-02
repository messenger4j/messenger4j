package com.github.messenger4j.send.templates;

import com.github.messenger4j.internal.PreConditions;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
public final class ReceiptTemplate extends Template {

    private final String recipientName;
    private final String merchantName;
    private final String orderNumber;
    private final String currency;
    private final String paymentMethod;
    private final String timestamp;
    private final String orderUrl;
    private final List<Element> elements;
    private final ShippingAddress address;
    private final Summary summary;
    private final List<Adjustment> adjustments;

    public static Builder newBuilder(String recipientName, String orderNumber, String currency, String paymentMethod) {
        return new Builder(recipientName, orderNumber, currency, paymentMethod);
    }

    private ReceiptTemplate(Builder builder) {
        super(TemplateType.RECEIPT);
        recipientName = builder.recipientName;
        merchantName = builder.merchantName;
        orderNumber = builder.orderNumber;
        currency = builder.currency;
        paymentMethod = builder.paymentMethod;
        timestamp = builder.timestamp;
        orderUrl = builder.orderUrl;
        elements = builder.elements;
        address = builder.address;
        summary = builder.summary;
        adjustments = builder.adjustments;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getCurrency() {
        return currency;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getOrderUrl() {
        return orderUrl;
    }

    public List<Element> getElements() {
        return elements;
    }

    public ShippingAddress getAddress() {
        return address;
    }

    public Summary getSummary() {
        return summary;
    }

    public List<Adjustment> getAdjustments() {
        return adjustments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ReceiptTemplate that = (ReceiptTemplate) o;
        return Objects.equals(recipientName, that.recipientName) &&
                Objects.equals(merchantName, that.merchantName) &&
                Objects.equals(orderNumber, that.orderNumber) &&
                Objects.equals(currency, that.currency) &&
                Objects.equals(paymentMethod, that.paymentMethod) &&
                Objects.equals(timestamp, that.timestamp) &&
                Objects.equals(orderUrl, that.orderUrl) &&
                Objects.equals(elements, that.elements) &&
                Objects.equals(address, that.address) &&
                Objects.equals(summary, that.summary) &&
                Objects.equals(adjustments, that.adjustments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), recipientName, merchantName, orderNumber, currency, paymentMethod,
                timestamp, orderUrl, elements, address, summary, adjustments);
    }

    @Override
    public String toString() {
        return "ReceiptTemplate{" +
                "recipientName='" + recipientName + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", currency='" + currency + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", orderUrl='" + orderUrl + '\'' +
                ", elements=" + elements +
                ", address=" + address +
                ", summary=" + summary +
                ", adjustments=" + adjustments +
                "} super=" + super.toString();
    }

    /**
     * @author Max Grabenhorst
     * @since 0.6.0
     */
    public static final class Builder {
        private final String recipientName;
        private String merchantName;
        private final String orderNumber;
        private final String currency;
        private final String paymentMethod;
        private String timestamp;
        private String orderUrl;
        private List<Element> elements;
        private ShippingAddress address;
        private Summary summary;
        private List<Adjustment> adjustments;

        private Builder(String recipientName, String orderNumber, String currency, String paymentMethod) {
            this.recipientName = recipientName;
            this.orderNumber = orderNumber;
            this.currency = currency;
            this.paymentMethod = paymentMethod;
        }

        public Summary.Builder addSummary(Float totalCost) {
            return new Summary.Builder(totalCost, this);
        }

        private Builder summary(Summary summary) {
            this.summary = summary;
            return this;
        }

        public Builder merchantName(String merchantName) {
            this.merchantName = merchantName;
            return this;
        }

        public Builder timestamp(Long timestamp) {
            this.timestamp = timestamp == null ? null : Long.toString(timestamp);
            return this;
        }

        public Builder orderUrl(String orderUrl) {
            this.orderUrl = orderUrl;
            return this;
        }

        public Element.ListBuilder addElements() {
            return new Element.ListBuilder(this);
        }

        private Builder elements(List<Element> elements) {
            this.elements = elements;
            return this;
        }

        public ShippingAddress.Builder addAddress(String street1, String city, String postalCode, String state,
                                                  String country) {

            return new ShippingAddress.Builder(street1, city, postalCode, state, country, this);
        }

        private Builder address(ShippingAddress address) {
            this.address = address;
            return this;
        }

        public Adjustment.ListBuilder addAdjustments() {
            return new Adjustment.ListBuilder(this);
        }

        private Builder adjustments(List<Adjustment> adjustments) {
            this.adjustments = adjustments;
            return this;
        }

        public ReceiptTemplate build() {
            PreConditions.notNull(this.summary, "summary", IllegalStateException.class);
            return new ReceiptTemplate(this);
        }
    }

    /**
     * @author Max Grabenhorst
     * @since 0.6.0
     */
    public static final class Summary {

        private final Float totalCost;
        private final Float totalTax;
        private final Float shippingCost;
        private final Float subtotal;

        private Summary(Builder builder) {
            totalCost = builder.totalCost;
            totalTax = builder.totalTax;
            shippingCost = builder.shippingCost;
            subtotal = builder.subtotal;
        }

        public Float getTotalCost() {
            return totalCost;
        }

        public Float getTotalTax() {
            return totalTax;
        }

        public Float getShippingCost() {
            return shippingCost;
        }

        public Float getSubtotal() {
            return subtotal;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Summary summary = (Summary) o;
            return Objects.equals(totalCost, summary.totalCost) &&
                    Objects.equals(totalTax, summary.totalTax) &&
                    Objects.equals(shippingCost, summary.shippingCost) &&
                    Objects.equals(subtotal, summary.subtotal);
        }

        @Override
        public int hashCode() {
            return Objects.hash(totalCost, totalTax, shippingCost, subtotal);
        }

        @Override
        public String toString() {
            return "Summary{" +
                    "totalCost=" + totalCost +
                    ", totalTax=" + totalTax +
                    ", shippingCost=" + shippingCost +
                    ", subtotal=" + subtotal +
                    '}';
        }

        /**
         * @author Max Grabenhorst
         * @since 0.6.0
         */
        public static final class Builder {
            private final Float totalCost;
            private Float totalTax;
            private Float shippingCost;
            private Float subtotal;
            private final ReceiptTemplate.Builder receiptTemplateBuilder;

            private Builder(Float totalCost, ReceiptTemplate.Builder receiptTemplateBuilder) {
                this.totalCost = totalCost;
                this.receiptTemplateBuilder = receiptTemplateBuilder;
            }

            public Builder totalTax(Float totalTax) {
                this.totalTax = totalTax;
                return this;
            }

            public Builder shippingCost(Float shippingCost) {
                this.shippingCost = shippingCost;
                return this;
            }

            public Builder subtotal(Float subtotal) {
                this.subtotal = subtotal;
                return this;
            }

            public ReceiptTemplate.Builder done() {
                return this.receiptTemplateBuilder.summary(new Summary(this));
            }
        }
    }

    /**
     * @author Max Grabenhorst
     * @since 0.6.0
     */
    public static final class Element {

        private final String title;
        private final String subtitle;
        private final Integer quantity;
        private final Float price;
        private final String currency;
        private final String imageUrl;

        private Element(Builder builder) {
            title = builder.title;
            subtitle = builder.subtitle;
            quantity = builder.quantity;
            price = builder.price;
            currency = builder.currency;
            imageUrl = builder.imageUrl;
        }

        public String getTitle() {
            return title;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public Float getPrice() {
            return price;
        }

        public String getCurrency() {
            return currency;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Element element = (Element) o;
            return Objects.equals(title, element.title) &&
                    Objects.equals(subtitle, element.subtitle) &&
                    Objects.equals(quantity, element.quantity) &&
                    Objects.equals(price, element.price) &&
                    Objects.equals(currency, element.currency) &&
                    Objects.equals(imageUrl, element.imageUrl);
        }

        @Override
        public int hashCode() {
            return Objects.hash(title, subtitle, quantity, price, currency, imageUrl);
        }

        @Override
        public String toString() {
            return "Element{" +
                    "title='" + title + '\'' +
                    ", subtitle='" + subtitle + '\'' +
                    ", quantity=" + quantity +
                    ", price=" + price +
                    ", currency='" + currency + '\'' +
                    ", imageUrl='" + imageUrl + '\'' +
                    '}';
        }

        /**
         * @author Max Grabenhorst
         * @since 0.6.0
         */
        public static final class ListBuilder {

            private static final int ELEMENTS_LIMIT = 100;

            private final List<Element> elements;
            private final ReceiptTemplate.Builder receiptTemplateBuilder;

            private ListBuilder(ReceiptTemplate.Builder receiptTemplateBuilder) {
                this.elements = new ArrayList<>();
                this.receiptTemplateBuilder = receiptTemplateBuilder;
            }

            public Builder addElement(String title, Float price) {
                return new Builder(title, price, this);
            }

            private ListBuilder addElementToList(Element element) {
                this.elements.add(element);
                return this;
            }

            public ReceiptTemplate.Builder done() {
                PreConditions.sizeNotGreaterThan(this.elements, ELEMENTS_LIMIT, "elements", IllegalStateException.class);
                return this.receiptTemplateBuilder.elements(Collections.unmodifiableList(new ArrayList<>(this.elements)));
            }
        }

        /**
         * @author Max Grabenhorst
         * @since 0.6.0
         */
        public static final class Builder {
            private final String title;
            private String subtitle;
            private Integer quantity;
            private final Float price;
            private String currency;
            private String imageUrl;
            private final ListBuilder listBuilder;

            private Builder(String title, Float price, ListBuilder listBuilder) {
                this.title = title;
                this.price = price;
                this.listBuilder = listBuilder;
            }

            public Builder subtitle(String subtitle) {
                this.subtitle = subtitle;
                return this;
            }

            public Builder quantity(Integer quantity) {
                this.quantity = quantity;
                return this;
            }

            public Builder currency(String currency) {
                this.currency = currency;
                return this;
            }

            public Builder imageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
                return this;
            }

            public ListBuilder toList() {
                return this.listBuilder.addElementToList(new Element(this));
            }
        }
    }

    /**
     * @author Max Grabenhorst
     * @since 0.6.0
     */
    public static final class ShippingAddress {

        @SerializedName("street_1")
        private final String street1;
        @SerializedName("street_2")
        private final String street2;
        private final String city;
        private final String postalCode;
        private final String state;
        private final String country;

        private ShippingAddress(Builder builder) {
            street1 = builder.street1;
            street2 = builder.street2;
            city = builder.city;
            postalCode = builder.postalCode;
            state = builder.state;
            country = builder.country;
        }

        public String getStreet1() {
            return street1;
        }

        public String getStreet2() {
            return street2;
        }

        public String getCity() {
            return city;
        }

        public String getPostalCode() {
            return postalCode;
        }

        public String getState() {
            return state;
        }

        public String getCountry() {
            return country;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ShippingAddress that = (ShippingAddress) o;
            return Objects.equals(street1, that.street1) &&
                    Objects.equals(street2, that.street2) &&
                    Objects.equals(city, that.city) &&
                    Objects.equals(postalCode, that.postalCode) &&
                    Objects.equals(state, that.state) &&
                    Objects.equals(country, that.country);
        }

        @Override
        public int hashCode() {
            return Objects.hash(street1, street2, city, postalCode, state, country);
        }

        @Override
        public String toString() {
            return "ShippingAddress{" +
                    "street1='" + street1 + '\'' +
                    ", street2='" + street2 + '\'' +
                    ", city='" + city + '\'' +
                    ", postalCode='" + postalCode + '\'' +
                    ", state='" + state + '\'' +
                    ", country='" + country + '\'' +
                    '}';
        }

        /**
         * @author Max Grabenhorst
         * @since 0.6.0
         */
        public static final class Builder {
            private final String street1;
            private String street2;
            private final String city;
            private final String postalCode;
            private final String state;
            private final String country;
            private final ReceiptTemplate.Builder receiptTemplateBuilder;

            private Builder(String street1, String city, String postalCode, String state, String country,
                            ReceiptTemplate.Builder receiptTemplateBuilder) {

                this.street1 = street1;
                this.city = city;
                this.postalCode = postalCode;
                this.state = state;
                this.country = country;
                this.receiptTemplateBuilder = receiptTemplateBuilder;
            }

            public Builder street2(String street2) {
                this.street2 = street2;
                return this;
            }

            public ReceiptTemplate.Builder done() {
                return this.receiptTemplateBuilder.address(new ShippingAddress(this));
            }
        }
    }

    /**
     * @author Max Grabenhorst
     * @since 0.6.0
     */
    public static final class Adjustment {

        private final String name;
        private final Float amount;

        private Adjustment(Builder builder) {
            name = builder.name;
            amount = builder.amount;
        }

        public String getName() {
            return name;
        }

        public Float getAmount() {
            return amount;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Adjustment that = (Adjustment) o;
            return Objects.equals(name, that.name) &&
                    Objects.equals(amount, that.amount);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, amount);
        }

        @Override
        public String toString() {
            return "Adjustment{" +
                    "name='" + name + '\'' +
                    ", amount=" + amount +
                    '}';
        }

        /**
         * @author Max Grabenhorst
         * @since 0.6.0
         */
        public static final class ListBuilder {

            private final List<Adjustment> adjustments;
            private final ReceiptTemplate.Builder receiptTemplateBuilder;

            private ListBuilder(ReceiptTemplate.Builder receiptTemplateBuilder) {
                this.adjustments = new ArrayList<>(10);
                this.receiptTemplateBuilder = receiptTemplateBuilder;
            }

            public Builder addAdjustment() {
                return new Builder(this);
            }

            private ListBuilder addAdjustmentToList(Adjustment adjustment) {
                this.adjustments.add(adjustment);
                return this;
            }

            public ReceiptTemplate.Builder done() {
                return this.receiptTemplateBuilder.adjustments(Collections.unmodifiableList(new ArrayList<>(this.adjustments)));
            }
        }

        /**
         * @author Max Grabenhorst
         * @since 0.6.0
         */
        public static final class Builder {
            private String name;
            private Float amount;
            private final ListBuilder listBuilder;

            private Builder(ListBuilder listBuilder) {
                this.listBuilder = listBuilder;
            }

            public Builder name(String name) {
                this.name = name;
                return this;
            }

            public Builder amount(Float amount) {
                this.amount = amount;
                return this;
            }

            public ListBuilder toList() {
                return this.listBuilder.addAdjustmentToList(new Adjustment(this));
            }
        }
    }
}