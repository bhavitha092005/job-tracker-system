console.log("hr.js loaded");

const token = localStorage.getItem("token");

if (!token) redirect();

let payload;
try {
    payload = JSON.parse(atob(token.split(".")[1]));
} catch {
    redirect();
}

if (!payload.roles.includes("ROLE_HR")) redirect();


document.getElementById("createJobBtn")
    .addEventListener("click", createJob);

//  CREATE JOB 

async function createJob() {

    const btn = document.getElementById("createJobBtn");

    const title = document.getElementById("title").value.trim();
    const description = document.getElementById("description").value.trim();
    const salary = document.getElementById("salary").value.trim();

    if (!title || !description || !salary) {
        showToast("All fields required");
        return;
    }

    btn.disabled = true;
    btn.textContent = "Creating...";

    try {
        const response = await fetch(`${API_BASE}/jobs`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`
            },
            body: JSON.stringify({ title, description, salary })
        });

        if (!response.ok) throw new Error("Job creation failed");

        showToast("Job created");

        document.getElementById("title").value = "";
        document.getElementById("description").value = "";
        document.getElementById("salary").value = "";

        loadJobs();

    } catch (err) {
        showToast(err.message);
    }

    btn.disabled = false;
    btn.textContent = "Create Job";
}

//  LOAD JOBS

async function loadJobs() {

    const list = document.getElementById("jobsList");
    list.innerHTML = "<p>Loading jobs...</p>";

    try {
        const response = await fetch(`${API_BASE}/jobs/my-jobs`, {
            headers: { "Authorization": `Bearer ${token}` }
        });

        if (!response.ok) throw new Error("Failed to load jobs");

        const page = await response.json();
        const jobs = page.content || [];

        list.innerHTML = "";

        if (!jobs.length) {
            list.innerHTML = "<p>No jobs created yet</p>";
            return;
        }

        jobs.forEach(job => {
            const li = document.createElement("li");

            li.innerHTML = `
                <strong>${job.title}</strong>
                <button class="secondary small"
                    onclick="loadApplications(${job.id})">
                    View Applications
                </button>
            `;

            list.appendChild(li);
        });

    } catch (err) {
        showToast(err.message);
    }
}

// ================= LOAD APPLICATIONS =================

async function loadApplications(jobId) {

    const container = document.getElementById("applicationsContainer");
    container.innerHTML = "<p>Loading applications...</p>";

    try {
        const response = await fetch(
            `${API_BASE}/applications/job/${jobId}`,
            { headers: { "Authorization": `Bearer ${token}` } }
        );

        if (!response.ok) throw new Error("Failed to load applications");

        const apps = await response.json();

        container.innerHTML = "";

        if (!apps.length) {
            container.innerHTML = "<p>No applications yet</p>";
            return;
        }

        apps.forEach(app => {
            const div = document.createElement("div");
            div.className = "card";

            div.innerHTML = `
                <p><strong>Candidate:</strong> ${app.candidateName}</p>
                <p><strong>Status:</strong> ${app.status}</p>
                ${renderActions(app)}
                <br><br>
                <button class="secondary small"
                    onclick="downloadResume(${app.id})">
                    Download Resume
                </button>
            `;

            container.appendChild(div);
        });

    } catch (err) {
        showToast(err.message);
    }
}

// ================= STATUS ACTIONS ================= ⭐⭐⭐⭐⭐

function renderActions(app) {

    if (app.status === "APPLIED") {
        return `<button onclick="updateStatus(${app.id}, 'REVIEWED', this)">
                    Review
                </button>`;
    }

    if (app.status === "REVIEWED") {
        return `<button onclick="updateStatus(${app.id}, 'INTERVIEW', this)">
                    Interview
                </button>`;
    }

    if (app.status === "INTERVIEW") {
        return `
            <button onclick="updateStatus(${app.id}, 'HIRED', this)">Hire</button>
            <button onclick="updateStatus(${app.id}, 'REJECTED', this)">Reject</button>
        `;
    }

    return "";
}

// ================= UPDATE STATUS =================

async function updateStatus(applicationId, status, btn) {

    btn.disabled = true;
    btn.textContent = "Updating...";

    try {
        const response = await fetch(
            `${API_BASE}/applications/${applicationId}/status`,
            {
                method: "PATCH",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`
                },
                body: JSON.stringify({ status })
            }
        );

        if (!response.ok) throw new Error("Update failed");

        showToast("Status updated");

        btn.textContent = "Done";

    } catch (err) {
        showToast(err.message);
        btn.disabled = false;
        btn.textContent = "Retry";
    }
}

// ================= DOWNLOAD RESUME =================

async function downloadResume(applicationId) {

    try {
        const response = await fetch(
            `${API_BASE}/applications/resume/${applicationId}`,
            { headers: { "Authorization": `Bearer ${token}` } }
        );

        if (!response.ok) throw new Error("Download failed");

        const blob = await response.blob();
        const url = window.URL.createObjectURL(blob);

        const a = document.createElement("a");
        a.href = url;
        a.download = "resume.pdf";
        a.click();

        window.URL.revokeObjectURL(url);

    } catch (err) {
        showToast(err.message);
    }
}

// ================= HELPERS =================

function redirect() {
    localStorage.removeItem("token");
    window.location.href = "index.html";
}

loadJobs();
