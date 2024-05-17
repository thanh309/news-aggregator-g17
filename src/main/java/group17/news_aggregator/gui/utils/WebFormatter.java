package group17.news_aggregator.gui.utils;

import org.jsoup.nodes.Document;

public class WebFormatter {
    public static void format(Document doc) {
        doc.select("script,.hidden,form").remove();

        // for Cryptopolitan
        // TODO more formatting to make this page look better
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
        doc.select("footer[class=\"footer\"]").remove();
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

        // for Cryptonews
        doc.select("section[class=\"container container-header-price\"]").remove();
        doc.select("section[class=\"container newsletter-section\"]").remove();
        doc.select("div[class=\"container container-post-one\"]").remove();
        doc.select("div[class=\"col-12 col-lg-3\"]").remove();

        // for MediumReader
        doc.select("div[class=\"fixed z-10 h-[54px] w-screen flex flex-grow-0 items-center justify-between bg-white dark:bg-black px-4 shadow-md dark:shadow-neutral-900\"]").remove();
        doc.select("nav").remove();
        doc.select("span[class=\"flex justify-center items-center w-full py-12 text-2xl divide-y border-t border-gray-300 dark:border-gray-700\"]").remove();
        doc.select("div[class=\"max-w-[680px]\"]").remove();
        doc.select("div[class=\"cursor-pointer relative\"]").remove();
        doc.select("div[class=\"w-full py-8\"]").remove();
        doc.select("div[class=\"relative mt-4\"]").remove();
        doc.select("div[class=\"link-block\"]").remove();
        doc.select("div[class=\" text-sm font-bold mt-12 space-y-[4px]\"]").remove();
    }
}