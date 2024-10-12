package models;

import java.util.List;

public class PositionUrlSegment {
    private List<String> adBreakIndex;
    private List<String> nextEventMs;

    public List<String> getAdBreakIndex() {
        return adBreakIndex;
    }

    public void setAdBreakIndex(List<String> adBreakIndex) {
        this.adBreakIndex = adBreakIndex;
    }

    public List<String> getNextEventMs() {
        return nextEventMs;
    }

    public void setNextEventMs(List<String> nextEventMs) {
        this.nextEventMs = nextEventMs;
    }
}
