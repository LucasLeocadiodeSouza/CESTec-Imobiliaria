/* 
   Dev: Lucas Leocadio de Souza
   Data: 22/02/25
   IM: 00009

    GridForm_init -> funcao para importar a criacao do objeto grid, necessario importar import { GridForm_init }   from (caminho, ex: "../modules/gridForm.js");

    Objeto Grid->
        GRID               = new GridForm_init(); //obrigatório

        GRID.id            = "exemplo"; 
                              Parametro obrigatório, id da div onde sera criado a grid: <div id="exemplo"></div>

        GRID.columnName    = "codigoprop,nome,documento,endereco,_email,_endereco_logradouro,_endereco_cep";
                              columnName -> recebe uma String e separa por "," o nome das colunas. Parametro não obrigatório, só para ficar mais
                                            facil de entender oq esta sendo criado pro coluna, auxilia na legibilidade.
                                            Caso seja uma columna invesivel (colunas adiantes do limite mostrados, deve ser colocado um "_" 
                                            antes da coluna como boa pratica);

        GRID.columnLabel   = "Cod. Prop.,Nome,Documento,Endereço,Telefone";
                              columnLabel -> recebe uma String separada por "," o nome das colunas. Parametro obrigatório, sera mostrado no thead,
                                             ele definira o limite de colunas na grid;

        GRID.columnWidth   = "10,40,10,30,10";
                              columnWith -> recebe uma String separada por ",". Parametro não obrigatório, mas caso seja criado é necessario informar uma divisao exata entre o numero de colunas informada no columnLabe.
                                            A soma deve ser exatamente 100;

        GRID.columnAlign   = "c,e,c,e,c";
                              columnAlign -> recebe uma String separada por ",". Parametro não obrigatório, mas caso seja informado é necessario informar uma divisao exata entre o numero de colunas informada no columnLabe.
                                            recebe 3 valores possiveis: c   -> conteudo sera alinhado no centro da coluna;
                                                                        d   -> conteudo sera alinhado na direita da coluna; 
                                                                        e   -> conteudo sera alinhado na esquerda da coluna;
                                                                        eoe -> conteudo sera alinhado na esquerda da coluna, porem caso o conteudo
                                                                               ultrapasse o limite da coluna ele quebra a linha ate que o conteudo 
                                                                               esteja totalmente exposto;

        GRID.gridWidth     = "1600px";
                             gridWidth -> recebe uma String. Parametro não obrigatorio. Altera o width da grid para uma largura fixa, deve ser informado a unidade de medida; 
        
        GRID.gridHeight    = "500px";
                             gridHeight -> recebe uma String. Parametro não obrigatorio. Altera o height da grid para uma altura fixa, deve ser informado a unidade de medida; 

        GRID.mousehouve    = true;
                            mousehouver -> parametro não obrigatório, boolean inicialmente false. Caso seja informado true ele vai adicionar uma
                                           cor destaque ao passar o mouse por cima de alguma row da grid (cor fixa em rgb(176, 193, 223));

        GRID.ocultarhead   = true;
                            ocultarhead -> parametro não obrigatório, oculta o cabecalho da grid, ficando so com as linhas;

        GRID.fullParent    = true;
                            fullParent -> parametro não obrigatório, remove o margin-block e aatribui o tamanho total da table para o tamanho completo da div sobreposta;

        GRID.tema          = '0';
                            borderTema -> parametro não obrigatório, recebe uma String, é o tema da table. '0' sem borda, '1' tema padrao, borda cinza e quadrada, '2' sem borda e com cor branca;

        GRID.createGrid();
                            createGrid(); -> funcao obrigatória para a criacao da grid;

        / * * * * * * * * * * /
        
        GRID.getRowNode -> metodo que retorna um array com as posicoes de cada coluna na linha especificada. Pode ser usada junto com o click_table 
                           para passar o event target no tr ou td;

                         ** Exemplo:  GRID.click_table = ()=>{
                                        const valoresLinha = GRID.getRowNode(event.target.closest('tr'));
                                      }

        GRID.carregaGrid -> metodo usado para carregar a grid. Recebe um parametro path obrigatorio de onde vai trazer os conteudos da grid.
                                                               Recebe mais dois parametros, method e headers, os dois nao sao obrigatorios, por padrao eles sao atribuidos como GET e { "Content-Type": "application/json" };

                                                               O retorno do GET deve ser um json (Para isso ja existe o meio da criacao da grd no backend (ver documentacao cestec/util/utilForm.java ));

        function event_click_table(obj,row,e){     -> Funcao sobrescrita para gerenciar o click na table. Não deve ser chamada diretamente nas aplicacoes (Se necessario primeiro analisar bem a logica);
            switch (obj) {
            case GRID: console.log("vc clicou em uma linha da grid " + GRID.id);  
                       break;
            };
        }

*/

