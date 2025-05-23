/* 
   Dev: Lucas Leocadio de Souza
   Data: 22/02/25
   IM: 00009
*/

export function GridForm_init(){
    this.id            = "";
    this.columnName    = "";
    this.columnlabel   = "";
    this.columnWidth   = "";
    this.gridWidth     = "";
    this.mousehouve    = false;
    this.destacarclick = false;

    this.createGrid = ()=>{
        if(!this.id)         throw new Error("Objeto da grid " + this.id + " não encontrado.");
        if(!this.columnName) throw new Error("Objeto da grid " + this.id + " não encontrado.");

        const divtable  = document.getElementById(this.id);
        const table     = document.createElement("table");
        const thead     = document.createElement("thead");
        const headerRow = document.createElement("tr");
        const colunas   = this.columnName.split(",");

        var pi = 0;
        colunas.forEach((coluna,index) => {
            const th = document.createElement("th");

            th.id = coluna.trim() + "_" + pi;
            th.textContent = coluna.trim();

            headerRow.appendChild(th);

            pi++;
        });

        thead.appendChild(headerRow);
        table.appendChild(thead);

        const tbody = document.createElement("tbody");
        tbody.setAttribute("id", `${table.id}-tbody`);
        table.appendChild(tbody);
        divtable.appendChild(table);


        //width colunas
        var pi = 0;
        colunas.forEach((coluna, index)=>{
            form(coluna.trim() + "_" + pi).innerText   = this.columnName.split(",")[pi];
            form(coluna.trim() + "_" + pi).style.width = this.columnWidth.split(",")[pi] +"%"; 
            pi++;
        });

        table.style.width = width + "px";
    }

    //limpar a grid
    this.clearGrid = ()=>{
        if(!this.id) throw new Error("Objeto da grid " + this.id + " não encontrado.");

        document.getElementById(this.id + "res").childNodes[1].innerHTML = "";
    }

    //carregar dados para a grid
    //o conteudo puxado precisa ser um json
    this.carregaGrid = (path,method,header)=>{
        if(path === "") throw new Error("O caminho  " + path + " não foi encontrado.");
        if(method === '') method = {"Content-Type":"application/json"};
        if(header === '') header = "GET";

        fetch("/home/userlogin", {
            method: method,
            headers: header
        })
        .then(response =>{
            if(!response.ok) throw new Error("Ocorreu um erro ao tentar executar a consulta para o caminho " + path + ". Erro:" + response.status + " - " +  response.statusText);
            
            return response.json()
        })
        .then(data => { 
            const dados = data;
            
            console.log(dados)


            //document.getElementById(this.id).childNodes[0].childNodes[1]
            })
        .catch(error => alert(error.message));
    }
}