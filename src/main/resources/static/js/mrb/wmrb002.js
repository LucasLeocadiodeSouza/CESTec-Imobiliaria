/* 
    Dev: Lucas Leocadio de Souza
    Data: 21/06/25
    IM: 00022
*/
window.addEventListener("load", function () {
    wmrb002_init();
});

var TABELASBANCO_GRID,COLUNAS_GRID,INDEX_GRID;
var CONSUL;

function wmrb002_init(){
    TABELASBANCO_GRID               = new GridForm_init();
    TABELASBANCO_GRID.id            = "table_tabelasbanco";
    TABELASBANCO_GRID.columnName    = "tabela";
    TABELASBANCO_GRID.columnLabel   = "Tabela";
    TABELASBANCO_GRID.columnWidth   = "100";
    TABELASBANCO_GRID.columnAlign   = "e";
    TABELASBANCO_GRID.mousehouve    = false;
    TABELASBANCO_GRID.destacarclick = true;
    TABELASBANCO_GRID.createGrid();

    COLUNAS_GRID               = new GridForm_init();
    COLUNAS_GRID.id            = "table_colunasbanco";
    COLUNAS_GRID.columnName    = "column,type,size,sizedecimal,order";
    COLUNAS_GRID.columnLabel   = "Column,Type,Size,Size Decimal,Order";
    COLUNAS_GRID.columnWidth   = "32,12,10,15,10";
    COLUNAS_GRID.columnAlign   = "e,e,c,c,c";
    COLUNAS_GRID.mousehouve    = false;
    COLUNAS_GRID.clickgrid     = true;
    COLUNAS_GRID.createGrid();

    INDEX_GRID               = new GridForm_init();
    INDEX_GRID.id            = "table_indextabela";
    INDEX_GRID.columnName    = "index,coluns,type,order";
    INDEX_GRID.columnLabel   = "Index,Coluns,Type,Order";
    INDEX_GRID.columnWidth   = "40,40,10,10";
    INDEX_GRID.columnAlign   = "e,e,c,c";
    INDEX_GRID.mousehouve    = false;
    INDEX_GRID.clickgrid     = true;
    INDEX_GRID.createGrid();

    CONSUL = new consulForm_init();

    iniciarEventos();
}

function iniciarEventos() {
    controlaTela("inicia");

    event_click_table();
    event_selected_init("codtable");

    event_click("bbuscar");
}

function event_click_table(){
    TABELASBANCO_GRID.click_table = ()=>{
        const valoresLinha = TABELASBANCO_GRID.getRowNode(event.target.closest('tr'));

        COLUNAS_GRID.clearGrid();
        INDEX_GRID.clearGrid();

        buscarColunas(valoresLinha[0]);
        buscarIndexs(valoresLinha[0]);
    };
}

function event_click(obj) {
    if(obj == "bbuscar"){
        form(obj).addEventListener("click", function () {
            controlaTela("cleargrids");
            buscarTabelas();
        });        
    }
}

function controlaTela(opc){
    limparTela(opc);
}

function limparTela(opc){
    if(opc === "inicia"){        
        form('codtable').value   = "";
    }
    if(opc === "cleargrids"){
        TABELASBANCO_GRID.clearGrid();
        COLUNAS_GRID.clearGrid();
        INDEX_GRID.clearGrid();
    }
}

function buscarTabelas(){
    TABELASBANCO_GRID.carregaGrid(`/mrb002/buscarTabelas?nomeTabela=${form("codtable").value}`);
}

//CONSUL.consultar("buscarRoleAcess",`/mrb001/buscarRoleAcess`)
//.then(data =>{
//    fillSelect("mrestrole",data,true);
//    form("mrestrole").value = valorinicial;
//});

function buscarColunas(tabela){
    COLUNAS_GRID.carregaGrid(`/mrb002/buscarColunas?nomeTabela=${tabela}`);
}

function buscarIndexs(tabela){
    INDEX_GRID.carregaGrid(`/mrb002/buscarIndexs?nomeTabela=${tabela}`);
}