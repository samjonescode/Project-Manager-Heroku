window.onload = {
    fetchAllProjects();
}
function fetchAllProjects(){
    fetch("https://spring-react-app-sj.herokuapp.com/api/project/all").then((Response)=> {
        console.log(response)
    })
}