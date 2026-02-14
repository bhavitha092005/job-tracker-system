
// LOGIN 

async function login() {

    const btn = document.querySelector("button.primary");

    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value.trim();

    console.log("📨 EMAIL SENT →", email);
    console.log("🔑 PASSWORD SENT →", password);

    if (!email || !password) {
        showToast("Email and password required");
        return;
    }

    btn.disabled = true;
    btn.textContent = "Logging in...";

    try {
        const response = await fetch(`${API_BASE}/auth/login`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email, password })
        });

        if (!response.ok) throw new Error("Invalid credentials");

        const data = await response.json();

        console.log("✅ LOGIN RESPONSE →", data);

        const token = data.accessToken;

        console.log("🎟 TOKEN →", token);

        localStorage.setItem("token", token);

        const payload = JSON.parse(atob(token.split(".")[1]));

        console.log("📦 PAYLOAD →", payload);
        console.log("👥 ROLES →", payload.roles);

        const roles = payload.roles || [];

        showToast("Login successful");

        // ✅ REDIRECT TRACE ⭐⭐⭐⭐⭐

        if (roles.includes("ROLE_ADMIN")) {
            console.log("➡ Redirecting ADMIN...");
            window.location.href = "admin.html";
        }
        else if (roles.includes("ROLE_HR")) {
            console.log("➡ Redirecting HR...");
            window.location.href = "hr.html";
        }
        else if (roles.includes("ROLE_CANDIDATE")) {
            console.log("➡ Redirecting CANDIDATE...");
            window.location.href = "candidate.html";
        }
        else {
            console.log("❌ UNKNOWN ROLE →", roles);
            showToast("Unknown role");
            localStorage.removeItem("token");
        }

    } catch (err) {
        console.error("❌ LOGIN ERROR →", err);
        showToast(err.message);
    }

    btn.disabled = false;
    btn.textContent = "Login";
}

// ================= LOGOUT =================

function logout() {
    localStorage.removeItem("token");
    window.location.href = "index.html";
}