function GridForm_init(){
    this.id              = "";
    this.columnName      = "";
    this.columnLabel     = "";
    this.columnWidth     = "";
    this.columnAlign     = "";
    this.gridWidth       = "";
    this.gridHeight      = "";
    this.scroll          = false;
    this.mousehouve      = false;
    this.destacarclick   = true;
    this.ocultarhead     = false;
    this.fullParent      = false;
    this.miniModalOver   = false;
    this.colsModalOver   = "";
    this.borderTema      = '1';
    this.click_table     = '';

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
        divtableinit.classList.add("div_table_init")

        if(this.scroll) divtable.style.overflowX = "scroll";
        if(this.gridWidth !== "")  divtable.style.width  = this.gridWidth;
        if(this.gridHeight !== "") divtable.style.height = this.gridHeight;
        if(this.fullParent)  divtableinit.style.marginBlock = '0';
        if(this.tema === '0' || this.tema === '2')     divtableinit.style.border = 'none';
        
        divtable.className = "h100";

        table.classList.add("tablecolun");

        //
        //criar colunas
        pi = 0;
        colunas.forEach((coluna,index) => {
            const th           = document.createElement("th");
            const colunasLabel = this.columnLabel.split(",");

            th.id        = coluna.trim() + "__" + pi;

            if(!colunasLabel[index]) th.style.display = "none";

            th.onclick = () => clickFiltroHead(this.id + "res", index);

            const container_row  = document.createElement("div");
            container_row.classList.add("container-row100");

            const container_text       = document.createElement("div");
            container_text.classList.add("tdalign-cen");
            container_text.style.width = "90%";
            container_text.innerHTML   = colunasLabel[pi];

            const container_img       = document.createElement("div");
            container_img.classList.add("img-filtro-grid");
            container_img.style.width = "10%";

            const img_seta  = document.createElement("img");
            img_seta.src    = "/icons/seta_filtro_grid_icon.png";
            img_seta.style.height = "10px";
            
            container_img.appendChild(img_seta);
            container_row.appendChild(container_text);
            container_row.appendChild(container_img);
            th.appendChild(container_row);

            headerRow.appendChild(th);
            pi++;
        });

        if(this.ocultarhead) headerRow.classList.add("dnone");

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
    }

    //
    //limpar a grid
    this.clearGrid = ()=>{
        if(!document.getElementById(this.id)) throw new Error("Objeto da grid " + this.id + " não encontrado.");

        if(document.getElementById(this.id + "res")) document.getElementById(this.id + "res").remove();
        if(document.getElementById("d" + this.id + "res")) document.getElementById("d" + this.id + "res").remove();
    }

    //carregar dados para a grid
    //o conteudo puxado precisa ser um json
    this.carregaGrid = async (path,method,headers,ocultarloader,body)=>{
         if(!path) throw new Error("Caminho não especificado");
    
        if(!method) method = "GET";
        if(!headers) headers = { "Content-Type": "application/json" };

        var loaderTimeout,loader;
        if(!ocultarloader){
            loader = document.createElement('div');
            loader.id    = 'global-loader';
            loader.style = `position: fixed;
                            top: 0;
                            left: 0;
                            width: 100%;
                            height: 100%;
                            background: rgb(255 255 255 / 55%);
                            z-index: 9999;
                            display: none;`;

            const spinner = document.createElement('img');
            spinner.src = '/icons/loader_icon.png';
            spinner.style = `position: absolute;
                            top: 50%;
                            left: 50%;
                            transform: translate(-50%, -50%);
                            animation: girar 1s linear infinite;`;

            loader.appendChild(spinner);

            const style = document.createElement('style');
            style.innerHTML = `
                @keyframes girar {
                    from { transform: translate(-50%, -50%) rotate(0deg); }
                    to { transform: translate(-50%, -50%) rotate(360deg); }
                }
            `;
            document.head.appendChild(style);

            document.body.appendChild(loader);

            loader.style.display = 'block';
            loaderTimeout    = setTimeout(() => {
                loader.innerHTML += '<p>Carregando...</p>';
            }, 1000);
        }

        await fetch(path, {
            method: method,
            headers: headers,
            body: method !== 'GET' ? JSON.stringify(body) : null
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

            if(document.getElementById(this.id + "res")) this.clearGrid();

            const divres = document.createElement("div");
            divres.id    = "d" + this.id + "res";
            divres.classList.add("divtableres");

            const table  = document.createElement("table");
            table.id     = this.id + "res";
            table.classList.add("tabledata2");

            if(!Array.isArray(dados)) throw new Error("Ocorreu um erro ao tentar consultar os dados, o retorno não esta na forma de um array. Retorno atual: " + dados);

            if(this.tema === '0' || this.tema === '2') {
                table.style.border = 'none';
            }

            const originalHeaderCells = document.querySelectorAll(`#${this.id} .tablecolun th`);

            const headerbody = document.createElement("thead");
            const trbody     = document.createElement("tr");

            headerbody.style.visibility = "collapse";

            //if(this.ocultarhead) headerbody.style.display = "none"; //isso vai perder o this.columnWidth;

            var si = 0;
            colunas.forEach((coluna,index) =>{
                const th             = document.createElement("th");
                //const colunaorignode = document.getElementById(this.id).childNodes[0].childNodes[0].childNodes[0].childNodes[0].childNodes[si];

                th.id = coluna.trim() + "_res_" + si;
                th.textContent = this.columnLabel.split(",")[si];

                if (originalHeaderCells[index]) {
                    th.style.width = originalHeaderCells[index].style.width;
                    th.style.minWidth = originalHeaderCells[index].style.width;
                }

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

                    if(this.tema === '2') {
                        td.style.color = '#FFF';
                    }

                    if(aligns[index] === "e") td.classList.add("tdalign-esq");
                    if(aligns[index] === "c") td.classList.add("tdalign-cen");
                    if(aligns[index] === "d") td.classList.add("tdalign-dir");
                    if(aligns[index] === "eoe") td.classList.add("tdalign-esqoverellip");
                    else td.classList.add("tdalign-esq");

                    if(!colunasLabel[index]) td.style.display = "none";

                    row.appendChild(td);
                });
 
                row.onclick = () => event_click_table(this, row, event);

                if(this.mouseover_table) row.addEventListener("mouseover", ()=>{this.mouseover_table()});
                
                //
                //cor mouse hover
                if(this.mousehouve){
                    row.addEventListener("mouseover", ()=>{row.classList.add("mouseovercolor")});
                    row.addEventListener("mouseout",  ()=>{row.classList.remove("mouseovercolor")});
                }

                tbody.appendChild(row);

                if(this.miniModalOver){
                    const modal = document.createElement("div");

                    if(!document.getElementById("dcontainer-minimodalrow")){
                        modal.id    = "dcontainer-minimodalrow"
                        modal.classList.add("container-minimodalrow");
                        document.body.appendChild(modal);
                    }

                    row.addEventListener("mouseover", (e)=>{ criarChildModalOverCol(row, this.id + "tabcolumn", this.colsModalOver, e)});
                    row.addEventListener("mouseout",  (e)=>{ 
                        const bodyTemModal = document.body.contains(document.getElementById("dcontainer-minimodalrow"));
                        if(bodyTemModal) setDisplay("dcontainer-minimodalrow", "none") 
                    });
                } 
            });
        
            table.appendChild(headerbody);
            table.appendChild(tbody);
            divres.appendChild(table);
            document.getElementById(this.id).childNodes[0].appendChild(divres);
            
            if(this.destacarclick){
                clickRowBorder(table.id);
            }
        })
        .catch(error => alert(error.message))
        .finally(() => {
            if(!ocultarloader){
                clearTimeout(loaderTimeout);
                loader.remove();
            };

            if(this.filaFetchGrid) this.filaFetchGrid();
        })
    }

    this.addJsonRow = (obj) => {
        const tbody = document.getElementById(this.id).childNodes[0].childNodes[1].childNodes[0].childNodes[1];

        const row = document.createElement("tr");

        const campos = obj.split(",");
        const aligns = this.columnAlign.split(",");

        campos.forEach((campo,index) => {
            const td = document.createElement("td");
            td.innerHTML = campo;

            if(this.tema === '2') {
                td.style.color = '#FFF';
            }

            if(aligns[index] === "e") td.classList.add("tdalign-esq");
            if(aligns[index] === "c") td.classList.add("tdalign-cen");
            if(aligns[index] === "d") td.classList.add("tdalign-dir");
            if(aligns[index] === "eoe") td.classList.add("tdalign-esqoverellip");
            else td.classList.add("tdalign-esq");
            
            if(!colunasLabel[index]) td.style.display = "none";

            row.appendChild(td)
        });

        row.onclick = () => event_click_table(this, row, event);
        
        //
        //cor mouse hover
        if(this.mousehouve){
            row.addEventListener("mouseover", ()=>{row.classList.add("mouseovercolor")});
            row.addEventListener("mouseout",  ()=>{row.classList.remove("mouseovercolor")});
        }

        tbody.appendChild(row);
    }

    //
    //retornos grid
    this.getTableNode = ()=>{
        return document.getElementById(this.id).childNodes[0].childNodes[1].childNodes[0].childNodes[1].childNodes;
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
                otherRow.classList.remove('efeitorowclick');
            });

            row.classList.add('clicktableborder');
            row.classList.add('efeitorowclick');
        });

        document.addEventListener('click', function(event) {
            if(!table.contains(event.target)) return;

            const clicouFora = !row.contains(event.target);
            
            const elementosBody = Array.from(document.body.children);
            const gridIndex     = elementosBody.indexOf(table);
            const targetIndex   = elementosBody.indexOf(event.target);
            
            const mesmoIndice = targetIndex === gridIndex;

            if (clicouFora && mesmoIndice) {
                row.classList.remove("clicktableborder");
                row.classList.remove("efeitorowclick");
            }
        });
    });    
}

