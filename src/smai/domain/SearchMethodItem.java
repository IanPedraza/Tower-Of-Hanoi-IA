package smai.domain;

import smai.common.SearchType;

public class SearchMethodItem {

    private int key;
    private String value;
    private SearchType type;

    public SearchMethodItem(int key, String value, SearchType type) {
        this.key = key;
        this.value = value;
        this.type = type;
    }

    @Override
    public String toString() {
        return this.value;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public SearchType getType() {
        return type;
    }

    public void setType(SearchType type) {
        this.type = type;
    }

}
