window.onload = function(){
	fullCatalogue();
}

let fullCatalogue = async ()=>{
	const request = await fetch("http://localhost:8089/list",{
		method: "GET",
		headers:{
			"Accept": "application/json",
			"Content-Type": "application/json"
			}
	});
	
	const movies = await request.json();

	let contentTable = "";
	
	for(let movie of movies) {
		let newReleaseTrad = movie.newRelease ? "Sí" : "";

		let contentRow = `<tr>
		<td id="idallign">${movie.id}</td>
		<td>${movie.title}</td>
		<td id="releaseDateAllign">${movie.releaseDate}</td>
		<td>${movie.genre}</td>
		<td>${movie.director}</td>
		<td id="newReleaseAllign">${newReleaseTrad}</td>
		<td class="acciones">
		<i class="material-icons button edit">edit</i>
		<i onClick="delMovie(${movie.id})" class="material-icons button delete">delete</i>
		</td>
		</tr>`
		
		contentTable += contentRow;
	}
	
	document.querySelector("#table tbody").outerHTML = contentTable;
}

let delMovie = async (id)=>{
	const request = await fetch("http://localhost:8089/delete/"+ id,
	{
		method: "DELETE",
		headers:{
			"Accept": "application/json",
			"Content-Type": "application/json"
		}
	});
	fullCatalogue();
}