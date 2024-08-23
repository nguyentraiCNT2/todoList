package org.project4.backend.service.impl;

import org.modelmapper.ModelMapper;
import org.project4.backend.dto.TaskDTO;
import org.project4.backend.entity.*;
import org.project4.backend.repository.*;
import org.project4.backend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Service
public class TaskServiceIMPL implements TaskService {

    private TaskRepository taskRepository;
    private TagRepository tagRepository;
    private TaskTagRepository taskTagRepository;
    private ModelMapper modelMapper;
    private UserRepository userRepository;
    private GroupRepository groupRepository;
    private TaskHistoryRepository taskHistoryRepository;
    private NotificationsRepository notificationsRepository;
    @Autowired
    public TaskServiceIMPL(TaskRepository taskRepository, TagRepository tagRepository, TaskTagRepository taskTagRepository, ModelMapper modelMapper, UserRepository userRepository, GroupRepository groupRepository, TaskHistoryRepository taskHistoryRepository, NotificationsRepository notificationsRepository) {
        this.taskRepository = taskRepository;
        this.tagRepository = tagRepository;
        this.taskTagRepository = taskTagRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.taskHistoryRepository = taskHistoryRepository;
        this.notificationsRepository = notificationsRepository;
    }

    @Override
    public List<TaskDTO> getTaskBytag(Long tagid) {
        List<TaskDTO> resutf = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        TagEntity tag = tagRepository.findById(tagid).orElseThrow(() -> new RuntimeException(" Không tìm thấy nhãn nào có id là: "+tagid));
        List<TaskTagEntity> taskTagEntities = taskTagRepository.findByTag(tag);
        for (TaskTagEntity item: taskTagEntities){
            TaskDTO taskDTO = modelMapper.map(item.getTask(), TaskDTO.class);
            resutf.add(taskDTO);
            if (item.getTask().getDueDate().isAfter(now)){
                NotificationEntity notificationEntity = new NotificationEntity();
                notificationEntity.setNotified(false);
                notificationEntity.setTask(item.getTask());
                notificationEntity.setTaskId(item.getTask().getId());
                notificationEntity.setNotifyAt(now);
                notificationEntity.setContents("Công việc "+item.getTask().getTitle()+" Đã quá hạn");
                notificationsRepository.save(notificationEntity);
            }
            if (item.getTask().getDueDate().isEqual(now)){
                NotificationEntity notificationEntity = new NotificationEntity();
                notificationEntity.setNotified(false);
                notificationEntity.setTask(item.getTask());
                notificationEntity.setTaskId(item.getTask().getId());
                notificationEntity.setNotifyAt(now);
                notificationEntity.setContents("Công việc "+item.getTask().getTitle()+" Đã đến hạn");
                notificationsRepository.save(notificationEntity);
            }
        }
        return resutf;
    }

    @Override
    public List<TaskDTO> getByname(String name) {
        List<TaskDTO> resutf = new ArrayList<>();
        List<TaskEntity> taskEntities = taskRepository.findByTitleLike(name);
        if (taskEntities.size() == 0)
            throw  new RuntimeException("Không có công việc nào là "+name);
        for (TaskEntity item: taskEntities){
            TaskDTO taskDTO = modelMapper.map(item, TaskDTO.class);
            resutf.add(taskDTO);
        }

        return resutf;
    }

    @Override
    public TaskDTO getById(Long id) {
        return null;
    }

