/* 
    Dev: Lucas Leocadio de Souza
    Data: 15/02/25
    IM: 00011
*/

//import { createGrid } from './gridForm.js';

window.addEventListener("load", function () {
    iniciarEventos();
    buscarUserName();
});

import { GridForm_init }   from "./modules/gridForm.js";
import { DMFForm_init }    from "./modules/dmfForm.js";
import { abaForm_init }    from "./modules/abaForm.js";
import { consulForm_init } from "./modules/consulForm.js";
import { elementsForm_init } from "./modules/elementsForm.js";
import { imgFormat,form,desabilitaCampo,setDisplay,event_selected_init } from "./modules/utils.js";

var ABA,DMFDiv,CONSUL,PROPRI_GRID;

function iniciarEventos() {
    elementsForm_init();

    PROPRI_GRID               = new GridForm_init();
    PROPRI_GRID.id            = "tabela_propri";
    PROPRI_GRID.columnName    = "codproprietario,nome,documento,enderecocompl,numtel,email,endereco_logradouro,endereco_numero,endereco_cidade,endereco_uf,endereco_cep";
    PROPRI_GRID.columnLabel   = "Cod. Prop.,Nome,Documento,Endereço,Telefone";
    PROPRI_GRID.columnWidth   = "10,40,10,30,10";
    PROPRI_GRID.columnAlign   = "c,e,c,e,c";
    PROPRI_GRID.mousehouve    = true;
    PROPRI_GRID.destacarclick = false;
    PROPRI_GRID.createGrid();
    
    ABA      = new abaForm_init();
    ABA.id   = "abas";
    ABA.name = "Consulta,Manutencão";
    ABA.icon = "/icons/consultaLupa.png,/icons/manutencaoIcon.png";
    ABA.createAba();

    DMFDiv              = new DMFForm_init();
    DMFDiv.divs         = "dmodalf_proprietario";
    DMFDiv.tema         = 1;
    DMFDiv.cortinaclose = true;
    DMFDiv.formModal();

    CONSUL = new consulForm_init();

    event_click("bnovabusca");
    event_click("bbuscar");
    event_click("binserir");
    event_click("blimpar");
    event_click("bcadastro");

    event_click_table();
    event_selected_init("codproprietario");
    event_click_aba();

    controlaTela("inicia");     
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
            buscarDadosTable(); 
        });
    }
    if(obj == 'binserir'){
        form(obj).addEventListener("click", function () {
            form("sacao").innerText   = "Inserindo";
            form("stitulo").innerText = "Cadastro de Cliente - " + form("sacao").innerText;

            controlaTela("modal");
            DMFDiv.openModal("dmodalf_proprietario");
        });
    }
    if(obj == 'blimpar'){
        form(obj).addEventListener("click", function () {
            controlaTela("inicia");
        });
    }
    if(obj == 'bcadastro'){
        form(obj).addEventListener("click", function () {
            adicionarProprietario();
            DMFDiv.closeModal();
        });
    }
}

