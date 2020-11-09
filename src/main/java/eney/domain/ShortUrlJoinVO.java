package eney.domain;

/**
 * Created by doctorjj on 2017. 6. 15..
 */
public class ShortUrlJoinVO {
    private int urlIdx;
    private String shortUrl;
    private String channel;

    public ShortUrlJoinVO(String shortUrl, String channel) {
        this.shortUrl = shortUrl;
        this.channel = channel;
    }

    public ShortUrlJoinVO(int urlIdx, String shortUrl, String channel) {
        this.urlIdx = urlIdx;
        this.shortUrl = shortUrl;
        this.channel = channel;
    }

    public int getUrlIdx() {
        return urlIdx;
    }

    public void setUrlId(int urlIdx) {
        this.urlIdx = urlIdx;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String ShortUrl) {
        this.shortUrl = ShortUrl;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        return "ShortUrlJoinVO{" +
                "urlIdx='" + urlIdx + '\'' +
                ", shortUrl='" + shortUrl + '\'' +
                ", channel='" + channel + '\'' +
                '}';
    }
}
