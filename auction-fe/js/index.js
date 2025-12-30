let currentPage = 0;
let totalPages = 0;

function loadProducts(page) {
    currentPage = page;

    api.get("/products", {
        params: {
            name: document.getElementById("name").value || null,
            price: document.getElementById("price").value || null,
            categoryId: document.getElementById("categoryId").value || null,
            page: page
        }
    }).then(res => {
        const data = res.data;
        totalPages = data.totalPages;
        renderTable(data.content);
        document.getElementById("page-info").innerText =
            `Trang ${data.number + 1} / ${totalPages}`;
    });
}

function renderTable(products) {
    const body = document.getElementById("product-body");
    body.innerHTML = "";

    products.forEach(p => {
        body.innerHTML += `
        <tr>
            <td><input type="checkbox" value="${p.id}"></td>
            <td>${p.id}</td>
            <td>${p.name}</td>
            <td>${p.price}</td>
            <td>${p.status}</td>
            <td>${p.category?.name || ""}</td>
            <td><button onclick="editProduct(${p.id})">Sửa</button></td>
        </tr>`;
    });
}

function toggleAll(source) {
    document.querySelectorAll("#product-body input[type=checkbox]")
        .forEach(cb => cb.checked = source.checked);
}

function deleteSelected() {
    const ids = Array.from(
        document.querySelectorAll("#product-body input:checked")
    ).map(cb => Number(cb.value));

    if (ids.length === 0) {
        alert("Chưa chọn sản phẩm");
        return;
    }

    if (!confirm("Bạn có muốn xóa tất cả sản phẩm đã chọn?")) return;

    api.delete("/products", { data: ids })
        .then(() => {
            alert("Xóa thành công");
            loadProducts(currentPage);
        })
        .catch(err => alert(err.response.data.message));
}

function goAdd() {
    window.location.href = "form.html";
}

function editProduct(id) {
    window.location.href = "form.html?id=" + id;
}

function clearSearch() {
    document.getElementById("name").value = "";
    document.getElementById("price").value = "";
    document.getElementById("categoryId").value = "";
    loadProducts(0);
}

function prevPage() {
    if (currentPage > 0) loadProducts(currentPage - 1);
}

function nextPage() {
    if (currentPage < totalPages - 1) loadProducts(currentPage + 1);
}

loadProducts(0);
