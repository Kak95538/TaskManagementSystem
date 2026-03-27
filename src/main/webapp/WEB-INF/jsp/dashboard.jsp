<!-- <!-- <script> --> -->
<!-- // const token = localStorage.getItem("token"); -->
<!-- // const role = localStorage.getItem("role"); -->

<!-- // //  Show UI based on role -->
<!-- // if(role === "USER"){ -->
<!-- //     document.getElementById("userSection").style.display = "block"; -->
<!-- // } -->
<!-- // else if(role === "ADMIN"){ -->
<!-- //     document.getElementById("adminSection").style.display = "block"; -->
<!-- // } -->

<!-- // //  Load user tasks -->
<!-- // function loadTasks() { -->
<!-- //     fetch("/tasks/user?userId=52", { -->
<!-- //         headers: { -->
<!-- //             "Authorization": "Bearer " + token -->
<!-- //         } -->
<!-- //     }) -->
<!-- //     .then(res => res.json()) -->
<!-- //     .then(showTasks); -->
<!-- // } -->

<!-- // //  Load all tasks (admin) -->
<!-- // function loadAllTasks() { -->
<!-- //     fetch("/tasks/admin", { -->
<!-- //         headers: { -->
<!-- //             "Authorization": "Bearer " + token -->
<!-- //         } -->
<!-- //     }) -->
<!-- //     .then(res => res.json()) -->
<!-- //     .then(showTasks); -->
<!-- // } -->

<!-- // //  Create task -->
<!-- // function createTask() { -->
<!-- //     const title = document.getElementById("title").value; -->
<!-- //     const desc = document.getElementById("desc").value; -->

<!-- //     fetch("/tasks/create", { -->
<!-- //         method: "POST", -->
<!-- //         headers: { -->
<!-- //             "Content-Type": "application/json", -->
<!-- //             "Authorization": "Bearer " + token -->
<!-- //         }, -->
<!-- //         body: JSON.stringify({ -->
<!-- //             title: title, -->
<!-- //             description: desc, -->
<!-- //             userId: 1 -->
<!-- //         }) -->
<!-- //     }) -->
<!-- //     .then(() => loadTasks()); -->
<!-- // } -->

<!-- // //  Render tasks -->
<!-- // function showTasks(tasks) { -->
<!-- //     const list = document.getElementById("taskList"); -->
<!-- //     list.innerHTML = ""; -->

<!-- //     tasks.forEach(t => { -->
<!-- //         const li = document.createElement("li"); -->
<!-- //         li.innerText = t.title + " - " + t.description; -->
<!-- //         list.appendChild(li); -->
<!-- //     }); -->
<!-- // } -->

<!-- // //  Logout -->
<!-- // function logout() { -->
<!-- //     localStorage.clear(); -->
<!-- //     window.location.href = "/tms/login"; -->
<!-- // } -->
<!-- <!-- </script> --> -->
<!-- <!-- <h2>Dashboard</h2> --> -->

<!-- <!-- <button onclick="loadTasks()">Load Tasks</button> --> -->
<!-- <!-- <button onclick="logout()">Logout</button> --> -->

<!-- <!-- <div id="userSection" style="display:none;"> --> -->
<!-- <!--     <h3>User Panel</h3> --> -->
<!-- <!--     <input type="text" id="title" placeholder="Title"/> --> -->
<!-- <!--     <input type="text" id="desc" placeholder="Description"/> --> -->
<!-- <!--     <button onclick="createTask()">Create Task</button> --> -->
<!-- <!-- </div> --> -->

<!-- <!-- <div id="adminSection" style="display:none;"> --> -->
<!-- <!--     <h3>Admin Panel</h3> --> -->
<!-- <!--     <button onclick="loadAllTasks()">View All Tasks</button> --> -->
<!-- <!-- </div> --> -->

<!-- <!-- <ul id="taskList"></ul> --> -->