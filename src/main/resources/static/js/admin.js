document.addEventListener("DOMContentLoaded", () => {

    console.log("admin.js loaded");

    const token = requireRole("ROLE_ADMIN");
    if (!token) return;

    document.getElementById("createHrBtn")
        .addEventListener("click", createHR);
});

//CREATE HR

async function createHR() {

    const btn = document.getElementById("createHrBtn");

    const token = localStorage.getItem("token");

    const fullName = document.getElementById("fullName").value.trim();
    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value.trim();

    if (!fullName || !email || !password) {
        showToast("All fields required");
        return;
    }

    btn.disabled = true;
    btn.textContent = "Creating...";

    try {
        const response = await fetch(`${API_BASE}/admin/create-hr`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`
            },
            body: JSON.stringify({ fullName, email, password })
        });

        if (!response.ok) throw new Error("HR creation failed");

        showToast("HR created successfully");

        document.getElementById("fullName").value = "";
        document.getElementById("email").value = "";
        document.getElementById("password").value = "";

    } catch (err) {
        console.error(err);
        showToast(err.message);
    }

    btn.disabled = false;
    btn.textContent = "Create HR";
}
