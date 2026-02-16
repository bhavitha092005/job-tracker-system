const API_BASE = `${window.location.origin}/api`;

async function register() {

    const fullName = document.getElementById("fullName").value.trim();
    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value.trim();

    if (!fullName || !email || !password) {
        showToast("All fields are required");
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

            let errorMessage = "Registration failed";

            try {
                const err = await response.json();
                errorMessage = err.message || errorMessage;
            } catch {
                // ignore JSON parsing errors
            }

            throw new Error(errorMessage);
        }

        showToast("Registered successfully");
        window.location.href = "index.html";

    } catch (err) {

        console.error("REGISTER ERROR:", err);
        showToast(err.message);
    }
}