    @Override
    public void createTask(TaskDTO taskDTO, Long tagid) {
        try {
            LocalDateTime now = LocalDateTime.now();
            if (taskDTO.getTitle() == null && taskDTO.getTitle() =="")
                throw new RuntimeException("Bạn chưa nhập tiêu đề của công việc.");
            if (taskDTO.getDescription() == null && taskDTO.getDescription() == "")
                throw new RuntimeException("Bạn chưa nhập chi tiết công việc.");
            if (taskDTO.getDueDate() == null )
                throw new RuntimeException("Bạn chưa thời gian hết hạn của công việc.");
            if (taskDTO.getDueDate().isBefore(now) )
                throw new RuntimeException("Thời gian không thể chọn thời gian nhỏ hơn này hiện tại.");
            TagEntity tag = tagRepository.findById(tagid).orElseThrow(() -> new RuntimeException("Không tìm thấy nhãn nào có id là: "+tagid));
            UserEntity user = userRepository.findById(taskDTO.getUserId()).orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng nào có id là:"+ taskDTO.getUserId()));
            GroupEntity group = groupRepository.findById(taskDTO.getGroupId()).orElseThrow(() -> new RuntimeException("Không tìm thấy nhóm nào có id là: "+ taskDTO.getGroupId()));
            TaskEntity task = new TaskEntity();
            task.setCompleted(true);
            task.setCreatedAt(now);
            task.setUpdatedAt(now);
            task.setUser(user);
            task.setGroup(group);
            task.setDescription(taskDTO.getDescription());
            task.setTitle(taskDTO.getTitle());
            task.setPriority(taskDTO.getPriority());
            task.setDueDate(taskDTO.getDueDate());
            task.setUserId(taskDTO.getUserId());
            task.setGroupId(taskDTO.getGroupId());
            TaskEntity task_save = taskRepository.save(task);
            TaskTagEntity taskTag = new TaskTagEntity();
            taskTag.setTask(task_save);
            taskTag.setTag(tag);
            taskTagRepository.save(taskTag);
            TaskHistoryEntity entity = new TaskHistoryEntity();
            entity.setAction("CREATE");
            entity.setTimestamp(now);
            entity.setTask(task_save);
            entity.setUser(user);
            entity.setTaskId(task_save.getId());
            entity.setUserId(user.getId());
            taskHistoryRepository.save(entity);
        }catch (Exception e){
            throw new RuntimeException("Có lỗi sảy ra khi thêm mới công việc: "+e.getMessage());
        }
    }

    @Override
    public void updateTask(TaskDTO taskDTO , Long tagid) {
        try {
            LocalDateTime now = LocalDateTime.now();
            if (taskDTO.getTitle() == null && taskDTO.getTitle() =="")
                throw new RuntimeException("Bạn chưa nhập tiêu đề của công việc.");
            if (taskDTO.getDescription() == null && taskDTO.getDescription() == "")
                throw new RuntimeException("Bạn chưa nhập chi tiết công việc.");
            if (taskDTO.getDueDate() == null )
                throw new RuntimeException("Bạn chưa thời gian hết hạn của công việc.");
            if (taskDTO.getDueDate().isAfter(now) )
                throw new RuntimeException("Thời gian không thể chọn thời gian nhỏ hơn này hiện tại.");
            TaskEntity taskEntity = taskRepository.findById(taskDTO.getId()).orElseThrow(() -> new RuntimeException("Không tìm thấy công việc nào có id là: "+taskDTO.getId()));
            TagEntity tag = tagRepository.findById(tagid).orElseThrow(() -> new RuntimeException("Không tìm thấy nhãn nào có id là: "+tagid));
            UserEntity user = userRepository.findById(taskDTO.getUserId()).orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng nào có id là:"+ taskDTO.getUserId()));
            GroupEntity group = groupRepository.findById(taskDTO.getGroupId()).orElseThrow(() -> new RuntimeException("Không tìm thấy nhóm nào có id là: "+ taskDTO.getGroupId()));
            TaskEntity task = modelMapper.map(taskDTO, TaskEntity.class);
            task.setCompleted(true);
            task.setCreatedAt(taskEntity.getCreatedAt());
            task.setUpdatedAt(now);
            task.setUser(user);
            task.setGroup(group);
            TaskEntity task_save = taskRepository.save(task);
            TaskTagEntity taskTag = new TaskTagEntity();
            taskTag.setTask(task_save);
            taskTag.setTag(tag);
            taskTagRepository.save(taskTag);
            TaskHistoryEntity entity = new TaskHistoryEntity();
            entity.setAction("UPDATE");
            entity.setTimestamp(now);
            entity.setTask(task_save);
            entity.setUser(user);
            entity.setTaskId(task_save.getId());
            entity.setUserId(user.getId());
            taskHistoryRepository.save(entity);
        }catch (Exception e){
            throw new RuntimeException("Có lỗi sảy ra khi thêm mới công việc: "+e.getMessage());
        }
    }

    @Override
    public void deleteTask(Long id) {
        try {
            TaskEntity taskEntity = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy công việc nào có id là: "+id));
            List<TaskTagEntity> taskTagEntities  = taskTagRepository.findByTask(taskEntity);
            for (TaskTagEntity item: taskTagEntities){
                taskTagRepository.delete(item);
            }
            taskRepository.delete(taskEntity);
        }catch (Exception e){
            throw  new RuntimeException("Có lỗi khi xóa là: "+e.getMessage());
        }
    }
}
