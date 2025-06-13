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
    this.scroll        = false;
    this.clickgrid     = true;
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
        colunas.forEach((coluna,index) => {
            const th           = document.createElement("th");
            const colunasLabel = this.columnLabel.split(",");

            th.id          = coluna.trim() + "__" + pi;
            th.textContent = colunasLabel[pi];

            if(!colunasLabel[index]) th.style.display = "none";

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
        if(!document.getElementById(this.id)) throw new Error("Objeto da grid " + this.id + " não encontrado.");

        if(document.getElementById(this.id + "res")) document.getElementById(this.id + "res").remove();
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
            const colunas      = this.columnName.split(",");
            const colunasLabel = this.columnLabel.split(",");
            const aligns       = this.columnAlign.split(",");
            const dados        = data;

            if(document.getElementById(this.id + "res")) this.clearGrid;

            const table = document.createElement("table");
            table.id    = this.id + "res";
            table.classList.add("tabledata2");

            if(this.scroll) table.style.overflowY = "scroll";

            if(!Array.isArray(dados)) throw new Error("Ocorreu um erro ao tentar consultar os dados, o retorno não esta na forma de um array. Retorno atual: " + dados);

            const headerbody = document.createElement("thead");
            const trbody     = document.createElement("tr");

            headerbody.style.visibility = "collapse";

            var si = 0;
            colunas.forEach((coluna,index) =>{
                const th             = document.createElement("th");
                const colunaorignode = document.getElementById(this.id).childNodes[0].childNodes[0].childNodes[0].childNodes[0].childNodes[si];

                th.id = coluna.trim() + "_res_" + si;
                th.textContent = this.columnLabel.split(",")[si];

                th.style.width = colunaorignode.style.width
                if(!colunasLabel[index]) th.style.display = "none";

                trbody.appendChild(th);
                si++;
            })
            headerbody.appendChild(trbody);

            const tbody = document.createElement("tbody");

            dados.forEach(opc => {
                const row = document.createElement("tr");

                
                colunas.forEach((coluna,index) =>{
                    const td     = document.createElement("td");
                    const tdtext = opc[index];
                    td.innerHTML = tdtext;

                    if(aligns[index] === "e") td.classList.add("tdalign-esq");
                    if(aligns[index] === "c") td.classList.add("tdalign-cen");
                    if(aligns[index] === "d") td.classList.add("tdalign-dir");
                    if(aligns[index] === "eoe") td.classList.add("tdalign-esqoverellip");
                    else td.classList.add("tdalign-esq");

                    if(!colunasLabel[index]) td.style.display = "none";

                    row.appendChild(td);
                });

                
                row.onclick = this.click_table;
                
                //
                //cor mouse hover
                if(this.mousehouve){
                    row.addEventListener("mouseover", ()=>{row.classList.add("mouseovercolor")});
                    row.addEventListener("mouseout",  ()=>{row.classList.remove("mouseovercolor")});
                }

                tbody.appendChild(row);
            });
        
            table.appendChild(headerbody);
            table.appendChild(tbody);
            document.getElementById(this.id).childNodes[0].appendChild(table);
            
            if(this.clickgrid){
                clickRowBorder(table.id);
            }
            })
        .catch(error => alert(error.message));
    }

    //
    //retornos grid
    this.getTableNode = ()=>{
        return document.getElementById(this.id).childNodes[0].childNodes[1].childNodes[1].childNodes;
    }

   this.getRowNode = (row)=>{
        const rowsnode = row.childNodes;
        let rowsInner = [];

        rowsnode.forEach(row =>{
            rowsInner.push(row.innerText);
        });

        return rowsInner;
    }

    this.getTable = ()=>{
        return document.getElementById(this.id + "tabcolumn");
    }
}

function clickRowBorder(idtable){
    const table = document.getElementById(idtable);

    if(!table) throw new Error("Ocorreu um erro ao tentar consultar a tabela com o id informado [" + idtable + "]. Div não encontrada.");
    
    const tbody = table.querySelector('tbody');
    if (!tbody) return;
    
    const rows = tbody.querySelectorAll('tr');
    
    rows.forEach(row => {
        row.addEventListener('click', () => {
            rows.forEach(otherRow => {
                otherRow.classList.remove('clicktableborder');
            });

            row.classList.add('clicktableborder');
        });

        document.addEventListener('click', function(event) {
            const clicouFora = !row.contains(event.target);
            
            const elementosBody = Array.from(document.body.children);
            const gridIndex     = elementosBody.indexOf(table);
            const targetIndex   = elementosBody.indexOf(event.target);
            
            const mesmoIndice = targetIndex === gridIndex;

            if (clicouFora && mesmoIndice) {
                row.classList.remove("clicktableborder");
            }
        });
    });    
}