/* 
    Dev: Lucas Leocadio de Souza
    Data: 15/02/25
    IM: 00011
*/

window.addEventListener("load", function () {
    iniciarEventos();
});

var ABA,DMFDiv,CONSUL,CLIENTES_GRID;

function iniciarEventos() {
    elementsForm_init();

    CLIENTES_GRID               = new GridForm_init();
    CLIENTES_GRID.id            = "tabela_clientes";
    CLIENTES_GRID.columnName    = "codcliente,nome,documento,endereco,numtel,_email,_endereco_logradouro,_endereco_cidade,_endereco_numero,_endereco_bairro,_endereco_uf,_endereco_cep,_pessoafisica";
    CLIENTES_GRID.columnLabel   = "Cod. Cliente,Nome,Documento,Endereço,Telefone";
    CLIENTES_GRID.columnWidth   = "10,40,10,30,10";
    CLIENTES_GRID.columnAlign   = "c,e,c,e,c";
    CLIENTES_GRID.mousehouve    = true;
    CLIENTES_GRID.destacarclick = true;
    CLIENTES_GRID.createGrid();

    ABA      = new abaForm_init();
    ABA.id   = "abas";
    ABA.name = "Consulta,Manutenção";
    ABA.icon = "/icons/consultaLupa.png,/icons/manutencaoIcon.png";
    ABA.createAba();

    DMFDiv              = new DMFForm_init();
    DMFDiv.divs         = "dmodalf_cliente";
    DMFDiv.tema         = 1;
    DMFDiv.cortinaclose = true;
    DMFDiv.formModal();

    CONSUL = new consulForm_init();
    filaFetchInit();

    buscarUserName();

    event_click("bnovabusca");
    event_click("bbuscar");
    event_click("binserir");
    event_click("blimpar");
    event_click("bcadastro");

    event_selected_init("codcliente,mddd,mtelefone,mnmr,mcepini,mcepdigito,mcidade,muf,mbairro,memail,mcpf,mnome,mlogradouro");
    inputOnlyNumber('codcliente,mddd,mtelefone,mnmr,mcepini,mcepdigito');

    CONSUL.filterChange('codcliente','',`/gen/getNomeCliente`,['codcli:codcliente'],'desccliente');

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
                               form("stitulo").innerText = "Cadastro de Cliente - " + form("sacao").innerText;
                           
                               controlaTela("modal");
                               DMFDiv.openModal("dmodalf_cliente");
                               break;

            case    "blimpar": controlaTela("inicia");
                               break;

            case  "bcadastro": adicionarCliente();
                               break;
        
        }
    });
}

