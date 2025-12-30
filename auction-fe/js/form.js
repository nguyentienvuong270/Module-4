const params = new URLSearchParams(window.location.search);
const id = params.get("id");

loadCategories();

if (id) {
    document.getElementById("title").innerText = "Sửa sản phẩm";
    loadProduct(id);
}

function loadCategories() {
    api.get("/categories").then(res => {
        const select = document.getElementById("categoryId");
        select.innerHTML = "";
        res.data.forEach(c => {
            select.innerHTML += `<option value="${c.id}">${c.name}</option>`;
        });
    });
}

function loadProduct(id) {
    api.get("/products/" + id).then(res => {
        const p = res.data;
        document.getElementById("name").value = p.name;
        document.getElementById("price").value = p.price;
        document.getElementById("status").value = p.status;
        document.getElementById("categoryId").value = p.category.id;
    });
}

function submitForm(e) {
    e.preventDefault();

    const product = {
        name: document.getElementById("name").value,
        price: Number(document.getElementById("price").value),
        status: document.getElementById("status").value,
        category: {
            id: Number(document.getElementById("categoryId").value)
        }
    };

    if (id) {
        api.put("/products/" + id, product)
            .then(() => {
                alert("Cập nhật thành công");
                back();
            })
            .catch(err => alert(err.response.data.message));
    } else {
        api.post("/products", product)
            .then(() => {
                alert("Thêm thành công");
                back();
            })
            .catch(err => alert(err.response.data.message));
    }
}

function back() {
    window.location.href = "index.html";
}
