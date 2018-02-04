package com.github.messenger4j.messengerprofile.homeurl;

import static java.util.Optional.empty;

import com.github.messenger4j.common.WebviewHeightRatio;
import com.github.messenger4j.common.WebviewShareButtonState;
import com.google.gson.annotations.SerializedName;
import java.net.URL;
import java.util.Optional;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode
public final class HomeUrl {

    private final URL url;
    private final WebviewHeightRatio webviewHeightRatio;
    private final boolean inTest;
    @SerializedName("webview_share_button")
    private final Optional<WebviewShareButtonState> webviewShareButtonState;

    public static HomeUrl create(@NonNull URL url, boolean inTest) {
        return create(url, inTest, empty());
    }

    public static HomeUrl create(@NonNull URL url, boolean inTest,
                                 @NonNull Optional<WebviewShareButtonState> webviewShareButtonState) {
        return new HomeUrl(url, inTest, webviewShareButtonState);
    }

    private HomeUrl(URL url, boolean inTest, Optional<WebviewShareButtonState> webviewShareButtonState) {
        this.url = url;
        this.webviewHeightRatio = WebviewHeightRatio.TALL;
        this.inTest = inTest;
        this.webviewShareButtonState = webviewShareButtonState;
    }

    public URL url() {
        return url;
    }

    public boolean inTest() {
        return inTest;
    }

    public Optional<WebviewShareButtonState> webviewShareButtonState() {
        return webviewShareButtonState;
    }
}
