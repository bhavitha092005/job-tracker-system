const API_BASE = `${window.location.origin}/api`;

function showToast(message) {
    const toast = document.getElementById("toast");

    if (!toast) {
        console.warn("Toast container missing");
        return;
    }

    toast.textContent = message;
    toast.classList.add("show");

    setTimeout(() => {
        toast.classList.remove("show");
    }, 2500);
}

function requireRole(role) {

    const token = localStorage.getItem("token");

    if (!token) {
        redirectToLogin();
        return null;
    }

    try {
        const payload = JSON.parse(atob(token.split(".")[1]));

        if (payload.exp * 1000 < Date.now()) {
            localStorage.removeItem("token");
            showToast("Session expired");
            redirectToLogin();
            return null;
        }

        if (!payload.roles.includes(role)) {
            showToast("Access denied");
            redirectToLogin();
            return null;
        }

        return token;

    } catch (err) {
        console.error("Invalid token", err);
        localStorage.removeItem("token");
        redirectToLogin();
        return null;
    }
}

function logout() {
    localStorage.removeItem("token");
    redirectToLogin();
}

function redirectToLogin() {
    window.location.href = "index.html";
}
