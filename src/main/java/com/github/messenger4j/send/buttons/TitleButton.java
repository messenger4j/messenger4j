package com.github.messenger4j.send.buttons;

import java.util.Objects;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
abstract class TitleButton extends Button {

    private final String title;

    TitleButton(ButtonType type, String title) {
        super(type);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TitleButton that = (TitleButton) o;
        return Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), title);
    }

    @Override
    public String toString() {
        return "TitleButton{" +
                "title='" + title + '\'' +
                "} super=" + super.toString();
    }
}
