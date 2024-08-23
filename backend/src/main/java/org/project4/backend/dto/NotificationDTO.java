package org.project4.backend.dto;

import java.time.LocalDateTime;

public class NotificationDTO {
    private Long id;

    private Long taskId;

    private LocalDateTime notifyAt;

    private Boolean notified = Boolean.FALSE;
    private String contents;
    private TaskDTO task;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public LocalDateTime getNotifyAt() {
        return notifyAt;
    }

    public void setNotifyAt(LocalDateTime notifyAt) {
        this.notifyAt = notifyAt;
    }

    public Boolean getNotified() {
        return notified;
    }

    public void setNotified(Boolean notified) {
        this.notified = notified;
    }

    public TaskDTO getTask() {
        return task;
    }

    public void setTask(TaskDTO task) {
        this.task = task;
    }
}

