/* 
   Dev: Lucas Leocadio de Souza
   Data: 22/02/25
   IM: 00009
*/

var init_click;

export function GridForm_init(){
    this.id            = "";
    this.columnName    = "";
    this.columnLabel   = "";
    this.columnWidth   = "";
    this.columnAlign   = "";
    this.gridWidth     = "";
    this.mousehouve    = false;
    this.destacarclick = false;

    this.click_table = '';

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

        table.id = this.id + "tabcolumn"; 

        //
        //estilos
        divtable.style.marginBlock = "20px";

        table.classList.add("tablecolun");

        //
        //criar colunas
        pi = 0;
        colunas.forEach(coluna => {
            const th = document.createElement("th");

            th.id          = coluna.trim() + "__" + pi;
            th.textContent = this.columnLabel.split(",")[pi];

            headerRow.appendChild(th);

            pi++;
        });

        thead.appendChild(headerRow);
        table.appendChild(thead);

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
            document.getElementById(coluna.trim() + "__" + pi).style.width = this.columnWidth.split(",")[pi] +"%"; 
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
            
            return response.json();
        })
        .then(data => {
            const colunas = this.columnName.split(",");
            const aligns  = this.columnAlign.split(",");
            const dados = data;
            
            const table = document.createElement("table");
            table.classList.add("tabledata2");

            if(!Array.isArray(dados)) throw new Error("Ocorreu um erro ao tentar consultar os dados, o retorno não esta na forma de um array. Retorno atual: " + dados);

            const headerbody = document.createElement("thead");
            const trbody     = document.createElement("tr");

            headerbody.style.visibility = "collapse";

            var si = 0;
            colunas.forEach(coluna =>{
                const th             = document.createElement("th");
                const colunaorignode = document.getElementById(this.id).childNodes[0].childNodes[0].childNodes[0].childNodes[0].childNodes[si];

                th.id = coluna.trim() + "_res_" + si;
                th.textContent = this.columnLabel.split(",")[si];

                th.style.width = colunaorignode.style.width;
                    
                trbody.appendChild(th);
                si++;
            })
            headerbody.appendChild(trbody);

            const tbody = document.createElement("tbody");

            dados.forEach(opc => {
                const row = document.createElement("tr");
                
                var ni = 0;
                colunas.forEach(coluna =>{
                    const idcoluna = coluna.split("__" + ni)[0];

                    if(!opc[idcoluna]) throw new Error("Ocorreu um erro ao tentar consultar os dados com o id informado [" + idcoluna + "], o retorno não esta na forma esperado.");

                    const td  = document.createElement("td");
                    const tdtext = opc[idcoluna]? opc[idcoluna] : "N/A";
                    td.textContent = tdtext;

                    if(aligns[ni] === "e") td.classList.add("tdalign-esq");
                    if(aligns[ni] === "c") td.classList.add("tdalign-cen");
                    if(aligns[ni] === "d") td.classList.add("tdalign-dir");
                    else td.classList.add("tdalign-esq");

                    row.onclick = this.click_table;
                    
                    row.appendChild(td); 

                    ni++;
                });

                tbody.appendChild(row);
            });
        
            table.appendChild(headerbody);
            table.appendChild(tbody);
            document.getElementById(this.id).childNodes[0].appendChild(table);
            
            })
        .catch(error => alert(error.message));
    }

    //
    //retornos grid
    this.getTableNode = ()=>{
        return document.getElementById(this.id).childNodes[0].childNodes[1].childNodes[1].childNodes;
    }

   this.getRowNode = (row)=>{
        return row.childNodes;
    }

    this.getTable = ()=>{
        return document.getElementById(this.id + "tabcolumn");
    }
}