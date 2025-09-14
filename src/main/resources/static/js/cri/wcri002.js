/* 
    Dev: Lucas Leocadio de Souza
    Data: 15/02/25
    IM: 00011
*/ 

window.addEventListener("load", function () {
    iniciarEventos();
    buscarUserName();
});

var ABA,DMFDiv,CONSUL,PROPRI_GRID;

function iniciarEventos() {
    elementsForm_init();

    PROPRI_GRID               = new GridForm_init();
    PROPRI_GRID.id            = "tabela_propri";
    PROPRI_GRID.columnName    = "codproprietario,nome,documento,enderecocompl,numtel,email,endereco_bairro,endereco_logradouro,endereco_numero,endereco_cidade,endereco_uf,endereco_cep,ehpf";
    PROPRI_GRID.columnLabel   = "Cod. Prop.,Nome,Documento,Endereço,Telefone";
    PROPRI_GRID.columnWidth   = "10,35,13,30,12";
    PROPRI_GRID.columnAlign   = "c,e,c,e,c";
    PROPRI_GRID.mousehouve    = true;
    PROPRI_GRID.destacarclick = true;
    PROPRI_GRID.createGrid();
    
    ABA      = new abaForm_init();
    ABA.id   = "abas";
    ABA.name = "Consulta,Manutenção";
    ABA.icon = "/icons/consultaLupa.png,/icons/manutencaoIcon.png";
    ABA.createAba();

    DMFDiv              = new DMFForm_init();
    DMFDiv.divs         = "dmodalf_proprietario";
    DMFDiv.tema         = 1;
    DMFDiv.cortinaclose = true;
    DMFDiv.formModal();

    CONSUL = new consulForm_init();
    filaFetchInit()

    event_click("bnovabusca");
    event_click("bbuscar");
    event_click("binserir");
    event_click("blimpar");
    event_click("bcadastro");

    event_selected_init("codproprietario");
    inputOnlyNumber('codproprietario,mnmr');

    CONSUL.filterChange('codproprietario','',`/gen/getNomeProp`,['codprop:codproprietario'],'descproprietario');

    controlaTela("inicia");     
}

function event_click(obj) {
    form(obj).addEventListener("click", function () {
        switch (obj) {
            case "bnovabusca": controlaTela("novabusca");
                               break;

            case    "bbuscar": controlaTela("buscar");
                               buscarDadosTable(); 
                               break;

            case   "binserir": form("sacao").innerText   = "Inserindo";
                               form("stitulo").innerText = "Cadastro de Proprietario - " + form("sacao").innerText;
                           
                               controlaTela("modal");
                               DMFDiv.openModal("dmodalf_proprietario");
                               break;

            case    "blimpar": controlaTela("inicia");
                               break;

            case  "bcadastro": adicionarProprietario();
                               break;
        
        }
    });
}

function event_click_table(obj, row){
    switch (obj) {
    case PROPRI_GRID: const valoresLinha = PROPRI_GRID.getRowNode(row);
                      form("sacao").innerText   = ehConsulta()?"Consultando":"Alterando";
                      form("stitulo").innerText = "Cadastro de Proprietario - " + form("sacao").innerText;
                      
                      controlaTela("modal");

                      buscarPropriGrid(valoresLinha);
                      DMFDiv.openModal("dmodalf_proprietario");
                      break;
    }
}

function event_click_aba(){
    switch (ABA.getIndex()) {
    case 0: 
    case 1: controlaTela("inicia");
            break;
    }
}

function filaFetchInit(){
    CONSUL.filaFetch = (retorno, error)=>{
        if(error) return; 

        switch (CONSUL.obj) {
        case            "buscarUserName": form("ideusu").value = retorno;
                                          break;

        case     "adicionarProprietario": alert("Sucesso!",'Dados foram salvos com sucesso! Proprietario está cadastrado no sistema.',5);
                                          if(form("sacao").innerText == "Inserindo") if(confirm("Deseja enviar um Email de Boas Vindas para o Proprietario?")) enviarEmail();

                                          buscarDadosTable();
                                          DMFDiv.closeModal();
                                          break;
        }
    }
}

function buscarPropriGrid(valoresLinha){
    form("mcodproprietario").value  = valoresLinha[0];
    form("mnome").value         	= valoresLinha[1];
    form("mcpf").value          	= valoresLinha[2];
    form("mddd").value          	= valoresLinha[4].substring(0,2);
    form("mtelefone").value     	= valoresLinha[4].substring(2);
    form("memail").value        	= valoresLinha[5];
    form('mbairro').value           = valoresLinha[6];
    form('mnmr').value              = valoresLinha[8];
    form('mcidade').value           = valoresLinha[9];
    form('muf').value               = valoresLinha[10];
    form('mlogradouro').value       = valoresLinha[7];
    form('mcepini').value           = valoresLinha[11].substring(0,5);
    form('mcepdigito').value        = valoresLinha[11].substring(6,9);
    form('mspessoafis').value       = valoresLinha[12] == "false"?"0":"1";
}

function buscarDadosTable(){
    PROPRI_GRID.carregaGrid(`/cri002/buscarPropriGrid`,["codprop:codproprietario"],"","");
}

function adicionarProprietario() {
    const proprietario = { codproprietario:     form("mcodproprietario").value,
                           nome:                form("mnome").value,
                           documento:           retirarFormatDoc(form("mcpf").value),
                           numtel:              form("mddd").value + form("mtelefone").value,
                           email:               form("memail").value,
                           pessoa_fisica:       form('mspessoafis').value == "1",
                           endereco_bairro:     form('mbairro').value,
                           endereco_numero:     form('mnmr').value,
                           endereco_logradouro: form('mlogradouro').value,
                           endereco_cep:        form('mcepini').value + "-" + form('mcepdigito').value,
                           endereco_cidade:     form('mcidade').value,
                           endereco_uf:         form('muf').value,
                           ideusu:              form('ideusu').value};

    CONSUL.consultar("adicionarProprietario",`/cri002/salvarproprietario`,[],"POST","",{body: proprietario});
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

    CONSUL.consultar("enviarEmail",`/email`,[],"POST","",{body: email})
}

function buscarUserName(){
    CONSUL.consultar("buscarUserName",`/home/userlogin`);
}

function controlaTela(opc){
    limparTela(opc);
    if(opc == "inicia" || opc == 'novabusca'){
        desabilitaCampo('bnovabusca',      true);
        desabilitaCampo('bbuscar',         false);
        desabilitaCampo('codproprietario', false);

        form("binserir").style.display = ehManutencao()?"flex":"none";
    }
    if(opc == "buscar"){
        desabilitaCampo('bnovabusca',      false);
        desabilitaCampo('bbuscar',         true);        
        desabilitaCampo('codproprietario', true);
    }
    if(opc == "modal"){
        desabilitaCampo('mnome',       ehConsulta() || !ehInserindo());
        desabilitaCampo('mcpf',        ehConsulta() || !ehInserindo());
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
        desabilitaCampo('mspessoafis', ehConsulta() || !ehInserindo());
    }
}

function limparTela(opc){
    if(opc == "inicia" || opc == 'novabusca'){        
        form('codproprietario').value  = "0";
        form('descproprietario').value = "";
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
        form('mspessoafis').value  = "0";
    }
}


function ehConsulta(){
    return ABA.getIndex() === 0;
}

function ehManutencao(){
    return ABA.getIndex() === 1;
}

function ehInserindo(){
    return form("sacao").innerText == "Inserindo";
}