function criarChildModalOverCol(row, idTableHead, indexCols, event){
    const modal = document.getElementById("dcontainer-minimodalrow");
    
    modal.innerHTML = "";

    indexCols.split(",").forEach(index =>{
        const container_div = document.createElement("div");
        container_div.classList.add("dmf");

        const titulo     = document.createElement("label");
        titulo.id = "label_minimodalover";
        titulo.innerText = getColTextHead(idTableHead, index) + ": ";

        const paragrafo     = document.createElement("p");
        paragrafo.id = "label_minimodalover";
        paragrafo.innerText = row.childNodes[index].innerText;

        container_div.appendChild(titulo);
        container_div.appendChild(paragrafo);
        modal.appendChild(container_div);
    });

    modal.style.top  = (event.screenY - 15) + "px";
    modal.style.left = (event.screenX)   + "px";

    setDisplay(modal.id, "flex");
}

function getColTextHead(idtableHead, posicao){
    const table  = document.getElementById(idtableHead);

    if(!table) return;

    const thead = table.querySelector('thead');
    if (!thead) return;

    const row = thead.querySelector('tr');
    
    for(let i = 0; i < row.childNodes.length; i++){
        if(i == posicao) return row.childNodes[i].innerText;
    }
}

function clickFiltroHead(idtable, indexCol){
    const table  = document.getElementById(idtable);
    let ehNumber = false;

    if(!table) throw new Error("Ocorreu um erro ao tentar consultar a tabela com o id informado [" + idtable + "]. Div não encontrada.");

    const cabecalho = table.querySelector('tr');
    if (!cabecalho) return;

    const tbody = table.querySelector('tbody');
    if (!tbody) return;

    const rows = tbody.querySelectorAll('tr');
    
    for (let i = 0; i < rows.length; i++){
        const index = Number(rows[i].childNodes[indexCol].innerText);

        if(!isNaN(index)) ehNumber = true;
    }

    const taOrdenado = jaEstaOrdenado(rows,indexCol);
    for (let i = 0; i < rows.length; i++){
        for (let j = i + 1; j < rows.length; j++){
            const campoA      = ehNumber?Number(rows[i].childNodes[indexCol].innerText) : rows[i].childNodes[indexCol].innerText;
            const campoB      = ehNumber?Number(rows[j].childNodes[indexCol].innerText) : rows[j].childNodes[indexCol].innerText;
            const validaOrdem = taOrdenado? campoA < campoB : campoA > campoB;

            if(validaOrdem){
                const auxiliar    = rows[j].innerHTML;
                rows[j].innerHTML = rows[i].innerHTML;
                rows[i].innerHTML = auxiliar;
            }
        }
    }   
}

function jaEstaOrdenado(rows,indexCol){
    let ehNumber = false;

    for (let i = 0; i < rows.length; i++){
        const index = Number(rows[i].childNodes[indexCol].innerText);

        if(!isNaN(index)) ehNumber = true;
    }

    for (let i = 0; i < rows.length; i++){
        for (let j = i + 1; j < rows.length; j++){
            const campoA = ehNumber?Number(rows[i].childNodes[indexCol].innerText) : rows[i].childNodes[indexCol].innerText;
            const campoB = ehNumber?Number(rows[j].childNodes[indexCol].innerText) : rows[j].childNodes[indexCol].innerText;
            if(campoA > campoB) return false;
        }
    }

    return true;
}