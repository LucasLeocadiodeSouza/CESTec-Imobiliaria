/* 
    Dev: Lucas Leocadio de Souza
    Data: 15/06/25
    IM: 00021
*/
window.addEventListener("load", function () {
    wopr001_init();
});

import { GridForm_init }   from "../modules/gridForm.js";
import { DMFForm_init }    from "../modules/dmfForm.js";
import { abaForm_init }    from "../modules/abaForm.js";
import { consulForm_init } from "../modules/consulForm.js";
import { elementsForm_init } from "../modules/elementsForm.js";
import { imgFormat,form,desabilitaCampo,setDisplay,event_selected_init,fillSelect } from "../modules/utils.js";

var AGEN_GRID, FUNC_GRID;
var DMFDiv, ABA, ABAFILTRO, CONSUL;

function wopr001_init(){
    elementsForm_init();

    AGEN_GRID               = new GridForm_init();
    AGEN_GRID.id            = "tabela_agend";
    AGEN_GRID.columnName    = "usuario,mcodmod,mdescmod,codapl,descapl,data,_role,_arqu_inic";
    AGEN_GRID.columnLabel   = "Usuário,Código Mod.,Módulo Referente,Código Aplicação,Aplicação,Data";
    AGEN_GRID.columnWidth   = "12,12,17,17,30,12";
    AGEN_GRID.columnAlign   = "e,c,c,c,e,c";
    AGEN_GRID.mousehouve    = false;
    AGEN_GRID.destacarclick = false;
    AGEN_GRID.createGrid();

    FUNC_GRID               = new GridForm_init();
    FUNC_GRID.id            = "funcionarios_grid";
    FUNC_GRID.columnName    = "cb,codfunc,nomefunc,codcargo,cargo,codsetor,setor";
    FUNC_GRID.columnLabel   = "<input type='checkbox' id='marcatodos' name='marcatodos'>,Cód. Func,Funcionario,Cód. Carg,Cargo,Cód. Set,Setor";
    FUNC_GRID.columnWidth   = "6,13,24,13,17,12,15";
    FUNC_GRID.columnAlign   = "c,c,e,c,e,c,e";
    FUNC_GRID.gridHeight    = "250px";
    FUNC_GRID.mousehouve    = false;
    FUNC_GRID.destacarclick = false;
    FUNC_GRID.createGrid();

    ABA      = new abaForm_init();
    ABA.id   = "abas";
    ABA.name = "Consulta,Manutenção";
    ABA.icon = "/icons/consultaLupa.png,/icons/manutencaoIcon.png";
    ABA.createAba();

    ABAFILTRO      = new abaForm_init();
    ABAFILTRO.id   = "abasfiltroagen";
    ABAFILTRO.name = "Funcionario,Setor,Cargo";
    ABAFILTRO.icon = "/icons/work_icon.png,/icons/group_icon.png,/icons/clips_icon.png";
    ABAFILTRO.createAba();

    DMFDiv              = new DMFForm_init();
    DMFDiv.divs         = "dmodalf_agenda";
    DMFDiv.tema         = 1;
    DMFDiv.cortinaclose = true;
    DMFDiv.formModal();

    CONSUL = new consulForm_init();

    iniciarEventos();
    buscarUserName();
}

function iniciarEventos() {
    controlaTela("inicia");

    event_click_table();
    event_click_aba();
    event_selected_init("codfunc,codcargo,codsetor");

    event_click("bnovabusca");
    event_click("bbuscar");
    event_click("blimpar");
    event_click("binserir");
    event_click("bagendar");
    
    event_change("codfunc");
    event_change("codcargo");
    event_change("codsetor");
}

