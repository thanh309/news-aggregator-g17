package group17.news_aggregator.gui;

import org.jsoup.nodes.Document;

public class WebFormatter {
    public static void format(Document doc) {
        doc.select("script,.hidden,form").remove();

        // for Cryptopolitan
        doc.select("div[class=\"elementor elementor-421319 elementor-location-header\"]").remove();
        doc.select("div[class=\"elementor elementor-421294 elementor-location-footer\"]").remove();
        doc.select("div[class=\"elementor-element elementor-element-4642d81 e-flex e-con-boxed e-con e-child\"]").remove();
        doc.select("div[class=\"elementor-element elementor-element-9a0cd41 e-flex e-con-boxed e-con e-child\"]").remove();
        doc.select("div[class=\"elementor-element elementor-element-9ec09fe e-flex e-con-boxed e-con e-child\"]").remove();
        doc.select("div[class=\"elementor-element elementor-element-ed0515d e-flex e-con-boxed e-con e-parent\"]").remove();
        doc.select("div[class=\"elementor-element elementor-element-5be13de e-con-full e-flex e-con e-child\"]").remove();
        doc.select("div[class=\"elementor-element elementor-element-bcc212e elementor-widget elementor-widget-shortcode\"]").remove();
        doc.select("div[class=\"elementor-element elementor-element-3c7a4e8 e-con-full e-flex e-con e-child\"]").remove();
        doc.select("div[class=\"elementor-element elementor-element-a7016d4 e-flex e-con-boxed e-con e-child\"]").remove();

        // for CryptoSlate
        doc.select("header[id=\"header\"]").remove();
        doc.select("footer[id=\"footer\"]").remove();
        doc.select("div[class=\"scrolling\"]").remove();
        doc.select("div[class=\"sidebar right\"]").remove();
        doc.select("div[class=\"mentioned-items\"]").remove();
        doc.select("div[class=\"posted-in\"]").remove();
        doc.select("div[class=\"post-meta-flex\"]").remove();
        doc.select("div[class=\"link-page\"]").remove();
        doc.select("div[class=\"related-articles featured list-feed\"]").remove();
        doc.select("div[class=\"related-articles press-releases\"]").remove();
        doc.select("div[class=\"footer-disclaimer\"]").remove();
        doc.select("div[id=\"hypelab-leaderboard\"]").remove();
        doc.select("div[id=\"hypelab-top-sticky\"]").remove();
    }
}
