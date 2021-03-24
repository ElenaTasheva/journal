

const userList = document.getElementById('userList')
const searchBar = document.getElementById('searchInput')

const users = [];

fetch("http://localhost:8080/users/all").
then(response => response.json()).
then(data => {
    for (let user of data) {
        users.push(user);
    }
})



searchBar.addEventListener('keyup', (e) => {
    const searchingCharacters = searchBar.value.toLowerCase();
    let filterUser = users.filter(user => {
        return user.email.toLowerCase().includes(searchingCharacters)

    });
    displayUser(filterUser);
})


const displayUser = (users) => {
    userList.innerHTML = users
        .map((u) => {
            return `  <div class="col-md-3">
            <div class="card mb-4 box-shadow">
                <img src="${u.imgUrl}"; 
                     data-holder-rendered="true"
                     style="height: 225px; width: 100%; display: block;">
                <div class="card-body">
                    <div class="text-center">
                        <p class="card-text border-bottom ">Id: ${u.id}</p>
                        <p class="card-text border-bottom ">Email: ${u.email}</p>
                        <p class="card-text border-bottom ">Username: ${u.username}</p>
                    </div>
                    <div class="d-flex justify-content-between align-items-center">
                             
                            <a href="/users/roles/update/${u.id}" class="btn btn-primary-sm btn btn-outline-light" style="background-color: #f5c6cb" type="submit">Make Admin</a> 
                                                
                     
                    </div>
                </div>
            </div>
        </div> `
        })
        .join('');

}
