/* 
   Dev: Lucas Leocadio de Souza
   Data: 22/02/25
   IM: 00009
*/

export function GridForm_init(){
    this.id            = "";
    this.columnName    = "";
    this.columnLabel   = "";
    this.columnWidth   = "";
    this.gridWidth     = "";
    this.mousehouve    = false;
    this.destacarclick = false;

    this.createGrid = ()=>{
        //
        //variaveis privadas
        var pi = '';

        if(!this.id)         throw new Error("Objeto da grid " + this.id + " não encontrado.");
        if(!this.columnName) throw new Error("Objeto da grid " + this.id + " não encontrado.");

        const divtableinit = document.getElementById(this.id);
        const divtable     = document.createElement("div");
        const table        = document.createElement("table");
        const thead        = document.createElement("thead");
        const headerRow    = document.createElement("tr");
        const colunas      = this.columnName.split(",");

        table.id = this.id + "tab"; 

        //
        //estilos
        divtable.style.border       = "1px solid #000";
        divtable .style.marginBlock = "20px";

        table.style.borderCollapse = "collapse";

        //
        //criar colunas
        pi = 0;
        colunas.forEach(coluna => {
            const th = document.createElement("th");

            th.id          = coluna.trim() + "_" + pi;
            th.textContent = this.columnLabel.split(",")[pi];

            headerRow.appendChild(th);

            pi++;
        });

        thead.appendChild(headerRow);
        table.appendChild(thead);

        //const tbody = document.createElement("tbody");
        //tbody.setAttribute("id", `${table.id}-tbody`);
        //table.appendChild(tbody);
        divtable.appendChild(table);
        divtableinit.appendChild(divtable);

        //
        //width colunas
        pi = 0;
        var soma = 0;
        colunas.forEach((coluna, index)=>{
            this.columnWidth.split(",")[pi]

            soma += parseInt(this.columnWidth.split(",")[pi]); 
            pi++;
        });

        if(soma > 100) return alert("Soma do Tamanho das colunas excede o tamanho limite de 100% [tamanho atual: " + soma + "%]");

        pi = 0;
        colunas.forEach((coluna, index)=>{
            document.getElementById(coluna.trim() + "_" + pi).style.width = this.columnWidth.split(",")[pi] +"%"; 
            pi++;
        });

        if(this.gridWidth !== "") table.style.width = this.gridWidth + "px";
    }

    //
    //limpar a grid
    this.clearGrid = ()=>{
        if(!this.id) throw new Error("Objeto da grid " + this.id + " não encontrado.");

        document.getElementById(this.id + "res").childNodes[1].innerHTML = "";
    }

    //carregar dados para a grid
    //o conteudo puxado precisa ser um json
    this.carregaGrid = (path,method,headers)=>{
         if(!path) throw new Error("Caminho não especificado");
    
        if(!method) method = "GET";
        if(!headers) headers = { "Content-Type": "application/json" };

        fetch(path, {
            method: method,
            headers: headers
        })
        .then(response =>{
            if(!response.ok) throw new Error("Ocorreu um erro ao tentar executar a consulta para o caminho " + path + ". Erro:" + response.status + " - " +  response.statusText);
            
            return response.json()
        })
        .then(data => { 
            const dados = data;
            
            console.log(dados);

            //document.getElementById(this.id).childNodes[0].childNodes[1]
            })
        .catch(error => alert(error.message));
    }

    //
    //retornos grid
    this.getTableNode = ()=>{
        if(!document.getElementById(this.id).childNodes[0].childNodes[0] == document.getElementById(this.id + "tab")) {
            throw new Error("Erro ao retornar " + this.id + ".getTableNode. Erro: Tabela não encontrada");
        }

        return document.getElementById(this.id).childNodes[0].childNodes[0].childNodes;
    }

   this.getRowNode = (row)=>{
        return row.childNodes;
    }

    this.getTable = ()=>{
        return document.getElementById(this.id + "tab");
    }
    
    //
    //click na table
}