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
    USUARIO_GRID.mousehouve    = true;
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

function event_click_table(obj,row){
    switch (obj) {
    case USUARIO_GRID: const valoresLinha = USUARIO_GRID.getRowNode(row);
                       controlaTela("modal");

                       preencherDadosModal(valoresLinha);
                       DMFDiv.openModal("dmodalf_usuario");
                       break;
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

function event_change(obj){
    if(obj == "mcodcargo"){
        form(obj).addEventListener("change", function(){
            buscarNomeCargo(form("mcodcargo").value);
        });
    }

    if(obj == "mcodsetor"){
        form(obj).addEventListener("change", function(){
            buscarNomeSetor(form("mcodsetor").value);
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
    CONSUL.filaFetch = (retorno,error)=>{
        if(error){
            switch (CONSUL.obj) {
            case    "buscarNomeCargo": alert("Erro ao buscar o registro do Cargo", "Não encontrado registro de Cargo com o codigo informado!", 4);
                                       form("mcodcargo").value = "0";
                                       break;

            case    "buscarNomeSetor": alert("Erro ao buscar o registro do Setor", "Não encontrado registro de Setor com o codigo informado!", 4);
                                       form("mcodsetor").value = "0";
                                       break;

            case   "cadastrarUsuario": alert("Erro ao cadastrar Usuário!", retorno, 4);
                                       break;
            }
            return;
        }

        switch (CONSUL.obj) {
        case     "buscarUserName": form("ideusu").value = retorno;
                                   break;
        
        case    "buscarNomeCargo": form("mdesccargo").value = retorno;
                                   break;
        
        case    "buscarNomeSetor": form("mdescsetor").value = retorno;
                                   break;

        case   "cadastrarUsuario": alert("Sucesso!", "Usuário cadastrado com sucesso!", 4);
                                   carregarGridFuncionarios();
                                   DMFDiv.closeModal();
                                   break;
        }
    }
}


function controlaTela(opc){
    limparTela(opc);
    if(opc == "inicia" || opc == 'novabusca'){
        desabilitaCampo('ideusufunc',      false);
        desabilitaCampo('bnovabusca',      true);
        desabilitaCampo('bbuscar',         false);

       setDisplay("bcadastrar", ehManutencao()?"block":"none");
    }
    if(opc == "buscar"){
        desabilitaCampo('ideusufunc',      true);
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
        form('ideusufunc').value = "";
        form('nomefunc').value   = "Todos os Usuários";
    }
    if(opc === "inicia" || opc === "novabusca"){
        USUARIO_GRID.clearGrid();
    }
    if(opc === "modal"){
        form('hmodal_codfunc').value = "";
        form('musuario').value       = "";
        form('mcpf').value           = "";
        form('mendereco').value      = "";
        form('mdatnasc').value       = "";
        form('mcodsetor').value      = "0";
        form('mdescsetor').value     = "";
        form('mcodcargo').value      = "0";
        form('mdesccargo').value     = "";
        form('msalario').value       = "0.00";
        form('mdatcriado').value     = "";
    }
}

function preencherDadosModal(valores){
    form("sacao").innerText   = ehConsulta()?"Consultando":"Alterando";
    form("stitulo").innerText = "Cadastrar Usuário - " + form("sacao").innerText;

    form('hmodal_codfunc').value = valores[0];
    form('musuario').value       = valores[1];
    form('mcpf').value           = valores[8];
    form('mendereco').value      = valores[9];
    form('mdatnasc').value       = valores[7];
    form('mcodsetor').value      = valores[2];
    form('mdescsetor').value     = valores[3];
    form('mcodcargo').value      = valores[4];
    form('mdesccargo').value     = valores[5];
    form('msalario').value       = valores[10];
    form('mdatcriado').value     = valores[11];
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

function cadastrarUsuario(){
    const usuario = {codfunc   : form('hmodal_codfunc').value,      
                     nomefunc  : form('musuario').value,
                     cpf       : form('mcpf').value,
                     codsetor  : form('mcodsetor').value,
                     codcargo  : form('mcodcargo').value,
                     endereco  : form('mendereco').value,
                     salario   : form('msalario').value,
                     datinasc  : form('mdatnasc').value}

    CONSUL.consultar("cadastrarUsuario",`/mrb003/cadastrarUsuario?ideusu=${form("ideusu").value}&acao=${form("sacao").innerText.trim()}`,'POST','',{body: usuario});
}

function buscarNomeCargo(codcargo){
    CONSUL.consultar("buscarNomeCargo",`/mrb003/buscarNomeCargo?codcargo=${codcargo}`);
}

function buscarNomeSetor(codsetor){
    CONSUL.consultar("buscarNomeSetor",`/mrb003/buscarNomeSetor?codsetor=${codsetor}`);
}

function carregarGridFuncionarios(){
    USUARIO_GRID.carregaGrid(`/mrb003/carregarGridFuncionarios?ideusu=${form("ideusufunc").value}`,"","");
}