function event_click_table(){
    // AGEN_GRID.click_table = ()=>{
    //     const clickedCell = event.target.closest('td');
    //     if (clickedCell && clickedCell.cellIndex === 0) return;

    //     const valoresLinha = LIBACESS_GRID.getRowNode(event.target.closest('tr'));

    //     controlaTela("modal");

    //     preencherDadosModal(valoresLinha);

    //     DMFDiv.openModal("dmodalf_libacess");
    // };
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
            //
        });        
    }
    if(obj == 'blimpar'){
        form(obj).addEventListener("click", function () {
            controlaTela("inicia");
        });
    }
    if(obj == 'binserir'){
        form(obj).addEventListener("click", function () {
            form("sacao").innerText   = "Inserindo";
            form("stitulo").innerText = "Operar Agendamentos - " + form("sacao").innerText;
            
            controlaTela("modal");

            DMFDiv.fullScream = true;
            DMFDiv.openModal("dmodalf_agenda");
            DMFDiv.fullScream = false;
        });
    }
    if(obj == 'bagendar'){
        form(obj).addEventListener("click", function () {
            if(!confirm("Deseja mesmo adicionar esse agendamento?")) return;

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
    ABAFILTRO.setAba_init(()=>{
        switch (ABAFILTRO.getIndex()) {
        case 0: 
        case 1: controlaTela("modal");
                break;
        }
    });
}

function controlaTela(opc){
    limparTela(opc);
    if(opc == "inicia" || opc == 'novabusca'){
        desabilitaCampo('codfunc',         false);
        desabilitaCampo('codcargo',        false);
        desabilitaCampo('codsetor',        false);
        desabilitaCampo('bnovabusca',      true);
        desabilitaCampo('bbuscar',         false);

        setDisplay("binserir",   ehManutencao()?"block":"none");
    }
    if(opc == "buscar"){
        desabilitaCampo('codfunc',         true);
        desabilitaCampo('codcargo',        true);
        desabilitaCampo('codsetor',        true);
        desabilitaCampo('bnovabusca',      false);
        desabilitaCampo('bbuscar',         true);
    }
    if(opc == "modal"){
        // desabilitaCampo('mdescapl',     !ehCadastroDeApl());
        // desabilitaCampo('mmodulo',      !ehCadastroDeApl());
        // desabilitaCampo('mrestrole',    !ehCadastroDeApl());
        // desabilitaCampo('bagendar', !ehCadastroDeApl());
    }
}

function limparTela(opc){
    if(opc === "inicia" || opc === 'novabusca'){        
        form('codfunc').value   = "0";
        form('descfunc').value  = "Todos os Funcionarios";
        form('codcargo').value  = "0";
        form('desccargo').value = "Todos os Cargos";
        form('codsetor').value  = "0";
        form('descsetor').value = "Todos os Setores";
    }
    if(opc === "inicia" || opc === "novabusca"){
        AGEN_GRID.clearGrid();
    }
    if(opc === "modal"){
        form('mtituloagen').value  = "";
        form('mdataagen').value    = "";
        form('mhoraagen').value    = "";
        form('mdescagen').value    = "";
        form('mfuncionario').value = "";

        fillSelect("msetor",  [{id: 0, descricao: "Todos os Setores"}],0);
        fillSelect("mcargo",  [{id: 0, descricao: "Todos os Cargos"}],0);
        fillSelect("mmotivo", [{id: 0, descricao: "Aviso"}],0);
    }
}

function preencherDadosModalCadasApl(valores){
    // form("sacao").innerText   = "Alterando";
    // form("stitulo").innerText = "Cadastro de Aplicação - " + form("sacao").innerText;

    // buscarRoleAcess(valores[6]);
    
    // form("hcodapl").value   = valores[3];
    // form("mdescapl").value  = valores[4];
    // form("mmodulo").value   = valores[1];
    // form("mdescmod").value  = valores[2];
    // form("marqinit").value  = valores[7];
}

function ehConsulta(){
    return ABA.getIndex() === 0;
}

function ehManutencao(){
    return ABA.getIndex() === 1;
}
function adicionarAplicacao() {
    // const aplicacao = { id:            form("hcodapl").value,
    //                     idmodulo:      form("mmodulo").value,
    //                     role:          form("mrestrole").value,
    //                     descricao:     form('mdescapl').value,
    //                     arquivo_inic:  form('marqinit').value,
    //                     ideusu:        form('ideusu').value};

    // CONSUL.consultar(`/mrb001/cadastrarAplicacao`,"POST","",{body: aplicacao})
    // .then(data =>{
    //     if(data != "OK") return alert(data);

    //     alert("Aplicação cadastrada com sucesso!");

    //     form("bnovabusca").click();
    //     form("bbuscar").click();

    //     DMFDiv.closeModal();
    // });
}

function buscarUserName(){
    CONSUL.consultar(`/home/userlogin`)
    .then(data =>{
        form("ideusu").value = data
    });
}

function getDescricaoModulo(){
    CONSUL.consultar(`/mrb001/getDescricaoModulo?codmodulo=${form("mmodulo").value}`)
    .then(data =>{ form("mdescmod").value = data })
    .catch(error => form("mdescmod").value = "");
}

function carregaGridAgendamentos(){
    AGEN_GRID.carregaGrid(`/mrb001/buscarAplicacoesGrid`,"","");
}