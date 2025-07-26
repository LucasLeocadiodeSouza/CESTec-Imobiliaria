/* 
    Dev: Lucas Leocadio de Souza
    Data: 15/06/25
    IM: 00011
*/

window.addEventListener("load", function () {
    iniciarEventos();
});

var ABA,DMFDiv,CONSUL,CORRETOR_GRID;

function iniciarEventos() {
    elementsForm_init();

    CORRETOR_GRID               = new GridForm_init();
    CORRETOR_GRID.id            = "tabela_corretores";
    CORRETOR_GRID.columnName    = "codcliente,nome,documento,endereco,numtel,email,endereco_logradouro,endereco_cidade,endereco_numero,endereco_bairro,endereco_uf,endereco_cep";
    CORRETOR_GRID.columnLabel   = "Cod. Cliente,Nome,Documento,Endereço,Telefone";
    CORRETOR_GRID.columnWidth   = "10,40,10,30,10";
    CORRETOR_GRID.columnAlign   = "c,e,c,e,c";
    CORRETOR_GRID.mousehouve    = true;
    CORRETOR_GRID.destacarclick = true;
    CORRETOR_GRID.createGrid();

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

    event_click_table();
    event_click_aba();
    event_selected_init("codcliente");

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

            DMFDiv.openModal("dmodalf_cliente");
            controlaTela("modal");
        });
    }
    if(obj == 'blimpar'){
        form(obj).addEventListener("click", function () {
            controlaTela("inicia");
        });
    }
    if(obj == 'bcadastro'){
        form(obj).addEventListener("click", function () {
            adicionarCliente();
        });
    }
}

function event_click_table(){
    CORRETOR_GRID.click_table = () => {
        const valoresLinha = CORRETOR_GRID.getRowNode(event.target.closest('tr'));
        controlaTela("modal");

        form("sacao").innerText   = ehConsulta()?"Consultando":"Alterando";
        form("stitulo").innerText = "Cadastro de Cliente - " + form("sacao").innerText;
        buscarClienteGrid(valoresLinha);

        
        DMFDiv.openModal("dmodalf_cliente");
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
        case        "buscarUserName": form("ideusu").value = retorno;
                                      break;

        case      "adicionarCliente": if(retorno != "OK") return alert(retorno);
                                      alert("Dados Salvos Com Sucesso!");
                                      if(form("sacao").innerText == "Inserindo")if(confirm("Deseja enviar um Email de Boas Vindas para o cliente?")) enviarEmail();

                                      DMFDiv.closeModal();

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
}

function buscarDadosTable(){
    CORRETOR_GRID.carregaGrid(`/cliente/buscarClientes?codcliente=${form("codcliente").value}`,"","");
}

function adicionarCliente() {
    const cliente = {codcliente:          form("mcodcliente").value,
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

    CONSUL.consultar("adicionarCliente",`/cliente/salvarCliente`,"POST","",{body: cliente});
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

    CONSUL.consultar("enviarEmail",`/email`,"POST","",{body: email});
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
        desabilitaCampo('mnome',       ehConsulta());
        desabilitaCampo('mcpf',        ehConsulta());
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
        form('codcliente').value = "0";
    }
    if(opc === "inicia" || opc === "novabusca"){
        CORRETOR_GRID.clearGrid();
    }
    if(opc == "modal"){
        form('mcodcliente').value   = "";
        form('mnome').value         = "";
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