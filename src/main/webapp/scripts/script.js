document.addEventListener("DOMContentLoaded", function () {

    console.log("JS ready"); // 🔥 debug

    const form = document.getElementById("loginForm");

    form.addEventListener("submit", function(event) {
        console.log("Form submitted"); // 🔥 debug

        event.preventDefault();

        const username = document.getElementById("username").value;
        const password = document.getElementById("password").value;

        fetch("/users/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                username: username,
                password: password
            })
        })
        .then(res => {
            if (!res.ok) throw new Error("Login failed");
            return res.json();
        })
        .then(data => {
            localStorage.setItem("token", data.token);
            localStorage.setItem("role", data.role);
            window.location.href = "/tms/dashboard";
        })
        .catch(err => {
            alert("Invalid credentials");
        });
    });

});



//
//document.getElementById("loginForm").addEventListener("submit", function(event) {
//    event.preventDefault(); // ❌ stop normal form submit
//
//    const username = document.getElementById("username").value;
//    const password = document.getElementById("password").value;
//
//    fetch("/users/login", {
//        method: "POST",
//        headers: {
//            "Content-Type": "application/json"
//        },
//        body: JSON.stringify({
//            username: username,
//            password: password
//        })
//    })
//    .then(res => {
//        if (!res.ok) {
//            throw new Error("Login failed");
//        }
//        return res.json();
//    })
//    .then(data => {
//        // ✅ store token
//        localStorage.setItem("token", data.token);
//
//        // ✅ redirect
//        window.location.href = "/tms/dashboard";
//    })
//    .catch(err => {
//        alert("Invalid credentials");
//    });
//});
//
//
//
