package com.appabc.datas.system;

import java.util.List;
import java.util.Map;

/**
 * Created by zouxifeng on 11/26/14.
 */
public class Context<T> {
    private int start;
    private int totalRows;
    private int pageSize;
    private List<T> result;
    private Map<String, Object> parameters;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }
}