function event_click_table(){
    PROPRI_GRID.click_table = ()=>{
        const valoresLinha = PROPRI_GRID.getRowNode(event.target.closest('tr'));

        controlaTela("modal");

        form("sacao").innerText   = ehConsulta()?"Consultando":"Alterando";
        form("stitulo").innerText = "Cadastro de Proprietario - " + form("sacao").innerText;

        buscarPropriGrid(valoresLinha);

        DMFDiv.openModal("dmodalf_proprietario");
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

function buscarPropriGrid(valoresLinha){
    form("mcodproprietario").value  = valoresLinha[0];
    form("mnome").value         	= valoresLinha[1];
    form("mcpf").value          	= valoresLinha[2];
    form("mddd").value          	= valoresLinha[4].substring(0,2);
    form("mtelefone").value     	= valoresLinha[4].substring(2);
    form("memail").value        	= valoresLinha[5];
    form('mbairro').value           = valoresLinha[3];
    form('mnmr').value              = valoresLinha[7];
    form('mcidade').value           = valoresLinha[8];
    form('muf').value               = valoresLinha[9];
    form('mlogradouro').value       = valoresLinha[6];
    form('mcepini').value           = valoresLinha[10].substring(0,5);
    form('mcepdigito').value        = valoresLinha[10].substring(6,9);
}

function buscarDadosTable(){
    PROPRI_GRID.carregaGrid(`/contratosCadastroClientes/proprietario/buscarPropriGrid?codprop=${form("codproprietario").value}`,"","");
}

function adicionarProprietario() {
    const proprietario = { codproprietario:     form("mcodproprietario").value,
                           nome:                form("mnome").value,
                           documento:           form("mcpf").value,
                           numtel:              form("mddd").value + form("mtelefone").value,
                           email:               form("memail").value,
                           endereco_bairro:     form('mbairro').value,
                           endereco_numero:     form('mnmr').value,
                           endereco_logradouro: form('mlogradouro').value,
                           endereco_cep:        form('mcepini').value + "-" + form('mcepdigito').value,
                           endereco_cidade:     form('mcidade').value,
                           endereco_uf:         form('muf').value,
                           id_usuario:          form('ideusu').value};

    CONSUL.consultar(`/contratosCadastroClientes/proprietario`,"POST","",proprietario)
    .then(data => {
        if(data != "OK") return alert(data);
        alert("Dados Salvos Com Sucesso!");
        if(form("sacao").innerText == "Inserindo")if(confirm("Deseja enviar um Email de Boas Vindas para o Proprietario?")) enviarEmail();

        form("bnovabusca").click();
        form("bbuscar").click();
    });
}

function enviarEmail(){
    const email = {
        to: form("memail").value,
        subject: "Bem Vinda a CESTec Enterprise 😊",
        body: "<html>"
            + "<head>"
            + "</head>"
            + "<body style='font-family: Arial, sans-serif; color: #333;'>"
            + '<div style="max-width: 600px; margin: auto; padding: 20px; border: 1px solid #e0e0e0; border-radius: 8px;">'
            + "<h2>Olá Colaborador!</h2>"
            + "<p>É com grande satisfação que damos as boas-vindas à nossa equipe.</p>"
            + "<p>Na <strong>CESTec Enterprise</strong>, atuamos no setor imobiliário com o compromisso de oferecer os melhores serviços e um atendimento de excelência tanto aos nossos clientes quanto aos nossos colaboradores.</p>"
            + "<p>Agradecemos pela sua confiança e por fazer parte do nosso time. Valorizamos o profissionalismo, a colaboração e a dedicação de cada membro da equipe para construirmos juntos uma trajetória de sucesso.</p>"
            + "<p>Conte conosco para o que precisar!</p>"
            + "<p>Atenciosamente,<br>"
            + "<strong>Equipe CESTec Enterprise</strong></p>"
            + "</div>"
            + "</body>"
            + "</html>"}

    CONSUL.consultar(`/email`,"POST","",email)
}

function buscarUserName(){
    CONSUL.consultar(`/home/userlogin`)
    .then(data =>{
        form("ideusu").value = data
    });
}

function controlaTela(opc){
    limparTela(opc);
    if(opc == "inicia" || opc == 'novabusca'){
        desabilitaCampo('bnovabusca',      true);
        desabilitaCampo('bbuscar',         false);
        desabilitaCampo('codproprietario', false);

        form("binserir").style.display     = ehManutencao()?"flex":"none";
    }
    if(opc == "buscar"){
        desabilitaCampo('bnovabusca',      false);
        desabilitaCampo('bbuscar',         true);        
        desabilitaCampo('codproprietario', true);
    }
    if(opc == "modal"){
        desabilitaCampo('mnome',       ehConsulta());
        desabilitaCampo('mcpf',        ehConsulta());
        desabilitaCampo('mddd',        ehConsulta());
        desabilitaCampo('mtelefone',   ehConsulta());
        desabilitaCampo('memail',      ehConsulta());
        desabilitaCampo('mbairro',     ehConsulta());
        desabilitaCampo('mnmr',        ehConsulta());
        desabilitaCampo('mcidade',     ehConsulta());
        desabilitaCampo('muf',         ehConsulta());
        desabilitaCampo('bcadastro',   ehConsulta());
        desabilitaCampo('mlogradouro', ehConsulta());
        desabilitaCampo('mcepini',     ehConsulta());
        desabilitaCampo('mcepdigito',  ehConsulta());
    }
}

function limparTela(opc){
    if(opc == "inicia" || opc == 'novabusca'){        
        form('codproprietario').value = "0";
    }
    if(opc === "inicia" || opc === "novabusca"){
        PROPRI_GRID.clearGrid();
    }
    if(opc == "modal"){
        form('mnome').value        = "";
        form('mcpf').value         = "";
        form('mddd').value         = "";
        form('mtelefone').value    = "";
        form('memail').value       = "";
        form('mbairro').value      = "";
        form('mnmr').value         = "";
        form('mcidade').value      = "";
        form('muf').value          = "";
        form('mlogradouro').value  = "";
        form('mcepini').value      = "";
        form('mcepdigito').value   = "";
    }
}


function ehConsulta(){
    return ABA.getIndex() === 0;
}

function ehManutencao(){
    return ABA.getIndex() === 1;
}