function event_click_table(obj,row){
    switch (obj) {
    case CLIENTES_GRID: const valoresLinha = CLIENTES_GRID.getRowNode(row);
                        form("sacao").innerText   = ehConsulta()?"Consultando":"Alterando";
                        form("stitulo").innerText = "Cadastro de Cliente - " + form("sacao").innerText;
                        
                        controlaTela("modal");
                        buscarClienteGrid(valoresLinha);

                        DMFDiv.openModal("dmodalf_cliente");
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
    CONSUL.filaFetch = (retorno)=>{
        switch (CONSUL.obj) {
        case            "buscarUserName": form("ideusu").value = retorno;
                                          break;

        case          "adicionarCliente": alert("Dados Salvos Com Sucesso!","O cliente foi registrado com Sucesso no sistema!", 4);
                                          DMFDiv.closeModal();
                                          
                                          if(form("sacao").innerText == "Inserindo") if(confirm("Deseja enviar um Email de Boas Vindas para o cliente?")) enviarEmail();
                                          
                                          form("bnovabusca").click();
                                          form("bbuscar").click();
                                          break;
        }
    }
}


function buscarClienteGrid(valoresLinha){
    form("mcodcliente").value = valoresLinha[0];
    form("mnome").value       = valoresLinha[1];
    form("mcpf").value        = valoresLinha[2];
    form("mddd").value        = valoresLinha[4].substring(0,2);
    form("mtelefone").value   = valoresLinha[4].substring(2);
    form("memail").value      = valoresLinha[5];
    form("mlogradouro").value = valoresLinha[6];
    form("mcepini").value     = valoresLinha[11].substring(0,5);
    form("mcepdigito").value  = valoresLinha[11].substring(6,9);
    form('mbairro').value     = valoresLinha[9];
    form('mnmr').value        = valoresLinha[8];
    form('mcidade').value     = valoresLinha[7];
    form('muf').value         = valoresLinha[10];
    form('mspessoafis').value = valoresLinha[12] == "false"?"0":"1";
}

function buscarDadosTable(){
    CLIENTES_GRID.carregaGrid(`/cri003/buscarClientes`,['codcliente:codcliente']);
}

function adicionarCliente() {
    const cliente = {codcliente:          form("mcodcliente").value,
                     nome:                form("mnome").value,
                     documento:           retirarFormatDoc(form("mcpf").value),
                     numtel:              form("mddd").value + form("mtelefone").value,
                     email:               form("memail").value,
                     endereco_bairro:     form('mbairro').value,
                     endereco_numero:     form('mnmr').value,
                     endereco_logradouro: form('mlogradouro').value,
                     endereco_cep:        form('mcepini').value + "-" + form('mcepdigito').value,
                     endereco_cidade:     form('mcidade').value,
                     endereco_uf:         form('muf').value,
                     pessoa_fisica:       form('mspessoafis').value == "1",
                     id_usuario:          form('ideusu').value};

    CONSUL.consultar("adicionarCliente",`/cri003/salvarCliente`,[],"POST","",{body: cliente})
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

    CONSUL.consultar("enviarEmail",`/email`,"POST","",{body: email})
}

function buscarUserName(){
    CONSUL.consultar("buscarUserName",`/home/userlogin`);
}

function controlaTela(opc){
    limparTela(opc);
    if(opc == "inicia" || opc == 'novabusca'){
        desabilitaCampo('bnovabusca',      true);
        desabilitaCampo('bbuscar',         false);
        desabilitaCampo('codcliente',      false);

        setDisplay("binserir", ehConsulta()?"none":"flex");
    }
    if(opc == "buscar"){
        desabilitaCampo('bnovabusca',      false);
        desabilitaCampo('bbuscar',         true);        
        desabilitaCampo('codcliente',      true);
    }if(opc == "modal"){
        desabilitaCampo('mnome',       ehConsulta() || !ehInserindo());
        desabilitaCampo('mcpf',        ehConsulta() || !ehInserindo());
        desabilitaCampo('mspessoafis', ehConsulta() || !ehInserindo());
        desabilitaCampo('mddd',        ehConsulta());
        desabilitaCampo('mtelefone',   ehConsulta());
        desabilitaCampo('memail',      ehConsulta());
        desabilitaCampo('mbairro',     ehConsulta());
        desabilitaCampo('mnmr',        ehConsulta());
        desabilitaCampo('mlogradouro', ehConsulta());
        desabilitaCampo('mcepini',     ehConsulta());
        desabilitaCampo('mcepdigito',  ehConsulta());
        desabilitaCampo('mcidade',     ehConsulta());
        desabilitaCampo('muf',         ehConsulta());
        desabilitaCampo('bcadastro',   ehConsulta());
    }
}

function limparTela(opc){
    if(opc == "inicia" || opc == 'novabusca'){        
        form('codcliente').value  = "0"; 
        form('desccliente').value = "Todos os clientes";
    }
    if(opc === "inicia" || opc === "novabusca"){
        CLIENTES_GRID.clearGrid();
    }
    if(opc == "modal"){
        form('mcodcliente').value   = "";
        form('mnome').value         = "";
        form('mspessoafis').value   = "1";
        form('mcpf').value          = "";
        form('mddd').value          = "";
        form('mtelefone').value     = "";
        form('memail').value        = "";
        form('mbairro').value       = "";
        form('mlogradouro').value   = "";
        form('mcepini').value       = "";
        form('mcepdigito').value    = "";
        form('mnmr').value          = "";
        form('mcidade').value       = "";
        form('muf').value           = "";
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