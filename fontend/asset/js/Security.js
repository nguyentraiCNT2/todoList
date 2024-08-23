// Function to check token validity
function checkToken() {
    fetch('http://localhost:8080/api/auth/validate-token', {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + getToken() // Hàm để lấy token từ localStorage hoặc cookie
        },
        credentials: 'include'
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Token is invalid or expired');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        window.location.href = 'login.html'; // Chuyển hướng đến trang đăng nhập
    });
}

// Function to check user permissions
function checkPermissions() {
    fetch('http://localhost:8080/api/auth/check-permissions', {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + getToken() // Hàm để lấy token từ localStorage hoặc cookie
        },
        credentials: 'include'
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Insufficient permissions');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Bạn không có quyền truy cập vào trang này.');
        window.location.href = 'index.html'; // Chuyển hướng đến trang chính hoặc trang khác
    });
}

// Check token and permissions on page load
document.addEventListener('DOMContentLoaded', function () {
    checkToken();
    checkPermissions();
});
