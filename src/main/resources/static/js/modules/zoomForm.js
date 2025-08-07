/* 
   Dev: Lucas Leocadio de Souza
   Data: 06/08/25
   IM: 00026

   ZOOM -> link para um filtro sobre algum dado;

*/

window.addEventListener("load", function () {
   wzoom_init();
});

var USUARIO_GRID;
var DMFDiv, ABA, CONSUL;

function wzoom_init(){
   elementsForm_init();

   USUARIO_GRID               = new GridForm_init();
   USUARIO_GRID.id            = "tabela_user";
   USUARIO_GRID.columnName    = "codfunc,nome,codsetor,nomesetor,codcargo,nomecargo,criadoem,nasc,cpf,endereco,salario,_criadoemSQL";
   USUARIO_GRID.columnLabel   = "Código Func.,Funcionario,Código Setor,Setor,Código Cargo,Cargo,Data inclusão";
   USUARIO_GRID.columnWidth   = "10,22,11,17,11,17,12";
   USUARIO_GRID.columnAlign   = "c,e,c,e,c,e,c";
   USUARIO_GRID.mousehouve    = true;
   USUARIO_GRID.destacarclick = true;
   USUARIO_GRID.createGrid();


   DMFDiv              = new DMFForm_init();
   DMFDiv.divs         = "dmodalf_usuario";
   DMFDiv.tema         = 1;
   DMFDiv.cortinaclose = true;
   DMFDiv.formModal();

    CONSUL = new consulForm_init();
    filaFetchInit();
    
    iniciarEventos();
}

function iniciarEventos() {
    controlaTela("inicia");

    event_click_table();
    event_click_aba();
    event_selected_init("ideusufunc,musuario,mcodsetor,mcodcargo,msalario,mcpf,mendereco");
    event_blur_init("msalario");
    inputOnlyNumber('msalario,mcodcargo,mcodsetor');

    event_click("bnovabusca");
    event_click("bbuscar");
    event_click("bcadastrar");
    event_click("blimpar");
    event_click("bsalvar");
    
    event_change("mcodcargo");
    event_change("mcodsetor");
}

function event_click_table(){
    USUARIO_GRID.click_table = ()=>{
        const valoresLinha = USUARIO_GRID.getRowNode(event.target.closest('tr'));
        controlaTela("modal");

        preencherDadosModal(valoresLinha);

        DMFDiv.openModal("dmodalf_usuario");
    };
}

function event_click(obj) {
    if(obj == "bnovabusca"){
        form(obj).addEventListener("click", function () {
            controlaTela("novabusca");
        });
    }
    if(obj == "bbuscar"){
        form(obj).addEventListener("click", function () {
            controlaTela("buscar");
            carregarGridFuncionarios();
        });        
    }
    if(obj == 'blimpar'){
        form(obj).addEventListener("click", function () {
            controlaTela("inicia");
        });
    }
    if(obj == 'bcadastrar'){
        form(obj).addEventListener("click", function () {
            form("sacao").innerText   = "Inserindo";
            form("stitulo").innerText = "Cadastrar Usuário - " + form("sacao").innerText;

            controlaTela("modal");
            DMFDiv.openModal("dmodalf_usuario");
            
        });
    }
    if(obj == 'bsalvar'){
        form(obj).addEventListener("click", function () {
            if(!confirm("Deseja mesmo cadastrar o usuário?")) return;

            cadastrarUsuario();
        });
    }
}