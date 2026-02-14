console.log("candidate.js loaded");

const token = localStorage.getItem("token");
if (!token) redirect();

let payload;
try {
    payload = JSON.parse(atob(token.split(".")[1]));
} catch {
    redirect();
}

if (!payload.roles.includes("ROLE_CANDIDATE")) redirect();


// LOAD JOBS 

async function loadJobs() {

    const container = document.getElementById("jobsContainer");
    container.innerHTML = "<p>Loading jobs...</p>";

    try {
        const response = await fetch(`${API_BASE}/jobs`, {
            headers: { "Authorization": `Bearer ${token}` }
        });

        if (!response.ok) throw new Error("Failed to load jobs");

        const page = await response.json();
        renderJobs(page.content || []);

    } catch (err) {
        showToast(err.message);
    }
}

function renderJobs(jobs) {

    const container = document.getElementById("jobsContainer");
    container.innerHTML = "";

    if (!jobs.length) {
        container.innerHTML = "<p>No jobs available</p>";
        return;
    }

    jobs.forEach(job => {

        const div = document.createElement("div");
        div.className = "card";

        div.innerHTML = `
            <h4>${job.title}</h4>
            <p>${job.description}</p>
            <p><strong>Salary:</strong> ${job.salary}</p>

            <input type="file" id="resume-${job.id}">
            <button onclick="applyJob(${job.id}, this)">
                Apply
            </button>
        `;

        container.appendChild(div);
    });
}

// APPLY 

async function applyJob(jobId, btn) {

    const resumeInput = document.getElementById(`resume-${jobId}`);

    if (!resumeInput.files.length) {
        showToast("Upload resume");
        return;
    }

    btn.disabled = true;
    btn.textContent = "Submitting...";

    const formData = new FormData();
    formData.append("jobId", jobId);
    formData.append("resume", resumeInput.files[0]);

    try {
        const response = await fetch(`${API_BASE}/applications/apply`, {
            method: "POST",
            headers: { "Authorization": `Bearer ${token}` },
            body: formData
        });

        if (!response.ok) throw new Error("Application failed");

        showToast("Applied successfully");

    } catch (err) {
        showToast(err.message);
        btn.disabled = false;
        btn.textContent = "Apply";
    }
}

//  HELPERS 

function redirect() {
    localStorage.removeItem("token");
    window.location.href = "index.html";
}

loadJobs();
