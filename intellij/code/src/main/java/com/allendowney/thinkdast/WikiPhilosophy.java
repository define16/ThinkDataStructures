package com.allendowney.thinkdast;

import java.io.IOException;
import java.util.*;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

public class WikiPhilosophy {

    final static List<String> visited = new ArrayList<String>();
    final static WikiFetcher wf = new WikiFetcher();

    /**
     * Tests a conjecture about Wikipedia and Philosophy.
     * <p>
     * https://en.wikipedia.org/wiki/Wikipedia:Getting_to_Philosophy
     * <p>
     * 1. Clicking on the first non-parenthesized, non-italicized link
     * 2. Ignoring external links, links to the current page, or red links
     * 3. Stopping when reaching "Philosophy", a page with no links or a page
     * that does not exist, or when a loop occurs
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        String destination = "https://en.wikipedia.org/wiki/Philosophy";
        String source = "https://en.wikipedia.org/wiki/Java_(programming_language)";

        testConjecture(destination, source, 10);
    }

    /**
     * Starts from given URL and follows first link until it finds the destination or exceeds the limit.
     *
     * @param destination
     * @param source
     * @throws IOException
     */
    public static void testConjecture(String destination, String source, int limit) throws IOException {
        // TODO: FILL THIS IN!
        String wikiHome = "https://en.wikipedia.org";

        HashMap<String, Boolean> checkWalkLink = new HashMap<String, Boolean>();
        checkWalkLink.put(source, true); // root

        WikiFetcher wikiFetcher = new WikiFetcher();

        Elements paras = wikiFetcher.fetchWikipedia(source);
        WikiParser wikiParser = new WikiParser(paras);
        Element link = wikiParser.findFirstLink();
        while (true) {
            System.out.println(link.baseUri());
            if (link == null) {
                System.out.println("유효하지 않는 URL입니다.");
                break;
            }

            String url = wikiHome + link.attr("href");
            if (checkWalkLink.containsKey(url)) {
                System.out.println("이미 확인된 링크입니다.");
                break;
            } else if (destination.equals(link.baseUri())) {
                System.out.println("목적지 도달했습니다");
                break;
            }
            else {
                paras = wikiFetcher.fetchWikipedia(url);
                wikiParser = new WikiParser(paras);
                link = wikiParser.findFirstLink();
                checkWalkLink.put(url, true);
            }
        }
    }

}
