const API_BASE = "http://localhost:8080/api";

async function register() {
    const fullName = document.getElementById("fullName").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    if (!fullName || !email || !password) {
        alert("All fields are required");
        return;
    }

    try {
        const response = await fetch(`${API_BASE}/auth/register`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ fullName, email, password })
        });

        if (!response.ok) {
            const err = await response.json();
            throw new Error(err.message || "Registration failed");
        }

        alert("Registered successfully. Please login.");
        window.location.href = "index.html";

    } catch (err) {
        alert(err.message);
    }
}
