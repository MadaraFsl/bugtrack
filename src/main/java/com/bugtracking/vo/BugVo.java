package com.bugtracking.vo;


public class BugVo {

    private Integer newCount;
    private Integer openCount;
    private Integer fixedCount;
    private Integer rejectedCount;
    private Integer closeCount;
    private Integer deferredCount;
    private Integer countAll;

    public Integer getNewCount() {
        return newCount;
    }

    public void setNewCount(Integer newCount) {
        this.newCount = newCount;
    }

    public Integer getOpenCount() {
        return openCount;
    }

    public void setOpenCount(Integer openCount) {
        this.openCount = openCount;
    }

    public Integer getFixedCount() {
        return fixedCount;
    }

    public void setFixedCount(Integer fixedCount) {
        this.fixedCount = fixedCount;
    }

    public Integer getRejectedCount() {
        return rejectedCount;
    }

    public void setRejectedCount(Integer rejectedCount) {
        this.rejectedCount = rejectedCount;
    }

    public Integer getCloseCount() {
        return closeCount;
    }

    public void setCloseCount(Integer closeCount) {
        this.closeCount = closeCount;
    }

    public Integer getDeferredCount() {
        return deferredCount;
    }

    public void setDeferredCount(Integer deferredCount) {
        this.deferredCount = deferredCount;
    }

    public Integer getCountAll() {
        return countAll;
    }

    public void setCountAll(Integer countAll) {
        this.countAll = countAll;
    }
}
