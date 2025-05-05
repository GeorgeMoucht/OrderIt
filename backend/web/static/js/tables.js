document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll(".delete-table-btn").forEach((btn) => {
        btn.addEventListener("click", () => {
            const tableId = btn.getAttribute("data-tableid");
            const tableName = btn.getAttribute("data-tablename");
            openModal("delete-table-template", "Delete Table");
            requestAnimationFrame(() => {
                document.getElementById("delete-table-name").textContent = tableName;

                const confirmBtn = document.getElementById("confirm-delete-table");
                confirmBtn.onclick = () => {
                    fetch(`/web/tables/delete/`, {
                        method: "POST",
                        headers: {
                            "Content-Type": "application/json",
                            "X-CSRFToken": getCSRFToken(),
                        },
                        body: JSON.stringify({ id: tableId }),
                    })
                    .then(res => res.json())
                    .then(result => {
                        if (result.success) {
                            showToast("Table deleted successfully", "success");
                            closeModal();
                            setTimeout(() => location.reload(), 1200);
                        } else {
                            showToast("Error: " + result.message, "error");
                        }
                    })
                    .catch(error => {
                        console.error("Delete error:", error);
                        showToast("An error occurred.", "error");
                    });
                };
            });
        });
    });
    // Add table modal
    document.getElementById("add-table-btn").addEventListener("click", () => {
        openModal("add-table-template", "Add Table");
        requestAnimationFrame(() => {
            const form = document.getElementById("add-table-form");
            form.addEventListener("submit", (e) => {
                e.preventDefault();
                const name = form.name.value.trim();
                if (!name) {
                    showToast("Table name is required.", "error");
                return;
                }
                fetch("/web/tables/create/", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                        "X-CSRFToken": getCSRFToken(),
                    },
                    body: JSON.stringify({ name }),
                })
                .then((res) => res.json())
                .then((result) => {
                    if (result.success) {
                        showToast("Table created successfully!", "success");
                        closeModal();
                        setTimeout(() => location.reload(), 1000);
                    } else {
                    showToast("Error: " + result.message, "error");
                    }
                })
                .catch((err) => {
                    console.error(err);
                    showToast("Something went wrong", "error");
                });
            });
        });
    });
});
