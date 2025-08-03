/* 
    Dev: Lucas Leocadio de Souza
    Data: 21/06/25
    IM: 00023
*/
window.addEventListener("load", function () {
    wmrb001_init();
});

var USUARIO_GRID;
var DMFDiv, ABA, CONSUL;

function wmrb001_init(){
    elementsForm_init();

    USUARIO_GRID               = new GridForm_init();
    USUARIO_GRID.id            = "tabela_user";
    USUARIO_GRID.columnName    = "codfunc,nome,codsetor,nomesetor,codcargo,nomecargo,criadoem,nasc,cpf,endereco,salario,_criadoemSQL";
    USUARIO_GRID.columnLabel   = "Código Func.,Funcionario,Código Setor,Setor,Código Cargo,Cargo,Data inclusão";
    USUARIO_GRID.columnWidth   = "10,22,11,17,11,17,12";
    USUARIO_GRID.columnAlign   = "c,e,c,e,c,e,c";
    USUARIO_GRID.mousehouve    = false;
    USUARIO_GRID.destacarclick = true;
    USUARIO_GRID.createGrid();

    ABA      = new abaForm_init();
    ABA.id   = "abas";
    ABA.name = "Consulta,Manutenção";
    ABA.icon = "/icons/consultaLupa.png,/icons/manutencaoIcon.png";
    ABA.createAba();

    DMFDiv              = new DMFForm_init();
    DMFDiv.divs         = "dmodalf_usuario";
    DMFDiv.tema         = 1;
    DMFDiv.cortinaclose = true;
    DMFDiv.formModal();

    CONSUL = new consulForm_init();
    filaFetchInit();
    
    iniciarEventos();
    buscarUserName();
}

function iniciarEventos() {
    controlaTela("inicia");

    event_click_table();
    event_click_aba();
    event_selected_init("codfunc,musuario,mcodsetor,mcodcargo");

    event_click("bnovabusca");
    event_click("bbuscar");
    event_click("bcadastrar");
    event_click("blimpar");
    event_click("bsalvar");
    
    // /event_change("mmodulo");
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
            carregarGridFucnionarios();
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

            //adicionarAplicacao();
            DMFDiv.closeModal();
        });
    }
}

function event_change(obj){
    if(obj == "mmodulo"){
        form(obj).addEventListener("change", function(){
            //getDescricaoModulo();
        });
    }
}

function event_click_aba(){
    ABA.setAba_init(()=>{
        switch (ABA.getIndex()) {
        case 0: 
        case 1: controlaTela("inicia");
                break;
        }
    });
}


function filaFetchInit(){
    CONSUL.filaFetch = (retorno)=>{
        switch (CONSUL.obj) {
        case    "buscarUserName": form("ideusu").value = retorno;
                                  break;
        
        }
    }
}


function controlaTela(opc){
    limparTela(opc);
    if(opc == "inicia" || opc == 'novabusca'){
        desabilitaCampo('codfunc',         false);
        desabilitaCampo('bnovabusca',      true);
        desabilitaCampo('bbuscar',         false);

       setDisplay("bcadastrar", ehManutencao()?"block":"none");
    }
    if(opc == "buscar"){
        desabilitaCampo('codfunc',         true);
        desabilitaCampo('bnovabusca',      false);
        desabilitaCampo('bbuscar',         true);
    }
    if(opc == "modal"){
        desabilitaCampo('musuario',   ehConsulta());
        desabilitaCampo('mcpf',       ehConsulta());
        desabilitaCampo('mendereco',  ehConsulta());
        desabilitaCampo('mdatnasc',   ehConsulta());
        desabilitaCampo('mcodsetor',  ehConsulta());
        desabilitaCampo('mcodcargo',  ehConsulta());
        desabilitaCampo('mdatcriado', ehConsulta());
        desabilitaCampo('msalario',   ehConsulta());
        desabilitaCampo('bsalvar',    ehConsulta());

        setDisplay("dcriadoem",        !ehConsulta()?"none":"block");
        setDisplay("container-button", !ehConsulta()?"flex":"none");
    }
}

function limparTela(opc){
    if(opc === "inicia" || opc === 'novabusca'){        
        form('codfunc').value   = "";
        form('nomefunc').value  = "Todos os Usuários";
    }
    if(opc === "inicia" || opc === "novabusca"){
        USUARIO_GRID.clearGrid();
    }
    if(opc === "modallibacess"){
        form('musuario').value   = "";
        form('mcpf').value       = "";
        form('mendereco').value  = "";
        form('mdatnasc').value   = "";
        form('mcodsetor').value  = "0";
        form('mdescsetor').value = "";
        form('mcodcargo').value  = "0";
        form('mdesccargo').value = "";
        form('msalario').value   = "0.00";
        form('mdatcriado').value = "";
    }
}

function preencherDadosModal(valores){
    form("sacao").innerText   = ehConsulta()?"Consultando":"Alterando";
    form("stitulo").innerText = "Cadastrar Usuário - " + form("sacao").innerText;

    form('musuario').value   = valores[1];
    form('mcpf').value       = valores[8];
    form('mendereco').value  = valores[9];
    form('mdatnasc').value   = valores[7];
    form('mcodsetor').value  = valores[2];
    form('mdescsetor').value = valores[3];
    form('mcodcargo').value  = valores[4];
    form('mdesccargo').value = valores[5];
    form('msalario').value   = valores[10];
    form('mdatcriado').value = valores[11];
}

function ehConsulta(){
    return ABA.getIndex() === 0;
}

function ehManutencao(){
    return ABA.getIndex() === 1;
}

function buscarUserName(){
    CONSUL.consultar("buscarUserName",`/home/userlogin`)
}

function carregarGridFucnionarios(){
    USUARIO_GRID.carregaGrid(`/mrb003/carregarGridFucnionarios?&ideusu=${form("codfunc").value}`,"","");
}