package com.github.messenger4j.send.templates;

import com.github.messenger4j.send.buttons.Button;
import com.github.messenger4j.send.buttons.UrlButton;
import org.junit.Test;

public class ListTemplateTest {

    @Test
    public void buildNormal() throws Exception {
        ListTemplate.newBuilder(ListTemplate.TopElementStyle.COMPACT)
                .buttons(Button.newListBuilder().addUrlButton("asfd", "https://example.com").toList().build())
                .addElements()
                    .addElement("title1")
                    .subtitle("subTitle1")
                    .imageUrl("http://example.com/1.png")
                    .buttons(Button.newListBuilder().addUrlButton("t1", "http://example.com/1").toList().build())
                        .defaultAction("http://example.com/1a")
                        .webviewHeightRatio(UrlButton.WebviewHeightRatio.COMPACT)
                        .build()
                    .build()
                    .addElement("title2")
                    .subtitle("subTitle2")
                    .imageUrl("http://example.com/2.png")
                    .buttons(Button.newListBuilder().addUrlButton("t2", "http://example.com/2").toList().build())
                    .build()
                .done()
                .build();
    }

    @Test(expected = IllegalStateException.class)
    public void buildNotEnoughElements() throws Exception {
        ListTemplate.newBuilder(ListTemplate.TopElementStyle.COMPACT)
                .buttons(Button.newListBuilder().addUrlButton("asfd", "https://example.com").toList().build())
                .addElements()
                    .addElement("title1")
                    .subtitle("subTitle1")
                    .imageUrl("http://example.com/1.png")
                    .buttons(Button.newListBuilder().addUrlButton("t1", "http://example.com/1").toList().build())
                        .defaultAction("http://example.com/1a")
                        .webviewHeightRatio(UrlButton.WebviewHeightRatio.COMPACT)
                        .build()
                    .build()
                .done()
                .build();
    }

    @Test(expected = IllegalStateException.class)
    public void buildTooManyElements() throws Exception {
        ListTemplate.newBuilder(ListTemplate.TopElementStyle.COMPACT)
                .buttons(Button.newListBuilder().addUrlButton("asfd", "https://example.com").toList().build())
                .addElements()
                    .addElement("title1")
                    .subtitle("subTitle1")
                    .imageUrl("http://example.com/1.png")
                    .build()
                    .addElement("title2")
                    .subtitle("subTitle2")
                    .imageUrl("http://example.com/2.png")
                    .build()
                    .addElement("title3")
                    .subtitle("subTitle3")
                    .imageUrl("http://example.com/3.png")
                    .build()
                    .addElement("title4")
                    .subtitle("subTitle4")
                    .imageUrl("http://example.com/4.png")
                    .build()
                    .addElement("title5")
                    .subtitle("subTitle5")
                    .imageUrl("http://example.com/5.png")
                    .build()
                .done()
                .build();
    }

    @Test(expected = IllegalStateException.class)
    public void buildTooManyButtons() throws Exception {
        ListTemplate.newBuilder(ListTemplate.TopElementStyle.COMPACT)
                .buttons(Button.newListBuilder().addUrlButton("asfd", "https://example.com").toList().build())
                .addElements()
                    .addElement("title1")
                    .subtitle("subTitle1")
                    .imageUrl("http://example.com/1.png")
                    .buttons(Button.newListBuilder().addUrlButton("t1", "http://example.com/1").toList().addUrlButton("t1", "http://example.com/1").toList().build())
                    .build()
                    .addElement("title2")
                    .subtitle("subTitle2")
                    .imageUrl("http://example.com/2.png")
                    .buttons(Button.newListBuilder().addUrlButton("t2", "http://example.com/2").toList().build())
                    .build()
                .done()
                .build();
    }

    @Test
    public void buildNoButton() throws Exception {
        ListTemplate.newBuilder(ListTemplate.TopElementStyle.COMPACT)
                .buttons(Button.newListBuilder().addUrlButton("asfd", "https://example.com").toList().build())
                .addElements()
                    .addElement("title1")
                    .subtitle("subTitle1")
                    .imageUrl("http://example.com/1.png")
                    .build()
                    .addElement("title2")
                    .subtitle("subTitle2")
                    .imageUrl("http://example.com/2.png")
                    .build()
                .done()
                .build();
    }


    @Test(expected = IllegalArgumentException.class)
    public void buildLargeWithoutImageUrlOnFirstElement() throws Exception {
        ListTemplate.newBuilder(ListTemplate.TopElementStyle.LARGE)
                .buttons(Button.newListBuilder().addUrlButton("asfd", "https://example.com").toList().build())
                .addElements()
                    .addElement("title1")
                    .subtitle("subTitle1")
                    .buttons(Button.newListBuilder().addUrlButton("t1", "http://example.com/1").toList().build())
                    .build()
                    .addElement("title2")
                    .subtitle("subTitle2")
                    .imageUrl("http://example.com/2.png")
                    .buttons(Button.newListBuilder().addUrlButton("t2", "http://example.com/2").toList().build())
                    .build()
                    .done()
                .build();
    }

}