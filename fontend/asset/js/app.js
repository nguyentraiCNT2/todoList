document.addEventListener('DOMContentLoaded', () => {
    const apiUrl = 'http://localhost:8080'; // URL của backend API

    // Create Task
    document.getElementById('createTaskForm').addEventListener('submit', async (e) => {
        e.preventDefault();
        
        const taskTitle = document.getElementById('taskTitle').value;
        const taskDescription = document.getElementById('taskDescription').value;
        const taskTagId = document.getElementById('taskTagId').value;
        
        try {
            const response = await fetch(`${apiUrl}/api/task/createtask/${taskTagId}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem('token')}`
                },
                body: JSON.stringify({
                    title: taskTitle,
                    description: taskDescription
                })
            });
            const result = await response.json();
            alert(result);
        } catch (error) {
            console.error('Error:', error);
        }
    });

    // Create Tag
    document.getElementById('createTagForm').addEventListener('submit', async (e) => {
        e.preventDefault();
        
        const tagName = document.getElementById('tagName').value;
        
        try {
            const response = await fetch(`${apiUrl}/user/tag/create`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem('token')}`
                },
                body: JSON.stringify({ name: tagName })
            });
            const result = await response.json();
            alert(result);
        } catch (error) {
            console.error('Error:', error);
        }
    });

    // Create Group
    document.getElementById('createGroupForm').addEventListener('submit', async (e) => {
        e.preventDefault();
        
        const groupName = document.getElementById('groupName').value;
        
        try {
            const response = await fetch(`${apiUrl}/user/group/creategroup`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem('token')}`
                },
                body: JSON.stringify({ name: groupName })
            });
            const result = await response.json();
            alert(result);
        } catch (error) {
            console.error('Error:', error);
        }
    });

    // Fetch Notifications
    document.getElementById('fetchNotifications').addEventListener('click', async () => {
        try {
            const response = await fetch(`${apiUrl}/api/user/getbytask/1`, { // Chỉ số task có thể được thay đổi
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('token')}`
                }
            });
            const notifications = await response.json();
            const notificationList = document.getElementById('notificationList');
            notificationList.innerHTML = notifications.map(notification => `<p>${notification.message}</p>`).join('');
        } catch (error) {
            console.error('Error:', error);
        }
    });
});
